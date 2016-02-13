package com.spectral.ttlfc.test.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.spectral.ttlfc.TopTrumps;
import com.spectral.ttlfc.service.CardService;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(TopTrumps.class)
@WebAppConfiguration
@EnableAutoConfiguration
public class CardServiceTest {
	
	@Autowired
	CardService cardService;
	
	
	@Test
	public void testGetCards(){
		
		assertEquals(10, cardService.generateCards(10).size());
	}

}
