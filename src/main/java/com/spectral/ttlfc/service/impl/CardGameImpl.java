package com.spectral.ttlfc.service.impl;

 
import java.util.Deque;

import com.spectral.ttlfc.model.Card;
import com.spectral.ttlfc.model.CardFaceOff;
import com.spectral.ttlfc.model.PlayerHand;
import com.spectral.ttlfc.model.Trick;
import com.spectral.ttlfc.service.CardGame;

public class CardGameImpl implements CardGame {
	
	private Deque<PlayerHand> players;

	public void dealDeck(Deque<Card> deck, Deque<PlayerHand> players) {
		players = sortPlayers(players);
		players = dealCards(deck, players);
		setPlayers(players);
	}
	
	

	public PlayerHand getPlayerTurn() {
		return getPlayers().peek();
	}

	public void executeTrick(String attribute) {
		Trick t = new Trick();
		t.setAttribute(attribute);
		for (PlayerHand ph : getPlayers()) {
			CardFaceOff cfo = new CardFaceOff();
			cfo.setPlayer(ph);
			cfo.setCard(ph.getCards().remove());
			t.getCards().add(cfo);
		}
		
		PlayerHand winner = null;
		Double winningValue = null;
		for (CardFaceOff cfo :t.getCards()) {
			Double currentValue = cfo.getCard().getAttributes().get(t.getAttribute());
			if (winningValue != null && currentValue == winningValue) {
				System.out.println("Equal value, nobody wins");
				winner = null;
			}
			System.out.println(cfo.getPlayer().getPlayer() +" has... " + currentValue);
			if (winningValue==null || currentValue > winningValue) {
				winningValue = currentValue;
				winner = cfo.getPlayer();
				System.out.println("Currently :" +winner + " wins");
			}
			
		}
		System.out.println(winner + " Wins!!!");
		if (winner!=null) {
			for (CardFaceOff cfo : t.getCards()) {
				winner.getCards().add(cfo.getCard());	
			}
		}
		
		PlayerHand next = getPlayers().remove();
		getPlayers().add(next);
	}

	
	private Deque<PlayerHand> dealCards(Deque<Card> deck, Deque<PlayerHand> players) {
		boolean startDealing = false;
		while (!deck.isEmpty()) {
			for (PlayerHand ph : players) {

				if (startDealing) {
					if (!deck.isEmpty()) {
						ph.getCards().add(deck.pop());
					}
				}
				if (ph.isDealing()) {
					startDealing = true;
				}
			}
			if (!startDealing) {
				startDealing = true;
			}
		}
		return players;
	}

	private Deque<PlayerHand> sortPlayers(Deque<PlayerHand> players) {
		boolean sorted = false;
		int i = 0;
		while (!sorted && i < players.size()) {

			PlayerHand ph = players.poll();
			if (!sorted) {
				if (ph.isDealing()) {
					sorted = true;
				}
				players.add(ph);
			}
			i++;
		}
		
		return players;
	}
	
	public Deque<PlayerHand> getPlayers() {
		return players;
	}
	public void setPlayers(Deque<PlayerHand> players) {
		this.players = players;
	}

}
