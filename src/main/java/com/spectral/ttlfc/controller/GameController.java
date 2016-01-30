package com.spectral.ttlfc.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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
public class GameController {
	@Autowired
	Host hostImpl;
	
	@Autowired
	Lobby lobbyImpl;
	
	@Autowired
	CardService footballPlayerCardService;

	@RequestMapping("/")
	public String index() {
		return "Welcome to the Top Trumps app!";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/enter-lobby")
	public PlayerEntryResponse playerEntry(@RequestBody Player p) {
		
		return hostImpl.acceptPlayer(p);
	}
	@RequestMapping(method=RequestMethod.GET, value="/my-update/{uuid}")
	public PlayerEntryResponse getPlayerUpdate(@RequestParam UUID  uuid) {
		
		return hostImpl.getPlayerStatus(uuid);
	}
	
	@RequestMapping(value="/game/{gameId/}deal")
	public UUID deal(@RequestParam UUID gameId) {
		CardGame game = lobbyImpl.getCardGames().get(gameId);
		game.dealDeck(footballPlayerCardService.generateCards(20));
		return gameId;
	}
	@RequestMapping(value="/game/{gameId}/trick/attribute/{attribute}")
	public TrickResult trick(@RequestParam UUID gameId, @RequestParam String attribute, @RequestBody Player player) {
		CardGame game = lobbyImpl.getCardGames().get(gameId);
		TrickResult tr =  game.executeTrick(player, attribute);
		return tr;
	}
	
	@RequestMapping(value="/game/{gameId}/view-hand/{playerUUID}")
	public PlayerHand viewHand(@RequestParam UUID gameId, @RequestParam UUID playerUUID, @RequestBody Player player) {
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
