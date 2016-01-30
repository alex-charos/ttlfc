package com.spectral.ttlfc.factory;

import java.util.Deque;

import com.spectral.ttlfc.model.Player;
import com.spectral.ttlfc.service.CardGame;
import com.spectral.ttlfc.service.impl.CardGameImpl;
import com.spectral.ttlfc.utils.CardGameType;

public class GameFactory {
	
	public static CardGame getGame(CardGameType type, Deque<Player> players) {
		CardGame game = null;
	        switch (type) {
	        case standardCardGame:
	        	game = new CardGameImpl(players);
	            break;
	 
	        default:
	        	game = new CardGameImpl(players);
	            break;
	        }
	        return game;
	}

}
