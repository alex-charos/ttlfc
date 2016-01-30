package com.spectral.ttlfc.service.impl;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.spectral.ttlfc.model.Player;
import com.spectral.ttlfc.service.CardGame;
import com.spectral.ttlfc.service.Lobby;

@Component(value="lobbyImpl")
public class LobbyImpl implements Lobby {
	private Map<UUID, Player> waitingRoom;
	private Map<UUID, CardGame> cardGames;

	public UUID addPlayerInWaitingRoom(Player player) {
		UUID uuid = UUID.randomUUID();
		
		getWaitingRoom().put(uuid, player);
		return uuid;
	}

	public Map<UUID, Player> getWaitingRoom() {
		if (waitingRoom == null) {
			waitingRoom = new ConcurrentHashMap<UUID, Player>();
		} 
		return waitingRoom;
	}

	public UUID createCardGame(CardGame game) {
		UUID gameId = UUID.randomUUID();
		getCardGames().put(gameId, game);
		return gameId;
	}

	public Map<UUID, CardGame> getCardGames() {
		if (cardGames == null) {
			cardGames = new ConcurrentHashMap<UUID, CardGame>();
		}
		return cardGames;
	}

}
