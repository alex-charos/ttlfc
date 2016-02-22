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
import com.spectral.ttlfc.model.PlayerEntryRequest;
import com.spectral.ttlfc.model.PlayerEntryResponse;
import com.spectral.ttlfc.model.PlayerHand;
import com.spectral.ttlfc.model.Table;
import com.spectral.ttlfc.service.CardGame;
import com.spectral.ttlfc.service.Host;
import com.spectral.ttlfc.service.Lobby;
import com.spectral.ttlfc.utils.CardGameType;
import com.spectral.ttlfc.utils.EntryRequestType;
import com.spectral.ttlfc.utils.PlayerResponseType;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(TopTrumps.class)
@WebAppConfiguration
@EnableAutoConfiguration
public class HostTestPlayerAcceptance {

	@Autowired
	Host hostImpl;
	
	@Autowired
	Lobby lobbyImpl;
	
	@Test
	public void testAcceptPlayersInEmptyRoom(){
		PlayerEntryRequest perReq = TestUtils.getCreateTableTwoPlayers();
		PlayerEntryResponse per = hostImpl.acceptPlayer(perReq);
		assertNotNull(per);
		assertEquals(PlayerResponseType.inWaitingRoom, per.getResponse());
		assertEquals(1, lobbyImpl.getWaitingTables().size());
		assertEquals(1, lobbyImpl.getWaitingTables().values().iterator().next().getPlayersWaiting().size());
		
		PlayerEntryRequest perReq2 =TestUtils.getJoinTableTwoPlayers(perReq.getRequestTable());
		PlayerEntryResponse per2 = hostImpl.acceptPlayer(perReq2);
		assertNotNull(per2);
		assertEquals(PlayerResponseType.enteredGame, per2.getResponse());
		assertEquals(0, lobbyImpl.getWaitingTables().size());
		assertEquals(1, lobbyImpl.getCardGames().size());
		
	}
	 
}
