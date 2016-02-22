package com.spectral.ttlfc.service.impl;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.spectral.ttlfc.model.Table;
import com.spectral.ttlfc.service.CardGame;
import com.spectral.ttlfc.service.Lobby;

@Component(value="lobbyImpl")
public class LobbyImpl implements Lobby {
	private Map<UUID, Table> waitingTables;
	private Map<UUID, CardGame> cardGames;

	 
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

	 

	public Map<UUID, Table> getWaitingTables() {
		if (waitingTables == null) {
			waitingTables = new ConcurrentHashMap<UUID, Table>();
			
		}
		// TODO Auto-generated method stub
		return waitingTables;
	}

}
