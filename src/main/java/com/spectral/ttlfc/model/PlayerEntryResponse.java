package com.spectral.ttlfc.model;

import java.util.UUID;

import com.spectral.ttlfc.utils.PlayerResponseType;

public class PlayerEntryResponse {
	private UUID token;
	private PlayerResponseType response;
	
	
	public UUID getToken() {
		return token;
	}
	public void setToken(UUID token) {
		this.token = token;
	}
	public PlayerResponseType getResponse() {
		return response;
	}
	public void setResponse(PlayerResponseType response) {
		this.response = response;
	}

}
