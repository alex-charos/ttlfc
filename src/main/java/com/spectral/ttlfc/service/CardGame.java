package com.spectral.ttlfc.service;

import java.util.Deque;

import com.spectral.ttlfc.exception.NotYourTurnException;
import com.spectral.ttlfc.model.Card;
import com.spectral.ttlfc.model.Player;
import com.spectral.ttlfc.model.PlayerHand;
import com.spectral.ttlfc.model.TrickResult;
import com.spectral.ttlfc.utils.GameStatus;

public abstract class CardGame {
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
	public abstract void dealDeck(Deque<Card> deck);
	public abstract Player getPlayerTurn();
	public abstract TrickResult executeTrick(Player p,String attribute) throws NotYourTurnException;
	public abstract Deque<PlayerHand> getPlayers();
	public abstract GameStatus getStatus();
	
}
