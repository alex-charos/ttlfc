package com.spectral.ttlfc.service.impl;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.spectral.ttlfc.model.Card;
import com.spectral.ttlfc.service.CardService;
import com.spectral.ttlfc.utils.HTTPUtil;

@Component("footballCardService")
public class FootballPlayerCardService implements CardService{
	
	private String apiUrl ="http://api.football-data.org/v1/soccerseasons";
	private String apiToken = "79e23fafd923491b91572cde3c9d41e3";
	private SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
	JsonParser parser = new JsonParser();
	public Deque<Card> generateCards(Integer totals) {
		
		Deque<Card> cards = new LinkedList<Card>();
		try {
			String seasonsJson = HTTPUtil.readUrl(apiUrl,apiToken);
			JsonArray seasons = parser.parse(seasonsJson).getAsJsonArray();
			for (JsonElement jseLeague : seasons) {
				String caption = jseLeague.getAsJsonObject().get("caption").getAsString();
				
				JsonElement jseLinks = jseLeague.getAsJsonObject().get("_links");
				String teamsUrl= jseLinks.getAsJsonObject().get("teams").getAsJsonObject().get("href").getAsString();
						
				System.out.println(caption);
				
				String teamsJson = HTTPUtil.readUrl(teamsUrl,apiToken);
				JsonArray teams = parser.parse(teamsJson).getAsJsonObject().get("teams").getAsJsonArray();
				for (JsonElement jseTeam : teams) {
					String name = jseTeam.getAsJsonObject().get("name").getAsString();
					String logo = jseTeam.getAsJsonObject().get("crestUrl").getAsString();
					JsonElement jseTLinks = jseTeam.getAsJsonObject().get("_links");
					String playersUrl= jseTLinks.getAsJsonObject().get("players").getAsJsonObject().get("href").getAsString();
					System.out.println(name);
					System.out.println(playersUrl);
					String playersJson = HTTPUtil.readUrl(playersUrl,apiToken);
					JsonArray players = parser.parse(playersJson).getAsJsonObject().get("players").getAsJsonArray();
					for (JsonElement jsePlayer : players) {
						try {
					
							JsonObject jsoPlayer = jsePlayer.getAsJsonObject();
							String pName = jsoPlayer.get("name").getAsString();
							Integer jerseyNumber = jsoPlayer.get("jerseyNumber").getAsInt();
							String marketValue = jsoPlayer.get("marketValue").getAsString();
							marketValue = marketValue.substring(0, marketValue.indexOf(" ")).replace(",", "");
							Double mktVal = Double.parseDouble(marketValue);
							
							String dobStr = jsoPlayer.get("dateOfBirth").getAsString();
							Date d =sdf.parse(dobStr);
							Date dNow = new Date();
							
							long diffInYears = ((((dNow.getTime() - d.getTime())/1000)/3600)/24)/365;
							  
							Card c = new Card();
							c.setName(pName);
							c.setImageUrl(logo);
							c.getAttributes().put("jerseyNumber", jerseyNumber.doubleValue());
							c.getAttributes().put("marketValue", mktVal);
							c.getAttributes().put("ageInYears", (double)diffInYears);
							
							cards.add(c);
							if (totals!=null && cards.size() >= totals) {
								return cards;
							}
						} catch (Exception ex) {
							System.out.println(ex.getMessage());
						}
					}
				}
				
				
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cards;
	}
	
	private Deque<Card> shuffleCards(LinkedList<Card> cards){
		 Collections.shuffle(cards);
		Deque<Card> shuffledQ = new LinkedList<Card>();
		for (Card c : cards) {
			shuffledQ.push(c);
		}
		
		return shuffledQ;
	}

}
