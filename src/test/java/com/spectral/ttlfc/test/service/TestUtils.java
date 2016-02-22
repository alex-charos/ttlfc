package com.spectral.ttlfc.test.service;

import com.spectral.ttlfc.model.Player;
import com.spectral.ttlfc.model.PlayerEntryRequest;
import com.spectral.ttlfc.model.Table;
import com.spectral.ttlfc.utils.EntryRequestType;

public class TestUtils {
	
	public static PlayerEntryRequest getCreateTableTwoPlayers() {
		Player p = new Player();
		p.setEmail("real@test.gr");
		Table t = new Table();
		t.setPlayersRequired(2);
		
		PlayerEntryRequest perReq = new PlayerEntryRequest();
		perReq.setPlayer(p);
		perReq.setRequestTable(t);
		perReq.setRequestType(EntryRequestType.createTable);
		
		return perReq;
	}

}
