package com.spectral.ttlfc.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spectral.ttlfc.model.Player;
import com.spectral.ttlfc.model.PlayerEntryRequest;
import com.spectral.ttlfc.model.PlayerEntryResponse;
import com.spectral.ttlfc.model.PlayerHand;
import com.spectral.ttlfc.model.Table;
import com.spectral.ttlfc.model.Trick;
import com.spectral.ttlfc.service.CardGame;
import com.spectral.ttlfc.service.Host;
import com.spectral.ttlfc.service.Lobby;

@RestController
@RequestMapping("/tt")
@CrossOrigin
public class GameController {
	@Autowired
	Host hostImpl;
	
	@Autowired
	Lobby lobbyImpl;
	
	
	@RequestMapping("/")
	public String index() {
		return "Welcome to the Top Trumps app!";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="enter-lobby")
	public PlayerEntryResponse playerEntry(@RequestBody PlayerEntryRequest per) {
		
		return hostImpl.acceptPlayer(per);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="get-waiting-tables")
	public Map<UUID, Table> getTables() {
		return lobbyImpl.getWaitingTables();
	}
	
	@RequestMapping(method=RequestMethod.GET, value="my-lobby-status/{uuid}")
	public PlayerEntryResponse getPlayerUpdate(@PathVariable UUID  uuid) {
		return hostImpl.getPlayerStatus(uuid);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="game/turn/{gameId}")
	public Map<String, Object> getPlayerTurnAndTrickHistory(@PathVariable UUID  gameId) {
		CardGame game = lobbyImpl.getCardGames().get(gameId);
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("turn", game.getPlayerTurn());
		ret.put("trickHistory", game.getTrickHistory());
		return ret;
	}
	
	@RequestMapping(method=RequestMethod.POST ,value="/game/{gameId}/trick/attribute/{attribute}")
	public Trick trick(@PathVariable UUID gameId, @PathVariable String attribute, @RequestBody Player player) {
		CardGame game = lobbyImpl.getCardGames().get(gameId);
		Trick t =  game.executeTrick(player, attribute);
		return t;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/game/{gameId}/view-hand/{playerUUID}")
	public PlayerHand viewHand(@PathVariable UUID gameId, @PathVariable UUID playerUUID) {
		CardGame game = lobbyImpl.getCardGames().get(gameId);
		PlayerHand phRet = null;
		for (PlayerHand ph : game.getPlayers()) {
			if (ph.getPlayer().getUuid().equals(playerUUID)) {
				phRet = ph;
				break;
			}
		}
		return phRet;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/heartbeat/player/{playerUUID}")
	public Date heartbeat( @PathVariable UUID playerUUID) {
		return hostImpl.playerHeartbeat(playerUUID);
	}
	 
	 
}
