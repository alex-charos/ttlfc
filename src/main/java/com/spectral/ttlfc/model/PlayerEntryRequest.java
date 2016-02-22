package com.spectral.ttlfc.model;

import com.spectral.ttlfc.utils.EntryRequestType;

public class PlayerEntryRequest {
	private Player player;
	private EntryRequestType requestType;
	private Table requestTable; 
	
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public EntryRequestType getRequestType() {
		return requestType;
	}
	public void setRequestType(EntryRequestType requestType) {
		this.requestType = requestType;
	}
	public Table getRequestTable() {
		return requestTable;
	}
	public void setRequestTable(Table requestTable) {
		this.requestTable = requestTable;
	}
	
	
}
