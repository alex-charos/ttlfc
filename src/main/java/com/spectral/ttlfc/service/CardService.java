package com.spectral.ttlfc.service;

import java.util.Deque;
import java.util.Set;

import com.spectral.ttlfc.model.Card;

public interface CardService {
	
	Deque<Card> generateCards(Integer total);

}
