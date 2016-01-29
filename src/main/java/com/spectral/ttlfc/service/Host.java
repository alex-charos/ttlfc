package com.spectral.ttlfc.service;

import com.spectral.ttlfc.model.Player;
import com.spectral.ttlfc.model.PlayerEntryResponse;

public interface Host {
	
	PlayerEntryResponse acceptPlayer(Player p);
	

}
