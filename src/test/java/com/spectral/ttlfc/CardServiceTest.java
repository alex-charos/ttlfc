package com.spectral.ttlfc;

import java.util.Set;

import org.junit.Test;

import com.spectral.ttlfc.model.Card;
import com.spectral.ttlfc.service.CardService;
import com.spectral.ttlfc.service.impl.PlayerCardService;

public class CardServiceTest {
	
	@Test
	public void testGetCards(){
		CardService cs = new PlayerCardService();
		Set<Card> cards = cs.generateCards();
	}

}
