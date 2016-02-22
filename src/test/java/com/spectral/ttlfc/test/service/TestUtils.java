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
	public static PlayerEntryRequest getJoinTableTwoPlayers(Table t ) {
		Player p2 = new Player();
		p2.setEmail("next@test.gr");
		PlayerEntryRequest perReq2 = new PlayerEntryRequest();
		perReq2.setPlayer(p2);
		perReq2.setRequestType(EntryRequestType.joinTable);
		perReq2.setRequestTable(t);
		return perReq2;
	}

}
