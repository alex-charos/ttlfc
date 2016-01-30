package com.spectral.ttlfc.test.service;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import com.spectral.ttlfc.model.Card;
import com.spectral.ttlfc.service.CardService;
import com.spectral.ttlfc.service.impl.FootballPlayerCardService;

public class CardServiceTest {
	
	@Test
	public void testGetCards(){
		CardService cs = new FootballPlayerCardService();
		assertEquals(10, cs.generateCards(10).size());
	}

}
