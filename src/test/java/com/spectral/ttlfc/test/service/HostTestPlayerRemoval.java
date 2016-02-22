package com.spectral.ttlfc.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;
import java.util.UUID;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.spectral.ttlfc.TopTrumps;
import com.spectral.ttlfc.factory.GameFactory;
import com.spectral.ttlfc.model.Player;
import com.spectral.ttlfc.model.PlayerEntryResponse;
import com.spectral.ttlfc.model.PlayerHand;
import com.spectral.ttlfc.service.CardGame;
import com.spectral.ttlfc.service.Host;
import com.spectral.ttlfc.service.Lobby;
import com.spectral.ttlfc.utils.CardGameType;
import com.spectral.ttlfc.utils.PlayerResponseType;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(TopTrumps.class)
@WebAppConfiguration
@EnableAutoConfiguration
public class HostTestPlayerRemoval {

	@Autowired
	Host hostImpl;
	
	@Autowired
	Lobby lobbyImpl;
 
//	@Test
//	public void testActivePlayerNotRemovedFromGames() {
//		Deque<Player> plz = new LinkedList<Player>();
//		
//		Player p1 = new Player();
//		p1.setUuid(UUID.randomUUID());
//		plz.add(p1);
//		
//		Player p2 = new Player();
//		p2.setUuid(UUID.randomUUID());
//		plz.add(p2);
//		
//		CardGame cg = GameFactory.getGame(CardGameType.standardCardGame	, plz);
//		hostImpl.acceptPlayer(p1);
//		hostImpl.acceptPlayer(p2);
////		hostImpl.playerHeartbeat(p1.getUuid());
////		hostImpl.playerHeartbeat(p2.getUuid());	
//		UUID uuid = UUID.randomUUID();
//		
//		lobbyImpl.getCardGames().put(uuid, cg);
//		
//		hostImpl.checkPlayersPresence();
//		assertEquals(2, lobbyImpl.getCardGames().get(uuid).getPlayers().size());
//	} 
//	@Test
//	public void testInactivePlayerRemovedFromGames() {
//		Deque<Player> plz = new LinkedList<Player>();
//		
//		Player p1 = new Player();
//		p1.setUuid(UUID.randomUUID());
//		plz.add(p1);
//		
//		Player p2 = new Player();
//		p2.setUuid(UUID.randomUUID());
//		plz.add(p2);
//		hostImpl.acceptPlayer(p1);
//		PlayerEntryResponse per= hostImpl.acceptPlayer(p2);
//		assertEquals(PlayerResponseType.enteredGame, per.getResponse());
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//		hostImpl.checkPlayersPresence();
//		assertEquals(0, lobbyImpl.getCardGames().get(per.getGameToken()).getPlayers().size());
//		
//	} 
}
