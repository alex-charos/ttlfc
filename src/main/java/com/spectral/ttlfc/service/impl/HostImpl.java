package com.spectral.ttlfc.service.impl;

import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.spectral.ttlfc.factory.GameFactory;
import com.spectral.ttlfc.model.Player;
import com.spectral.ttlfc.model.PlayerEntryRequest;
import com.spectral.ttlfc.model.PlayerEntryResponse;
import com.spectral.ttlfc.model.PlayerHand;
import com.spectral.ttlfc.model.Table;
import com.spectral.ttlfc.service.CardGame;
import com.spectral.ttlfc.service.CardService;
import com.spectral.ttlfc.service.Host;
import com.spectral.ttlfc.service.Lobby;
import com.spectral.ttlfc.utils.CardGameType;
import com.spectral.ttlfc.utils.EntryRequestType;
import com.spectral.ttlfc.utils.PlayerResponseType;


@Component(value="hostImpl")
public class HostImpl implements Host {
	
	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	Lobby lobbyImpl;
	
	@Autowired
	CardService footballPlayerCardService;
	
	Map<UUID, Long> playerHeartbeats;
	
    @Value("${host.allowedInactiveTimeInSeconds}")
    private int allowedInactiveTimeInSeconds;

	
	private CardGameType gameType = CardGameType.standardCardGame;
	
	public PlayerEntryResponse acceptPlayer(PlayerEntryRequest peReq) {
		Player p = peReq.getPlayer();
		if (p.getUuid() == null) {
			p.setUuid(UUID.randomUUID());
		}
		PlayerEntryResponse per = new PlayerEntryResponse();
		per.setPlayerToken(p.getUuid());
		per.setResponse(PlayerResponseType.inWaitingRoom);
		EntryRequestType ert = peReq.getRequestType();
		if (ert.equals(EntryRequestType.createTable)) {
			Table t = peReq.getRequestTable();
			t.setId(UUID.randomUUID());
			t.getPlayersWaiting().add(p);
			lobbyImpl.getWaitingTables().put(t.getId(), t);
			
			
		} else if (ert.equals(EntryRequestType.joinTable)) {
			Table t =lobbyImpl.getWaitingTables().get(peReq.getRequestTable().getId());
			t.getPlayersWaiting().add(peReq.getPlayer());
			if (t.getPlayersWaiting().size() >= t.getPlayersRequired()) {
				lobbyImpl.getWaitingTables().remove(t.getId());
				CardGame cg =  GameFactory.getGame(gameType, t.getPlayersWaiting());
				cg.dealDeck(footballPlayerCardService.generateCards(10));
				
				UUID gameId = lobbyImpl.createCardGame(cg);
				per.setGameToken(gameId);
				per.setResponse(PlayerResponseType.enteredGame);

				
			}
		}
		playerHeartbeat(p.getUuid());
		return per;
	}

	public PlayerEntryResponse getPlayerStatus(UUID playerUUID) {
		PlayerEntryResponse per = new PlayerEntryResponse();
		per.setPlayerToken(playerUUID);
		boolean playerInWaitingRoom = false;
		for (Table t : lobbyImpl.getWaitingTables().values()) {
			for (Player p : t.getPlayersWaiting()) {
				if (p.getUuid().equals(playerUUID)) {
					per.setResponse(PlayerResponseType.inWaitingRoom);
					playerInWaitingRoom = true;
				}
			}
		}
		if (!playerInWaitingRoom) {
			for (UUID uuid : lobbyImpl.getCardGames().keySet()) {
				for ( PlayerHand pPlaying :lobbyImpl.getCardGames().get(uuid).getPlayers()) {
					if (pPlaying.getPlayer().getUuid().equals(playerUUID)) {
						per.setResponse(PlayerResponseType.enteredGame);
						per.setGameToken(uuid);
					}
				}
			}
		}
		return per;
	}
	@Scheduled(fixedRate=10000)
	public void checkPlayersPresence() {
		logger.info("Checking player presence...");
		DateTime now = new DateTime();
		Set<UUID> playersToRemoveHBs = new HashSet<UUID>();
		for (UUID playerId : getPlayerHeartbeats().keySet()) {
			if (now.minusSeconds(getAllowedInactiveTimeInSeconds()).isAfter(new DateTime(getPlayerHeartbeats().get(playerId)))) {
				logger.warn("Player :" + playerId + " hasn't checked for over " + getAllowedInactiveTimeInSeconds() + " seconds. Removing from lobby...");
				removePlayerFromLobby(playerId);
				playersToRemoveHBs.add(playerId);
			}
		}
		for (UUID uuid : playersToRemoveHBs) {
			getPlayerHeartbeats().remove(uuid);
		}
		
	}
	
	private void removePlayerFromLobby(UUID playerId){
		for (CardGame cg : lobbyImpl.getCardGames().values()) {
			for (PlayerHand ph : cg.getPlayers()) {
				if (ph.getPlayer().getUuid().equals(playerId)) {
					cg.getPlayers().remove(ph);
				}
				 
			}
		}
		Set<UUID> tablesToRemove = new HashSet<UUID>();
		for (Table t : lobbyImpl.getWaitingTables().values()) {
			for (Player p : t.getPlayersWaiting()) {
				if (p.getUuid().equals(playerId)) {
					t.getPlayersWaiting().remove(p);
					break;
				}
			}
			if (t.getPlayersWaiting().size()==0) {
				tablesToRemove.add(t.getId());
			}
		}
		for (UUID idToRemove : tablesToRemove) {
			lobbyImpl.getWaitingTables().remove(idToRemove);
		}
		
		
		
	}
	public Date playerHeartbeat(UUID playerId) {
		Date d = new Date();
		getPlayerHeartbeats().put(playerId, (Long)d.getTime());
		return d;
	}
	private Map<UUID, Long> getPlayerHeartbeats() {
		if (playerHeartbeats == null) {
			playerHeartbeats = new HashMap<UUID, Long>();
		}
		return playerHeartbeats;
	}

	public int getAllowedInactiveTimeInSeconds() {
		return allowedInactiveTimeInSeconds;
	}

	public void setAllowedInactiveTimeInSeconds(int allowedInactiveTimeInSeconds) {
		this.allowedInactiveTimeInSeconds = allowedInactiveTimeInSeconds;
	}



}
