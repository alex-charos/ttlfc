package com.spectral.ttlfc.service;

import java.util.Deque;

import com.spectral.ttlfc.exception.NotYourTurnException;
import com.spectral.ttlfc.model.Card;
import com.spectral.ttlfc.model.Player;
import com.spectral.ttlfc.model.PlayerHand;
import com.spectral.ttlfc.model.TrickResult;
import com.spectral.ttlfc.utils.GameStatus;

public interface CardGame {
	
	public void dealDeck(Deque<Card> deck);
	public Player getPlayerTurn();
	public TrickResult executeTrick(Player p,String attribute) throws NotYourTurnException;
	public Deque<PlayerHand> getPlayers();
	public GameStatus getStatus();
}
