package com.spectral.ttlfc.service;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.spectral.ttlfc.model.Player;

public interface Lobby {
	UUID addPlayerInWaitingRoom(Player player);
	Map<UUID, Player> getWaitingRoom();
	
	UUID createCardGame(CardGame game);
	Map<UUID, CardGame> getCardGames();
	

}
