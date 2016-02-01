package com.spectral.ttlfc.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spectral.ttlfc.model.Player;
import com.spectral.ttlfc.model.PlayerEntryResponse;
import com.spectral.ttlfc.model.PlayerHand;
import com.spectral.ttlfc.model.TrickResult;
import com.spectral.ttlfc.service.CardGame;
import com.spectral.ttlfc.service.CardService;
import com.spectral.ttlfc.service.Host;
import com.spectral.ttlfc.service.Lobby;

@RestController
@RequestMapping("/tt")
public class GameController {
	@Autowired
	Host hostImpl;
	
	@Autowired
	Lobby lobbyImpl;
	
	@Autowired
	CardService footballPlayerCardService;

	@RequestMapping("/")
	public String index() {
		
		String s = "sfsdf";
		
		return "Welcome to the Top Trumps app!";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="enter-lobby")
	public PlayerEntryResponse playerEntry(@RequestBody Player p) {
		
		return hostImpl.acceptPlayer(p);
	}
	@RequestMapping(method=RequestMethod.GET, value="/my-update/{uuid}")
	public PlayerEntryResponse getPlayerUpdate(@RequestParam UUID  uuid) {
		
		return hostImpl.getPlayerStatus(uuid);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/game/{gameId}/deal")
	public UUID deal(@PathVariable UUID gameId) {
		CardGame game = lobbyImpl.getCardGames().get(gameId);
		if (game!=null) {
			game.dealDeck(footballPlayerCardService.generateCards(20));
		}
		return gameId;
	}
	@RequestMapping(method=RequestMethod.POST ,value="/game/{gameId}/trick/attribute/{attribute}")
	public TrickResult trick(@PathVariable UUID gameId, @PathVariable String attribute, @RequestBody Player player) {
		CardGame game = lobbyImpl.getCardGames().get(gameId);
		TrickResult tr =  game.executeTrick(player, attribute);
		return tr;
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
	 
}
