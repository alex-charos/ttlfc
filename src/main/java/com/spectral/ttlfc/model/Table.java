package com.spectral.ttlfc.model;

import java.util.Deque;
import java.util.LinkedList;
import java.util.UUID;

public class Table {
	private UUID id;
	private int playersRequired;
	private Deque<Player> playersWaiting;
	
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public int getPlayersRequired() {
		return playersRequired;
	}
	public void setPlayersRequired(int playersRequired) {
		this.playersRequired = playersRequired;
	}
	public Deque<Player> getPlayersWaiting() {
		if (playersWaiting == null) {
			playersWaiting = new LinkedList<Player>();
		}
		return playersWaiting;
	}
	public void setPlayersWaiting(Deque<Player> playersWaiting) {
		this.playersWaiting = playersWaiting;
	}


}
