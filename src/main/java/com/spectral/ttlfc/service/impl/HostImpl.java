package com.spectral.ttlfc.service.impl;

import java.util.Deque;
import java.util.LinkedList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spectral.ttlfc.factory.GameFactory;
import com.spectral.ttlfc.model.Player;
import com.spectral.ttlfc.model.PlayerEntryResponse;
import com.spectral.ttlfc.model.PlayerHand;
import com.spectral.ttlfc.service.CardGame;
import com.spectral.ttlfc.service.CardService;
import com.spectral.ttlfc.service.Host;
import com.spectral.ttlfc.service.Lobby;
import com.spectral.ttlfc.utils.CardGameType;
import com.spectral.ttlfc.utils.PlayerResponseType;


@Component(value="hostImpl")
public class HostImpl implements Host {
	@Autowired
	Lobby lobbyImpl;
	
	@Autowired
	CardService footballPlayerCardService;

	
	private CardGameType gameType = CardGameType.standardCardGame;
	
	public PlayerEntryResponse acceptPlayer(Player p) {
		p.setUuid(UUID.randomUUID());
		PlayerEntryResponse per = new PlayerEntryResponse();
		per.setPlayerToken(p.getUuid());
		if (lobbyImpl.getWaitingRoom().isEmpty()) {
			per.setResponse(PlayerResponseType.inWaitingRoom);
			lobbyImpl.addPlayerInWaitingRoom(p);
		} else {
			UUID playerUUIDToMatch = lobbyImpl.getWaitingRoom().keySet().iterator().next();
			Player playerToMatch = lobbyImpl.getWaitingRoom().remove(playerUUIDToMatch);
			Deque<Player> players = new LinkedList<Player>();
			players.add(playerToMatch);
			players.add(p);
			CardGame cg =  GameFactory.getGame(gameType, players);
			cg.dealDeck(footballPlayerCardService.generateCards(10));

			UUID gameId = lobbyImpl.createCardGame(cg);
			per.setGameToken(gameId);
			per.setResponse(PlayerResponseType.enteredGame);
		}
		return per;
	}

	public PlayerEntryResponse getPlayerStatus(UUID playerUUID) {
		PlayerEntryResponse per = new PlayerEntryResponse();
		per.setPlayerToken(playerUUID);
		if (lobbyImpl.getWaitingRoom().keySet().contains(playerUUID)) {
			per.setResponse(PlayerResponseType.inWaitingRoom);
		} else {
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

}
