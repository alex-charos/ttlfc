package com.spectral.ttlfc;

import org.junit.Test;

import com.spectral.ttlfc.service.CardService;
import com.spectral.ttlfc.service.impl.PlayerCardService;

public class CardServiceTest {
	
	@Test
	public void testGetCards(){
		CardService cs = new PlayerCardService();
		cs.generateCards();
	}

}
