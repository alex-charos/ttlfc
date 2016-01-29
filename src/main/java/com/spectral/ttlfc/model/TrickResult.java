package com.spectral.ttlfc.model;

import com.spectral.ttlfc.utils.TrickOutcome;

public class TrickResult {
	
	private TrickOutcome outcome;
	private Player player;
	
	public TrickOutcome getOutcome() {
		return outcome;
	}
	public void setOutcome(TrickOutcome outcome) {
		this.outcome = outcome;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	@Override
	public String toString() {
		return "TrickResult [outcome=" + outcome + ", player=" + player + "]";
	}
	
	

}
