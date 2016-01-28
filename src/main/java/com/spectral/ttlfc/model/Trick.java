package com.spectral.ttlfc.model;

import java.util.ArrayList;
import java.util.List;

public class Trick {
	
	private List<CardFaceOff> cards;
	private String attribute;
	 
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public List<CardFaceOff> getCards() {
		if (cards == null) {
			cards = new ArrayList<CardFaceOff>();
		}
		return cards;
	}
	public void setCards(List<CardFaceOff> cards) {
		this.cards = cards;
	}
	

}
