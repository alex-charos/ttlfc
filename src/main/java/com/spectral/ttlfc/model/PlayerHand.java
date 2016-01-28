package com.spectral.ttlfc.model;

import java.util.Deque;
import java.util.LinkedList;

public class PlayerHand {
	
	
	private Player player;
	private Deque<Card> cards;
	private boolean dealing;
	
	
	public PlayerHand(Player p) {
		this.player = p;
	}
	
	public Player getPlayer() {
		 
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public Deque<Card> getCards() {
		if (cards == null) {
			cards = new LinkedList<Card>();
		}
		return cards;
	}
	public void setCards(Deque<Card> cards) {
		this.cards = cards;
	}

	public boolean isDealing() {
		return dealing;
	}

	public void setDealing(boolean dealing) {
		this.dealing = dealing;
	}
 
	@Override
	public String toString() {
		return "PlayerHand [player=" + player + ", cards=" + cards + ", dealing=" + dealing + "]";
	}

}
