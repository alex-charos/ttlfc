package com.spectral.ttlfc.service.impl;

import java.util.Deque;
import java.util.LinkedList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spectral.ttlfc.model.Player;
import com.spectral.ttlfc.model.PlayerEntryResponse;
import com.spectral.ttlfc.model.PlayerHand;
import com.spectral.ttlfc.service.CardGame;
import com.spectral.ttlfc.service.Host;
import com.spectral.ttlfc.service.Lobby;
import com.spectral.ttlfc.utils.PlayerResponseType;


@Component(value="hostImpl")
public class HostImpl implements Host {
	@Autowired
	Lobby lobbyImpl;
	
	public PlayerEntryResponse acceptPlayer(Player p) {
		p.setUuid(UUID.randomUUID());
		PlayerEntryResponse per = new PlayerEntryResponse();
		per.setToken(p.getUuid());
		if (lobbyImpl.getWaitingRoom().isEmpty()) {
			per.setResponse(PlayerResponseType.inWaitingRoom);
			lobbyImpl.addPlayerInWaitingRoom(p);
		} else {
			UUID playerUUIDToMatch = lobbyImpl.getWaitingRoom().keySet().iterator().next();
			Player playerToMatch = lobbyImpl.getWaitingRoom().remove(playerUUIDToMatch);
			Deque<Player> players = new LinkedList<Player>();
			players.add(playerToMatch);
			players.add(p);
			CardGame cg = new CardGameImpl(players);
			
			lobbyImpl.createCardGame(cg);
			per.setResponse(PlayerResponseType.enteredGame);
		}
		return per;
	}

	public PlayerEntryResponse getPlayerStatus(UUID playerUUID) {
		PlayerEntryResponse per = new PlayerEntryResponse();
		if (lobbyImpl.getWaitingRoom().keySet().contains(playerUUID)) {
			per.setResponse(PlayerResponseType.inWaitingRoom);
			per.setToken(playerUUID);
		} else {
			for (UUID uuid : lobbyImpl.getCardGames().keySet()) {
				for ( PlayerHand pPlaying :lobbyImpl.getCardGames().get(uuid).getPlayers()) {
					if (pPlaying.getPlayer().getUuid().equals(playerUUID)) {
						per.setResponse(PlayerResponseType.enteredGame);
						per.setToken(uuid);
					}
				}
			}
		}
		return per;
	}

}
