package com.spectral.ttlfc.test.service;

import static org.junit.Assert.assertEquals;

import java.util.Deque;
import java.util.LinkedList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.spectral.ttlfc.TopTrumps;
import com.spectral.ttlfc.model.Card;
import com.spectral.ttlfc.model.Player;
import com.spectral.ttlfc.model.PlayerHand;
import com.spectral.ttlfc.model.Trick;
import com.spectral.ttlfc.service.CardGame;
import com.spectral.ttlfc.service.impl.CardGameImpl;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(TopTrumps.class)
@WebAppConfiguration
@EnableAutoConfiguration
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
		Player playerToPlay = null;
		for (PlayerHand ph : g.getPlayers()) {
			int i =0;
			if (!ph.getPlayer().getEmail().equals("alex@alex.com")) {
				i=1*100;
				playerToPlay = ph.getPlayer();
			}   
			
			for (Card c : ph.getCards()) {
				c.getAttributes().put("testAttr", (double)i++);
			}
		 
			for (Card c : ph.getCards()) {
				c.getAttributes().put("testAttr", (double)i++);
			}
		}
		Trick tr =  g.executeTrick(playerToPlay,"testAttr" );
		System.out.println(tr);
		for (PlayerHand phzz : g.getPlayers()) {
			if ( phzz.getPlayer().getEmail().equals("alex@alex.com")) {
				assertEquals(4, phzz.getCards().size());
			} else {
				assertEquals(6, phzz.getCards().size());
			}
			 
		}
		
		
		
	}
	
	private CardGame getCardGame() {
		Deque<Player> players = getTwoPlayers();
		CardGame game = new CardGameImpl(players);
		game.dealDeck(getDeck(10));
		
		return game;
	}
	
	private void testDealCorrect(int positionToDeal) {
		Deque<Player> players = getTwoPlayers();
		CardGame game = new CardGameImpl(players);
		
		int i=0;
		PlayerHand dealer = null;
		for (PlayerHand p : game.getPlayers()) {
			if (i==positionToDeal) {
				p.setDealing(true);
				dealer = p;
			}
			i++;
		}
		game.dealDeck(getDeck(11));
		
		for (PlayerHand ph : game.getPlayers()) {
			if (ph.equals(dealer)) {
				assertEquals(5, ph.getCards().size());
				
			} else {
				assertEquals(6, ph.getCards().size());
			}
		}
	}
	
	
	
	
	
	
	private Deque<Player> getTwoPlayers(){
		Player p1 = new Player();
		p1.setEmail("alex@alex.com");
		Player p2 = new Player();
		p2.setEmail("christine@alex.com");
		
	 
		Deque<Player> players = new LinkedList<Player>();
		players.add(p1);
		players.add(p2);
		
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
