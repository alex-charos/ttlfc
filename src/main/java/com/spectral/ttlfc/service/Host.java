package com.spectral.ttlfc.service;

import java.util.Date;
import java.util.UUID;

import com.spectral.ttlfc.model.Player;
import com.spectral.ttlfc.model.PlayerEntryResponse;

public interface Host {
	
	PlayerEntryResponse acceptPlayer(Player p);
	PlayerEntryResponse getPlayerStatus(UUID playerUUID);
	void checkPlayersPresence();
	Date playerHeartbeat(UUID playerId);
}
