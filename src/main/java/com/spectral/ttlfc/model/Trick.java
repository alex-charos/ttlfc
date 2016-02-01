package com.spectral.ttlfc.model;

import java.util.ArrayList;
import java.util.List;

public class Trick {
	
	private List<CardFaceOff> cards;
	private String attribute;
	private TrickResult result;
	private int id;
	 
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
	public TrickResult getResult() {
		return result;
	}
	public void setResult(TrickResult result) {
		this.result = result;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	

}
