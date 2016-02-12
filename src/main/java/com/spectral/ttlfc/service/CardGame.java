package com.spectral.ttlfc.service;

import java.util.Deque;
import java.util.LinkedList;

import com.spectral.ttlfc.exception.NotYourTurnException;
import com.spectral.ttlfc.model.Card;
import com.spectral.ttlfc.model.Player;
import com.spectral.ttlfc.model.PlayerHand;
import com.spectral.ttlfc.model.Trick;
import com.spectral.ttlfc.utils.GameStatus;

public abstract class CardGame {
	protected Deque<PlayerHand> players;
	public CardGame(Deque<Player> players) {
		int i =0;
		if (players !=null){
			for (Player p : players) {
				PlayerHand ph = new PlayerHand(p);
				if (i==0) {
					ph.setDealing(true);
				}
				getPlayers().add(ph);
				i++;
			}
		}
	}
	public Deque<PlayerHand> getPlayers() {
		if (players == null) {
			players = new LinkedList<PlayerHand>();
		}
		return players;
	}
	public void setPlayers(Deque<PlayerHand> players) {
		this.players = players;
	}



	
	
	public abstract void dealDeck(Deque<Card> deck);
	public abstract Player getPlayerTurn();
	public abstract Trick executeTrick(Player p,String attribute) throws NotYourTurnException;
	public abstract GameStatus getStatus();
	public abstract Deque<Trick> getTrickHistory();
	
}
