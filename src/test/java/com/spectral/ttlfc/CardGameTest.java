package com.spectral.ttlfc;

import static org.junit.Assert.assertEquals;

import java.util.Deque;
import java.util.LinkedList;

import org.junit.Test;

import com.spectral.ttlfc.model.Card;
import com.spectral.ttlfc.model.Player;
import com.spectral.ttlfc.model.PlayerHand;
import com.spectral.ttlfc.service.CardGame;
import com.spectral.ttlfc.service.impl.CardGameImpl;

public class CardGameTest {
	
	@Test
	public void testDealEqualCards() {
		CardGame game = getCardGame();
		
		assertEquals(5, game.getPlayers().pop().getCards().size());
		assertEquals(5, game.getPlayers().pop().getCards().size());
	}
	
	
	@Test
	public void testDealNotEqualCardsDealerFirst() {
		testDealCorrect(0);
	}
	@Test
	public void testDealNotEqualCardsDealerSecond() {
		testDealCorrect(1);
	}
	@Test
	public void testGame() {
		CardGame g = getCardGame();
		PlayerHand ph = g.getPlayerTurn();
		System.out.println(ph.getPlayer() + " Plays...");
		
		g.executeTrick("attr 1");
		
		
		
		
	}
	
	private CardGame getCardGame() {
		CardGame game = new CardGameImpl();
		
		Deque<PlayerHand> players = getTwoPlayerHands();
		game.dealDeck(getDeck(10), players);
		
		return game;
	}
	
	private void testDealCorrect(int positionToDeal) {
		CardGame game = new CardGameImpl();
		
		Deque<PlayerHand> players = getTwoPlayerHands();
		int i=0;
		PlayerHand dealer = null;
		for (PlayerHand p : players) {
			if (i==positionToDeal) {
				p.setDealing(true);
				dealer = p;
			}
			i++;
		}
		game.dealDeck(getDeck(11), players);
		
		for (PlayerHand ph : game.getPlayers()) {
			if (ph.equals(dealer)) {
				assertEquals(5, ph.getCards().size());
				
			} else {
				assertEquals(6, ph.getCards().size());
			}
		}
	}
	
	
	
	
	
	
	private Deque<PlayerHand> getTwoPlayerHands(){
		Player p1 = new Player();
		p1.setEmail("alex@alex.com");
		Player p2 = new Player();
		p2.setEmail("christine@alex.com");
		
		PlayerHand ph1 = new PlayerHand(p1);
		PlayerHand ph2 = new PlayerHand(p2);
		
		
		Deque<PlayerHand> players = new LinkedList<PlayerHand>();
		players.add(ph1);
		players.add(ph2);
		
		return players;
	}
	private Deque<Card> getDeck(int deckSize){
		
		Deque<Card> deck = new LinkedList<Card>();
		
		for (int i=0; i< deckSize; i++) {
			Card c = new Card();
			c.setName("Card " + i);
			for (int j=0; j < 4; j++) {
				c.getAttributes().put("attr " +j ,1 + (double)(Math.random() * 100) );
			}
			deck.add(c);
		}
		
		return deck;
		
	}

}
