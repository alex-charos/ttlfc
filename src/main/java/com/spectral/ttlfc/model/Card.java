package com.spectral.ttlfc.model;

import java.util.HashMap;
import java.util.Map;

public class Card {
	
	private String name;
	private String imageUrl;
	private Map<String, Double> attributes;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Map<String, Double> getAttributes() {
		if (attributes==null) {
			attributes = new HashMap<String, Double>();
		}
		return attributes;
	}

	public void setAttributes(Map<String, Double> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String toString() {
		return "Card [name=" + name + ", imageUrl=" + imageUrl + ", attributes=" + attributes + "]";
	}
	
	

}
