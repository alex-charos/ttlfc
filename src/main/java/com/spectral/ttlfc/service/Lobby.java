package com.spectral.ttlfc.service;

import java.util.Map;
import java.util.UUID;

import com.spectral.ttlfc.model.Player;
import com.spectral.ttlfc.model.Table;

public interface Lobby {
	Map<UUID, Table> getWaitingTables();
	UUID createCardGame(CardGame game);
	Map<UUID, CardGame> getCardGames();
	

}
