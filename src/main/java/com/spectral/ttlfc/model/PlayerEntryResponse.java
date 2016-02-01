package com.spectral.ttlfc.model;

import java.util.UUID;

import com.spectral.ttlfc.utils.PlayerResponseType;

public class PlayerEntryResponse {
	private UUID playerToken;
	private UUID gameToken;
	private PlayerResponseType response;
	
	public PlayerResponseType getResponse() {
		return response;
	}
	public void setResponse(PlayerResponseType response) {
		this.response = response;
	}
	public UUID getGameToken() {
		return gameToken;
	}
	public void setGameToken(UUID gameToken) {
		this.gameToken = gameToken;
	}
	public UUID getPlayerToken() {
		return playerToken;
	}
	public void setPlayerToken(UUID playerToken) {
		this.playerToken = playerToken;
	}
	

}
