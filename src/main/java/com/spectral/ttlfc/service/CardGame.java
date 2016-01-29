package com.spectral.ttlfc.service;

import java.util.Deque;

import com.spectral.ttlfc.model.Card;
import com.spectral.ttlfc.model.PlayerHand;
import com.spectral.ttlfc.model.TrickResult;

public interface CardGame {
	
	public void dealDeck(Deque<Card> deck, Deque<PlayerHand> players);
	public PlayerHand getPlayerTurn();
	public TrickResult executeTrick(String attribute);
	public Deque<PlayerHand> getPlayers();

}
