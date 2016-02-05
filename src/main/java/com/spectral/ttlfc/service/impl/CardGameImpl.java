package com.spectral.ttlfc.service.impl;

 
import java.util.Deque;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.spectral.ttlfc.exception.NotYourTurnException;
import com.spectral.ttlfc.model.Card;
import com.spectral.ttlfc.model.CardFaceOff;
import com.spectral.ttlfc.model.Player;
import com.spectral.ttlfc.model.PlayerHand;
import com.spectral.ttlfc.model.Trick;
import com.spectral.ttlfc.model.TrickResult;
import com.spectral.ttlfc.service.CardGame;
import com.spectral.ttlfc.utils.GameStatus;
import com.spectral.ttlfc.utils.TrickOutcome;

public class CardGameImpl extends CardGame {
	
	Logger logger = Logger.getLogger(getClass());
	private int trickCount = 0;
	private GameStatus status;
	private Deque<PlayerHand> players;
	private Deque<Card> cardsOnHold;
	private Deque<Trick> trickHistory;

	public CardGameImpl(Deque<Player> players) {
		super(players);
		status = GameStatus.waitingToDeal;
	}
	
	public void dealDeck(Deque<Card> deck) {
		players = sortPlayers(players);
		players = dealCards(deck, players);
		setPlayers(players);
		status = GameStatus.waitingToDeal;
	}


	public Trick executeTrick(Player p, String attribute) throws NotYourTurnException {
		if (!p.equals(getPlayerTurn())) {
			throw new NotYourTurnException();
		}
		Trick t = setupTrick(attribute);
		
		PlayerHand winner = getWinner(t);
		
		winner = manageWinner(winner,t);
		removeLosers();
		
		TrickResult tr = getTrickResult(winner);
		t.setResult(tr);
		getTrickHistory().add(t);
		PlayerHand next = getPlayers().remove();
		getPlayers().add(next);
		return t;
	}
	
	public Player getPlayerTurn() {
		return getPlayers().peek().getPlayer();
	}
	public GameStatus getStatus() {
		return status;
	}
	
	private TrickResult getTrickResult(PlayerHand winner){
		TrickResult tr = new TrickResult();
		if (getPlayers().size()==1) {
			tr.setOutcome(TrickOutcome.gameWin);
			tr.setPlayer(getPlayers().iterator().next().getPlayer());
			status = GameStatus.finished;
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
		t.setId(trickCount++);
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
			if (currentValue!= null  && currentValue.equals(winningValue)) {
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
		if (players == null) {
			players = new LinkedList<PlayerHand>();
		}
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

	@Override
	public Deque<Trick> getTrickHistory() {
		if (trickHistory==null) {
			trickHistory = new LinkedList<Trick>();
		}
		return trickHistory;
	}
	

}
