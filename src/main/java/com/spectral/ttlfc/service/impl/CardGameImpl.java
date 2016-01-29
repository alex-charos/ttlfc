package com.spectral.ttlfc.service.impl;

 
import java.util.Deque;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.spectral.ttlfc.model.Card;
import com.spectral.ttlfc.model.CardFaceOff;
import com.spectral.ttlfc.model.Player;
import com.spectral.ttlfc.model.PlayerHand;
import com.spectral.ttlfc.model.Trick;
import com.spectral.ttlfc.model.TrickResult;
import com.spectral.ttlfc.service.CardGame;
import com.spectral.ttlfc.utils.TrickOutcome;

public class CardGameImpl implements CardGame {
	
	Logger logger = Logger.getLogger(getClass());
	
	
	private Deque<PlayerHand> players;
	private Deque<Card> cardsOnHold;

	public void dealDeck(Deque<Card> deck, Deque<PlayerHand> players) {
		players = sortPlayers(players);
		players = dealCards(deck, players);
		setPlayers(players);
	}
	
	

	public PlayerHand getPlayerTurn() {
		return getPlayers().peek();
	}

	public TrickResult executeTrick(String attribute) {
	
		Trick t = setupTrick(attribute);
		
		
		PlayerHand winner = getWinner(t);
		winner = manageWinner(winner,t);
		
		removeLosers();
		
		TrickResult tr = getTrickResult(winner);
		
		PlayerHand next = getPlayers().remove();
		getPlayers().add(next);
		return tr;
	}
	
	private TrickResult getTrickResult(PlayerHand winner){
		TrickResult tr = new TrickResult();
		if (getPlayers().size()==1) {
			tr.setOutcome(TrickOutcome.gameWin);
			tr.setPlayer(getPlayers().iterator().next().getPlayer());
		} else {
			if (winner == null) {
				tr.setOutcome(TrickOutcome.roundDraw);
			} else {
				tr.setOutcome(TrickOutcome.roundWin);
				tr.setPlayer(winner.getPlayer());
			}
		}
		return tr;
		
	}
	
	private void removeLosers() {
		Deque<PlayerHand> phToRemove = new LinkedList<PlayerHand>();
		for (PlayerHand ph : getPlayers()) {
			if (ph.getCards().isEmpty()) {
				phToRemove.add(ph);
			}
		}
		
		
		getPlayers().removeAll(phToRemove);
	}
	
	
	private Trick setupTrick(String attribute) {
		Trick t = new Trick();
		t.setAttribute(attribute);
		for (PlayerHand ph : getPlayers()) {
			CardFaceOff cfo = new CardFaceOff();
			cfo.setPlayer(ph);
			cfo.setCard(ph.getCards().remove());
			t.getCards().add(cfo);
		}
		
		
		return t;
	}
	private PlayerHand manageWinner(PlayerHand winner, Trick t) {
		if (winner!=null) {
			logger.info(winner.getPlayer() + " Wins!!!");
			winner.getCards().addAll(getCardsOnHold());
			
			for (CardFaceOff cfo : t.getCards()) {
				winner.getCards().add(cfo.getCard());	
			}
			getCardsOnHold().clear();
			
		} else {
			for (CardFaceOff cfo : t.getCards()) {
				getCardsOnHold().add(cfo.getCard());
			}
			
		}
		return winner;
	}

	private PlayerHand getWinner(Trick t) {
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
				logger.info("Currently :" + winner + " wins");
			}
		}
		return winner;
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



	public Deque<Card> getCardsOnHold() {
		if (cardsOnHold==null) {
			cardsOnHold = new LinkedList<Card>();
		}
		return cardsOnHold;
	}



	public void setCardsOnHold(Deque<Card> cardsOnHold) {
		this.cardsOnHold = cardsOnHold;
	}

}
