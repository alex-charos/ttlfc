package com.spectral.ttlfc.service;

import java.util.Date;
import java.util.UUID;

import com.spectral.ttlfc.model.Player;
import com.spectral.ttlfc.model.PlayerEntryRequest;
import com.spectral.ttlfc.model.PlayerEntryResponse;
import com.spectral.ttlfc.model.Table;

public interface Host {
	
	PlayerEntryResponse acceptPlayer(PlayerEntryRequest per);
	PlayerEntryResponse getPlayerStatus(UUID playerUUID);
	void checkPlayersPresence();
	Date playerHeartbeat(UUID playerId);
	UUID sitToTable(Table t,Player player);
	Table openTable(Player p);
}
