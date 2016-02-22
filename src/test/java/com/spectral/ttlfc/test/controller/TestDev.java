package com.spectral.ttlfc.test.controller;

import com.spectral.ttlfc.model.Player;

public class TestDev {

	public static void change(Player p) {
		
		p.setEmail("changed");
		p = new Player();
		
	}
	public static void main(String[] args) {
		Player p = new Player();
		p.setEmail("main");
		change(p);
		System.out.println(p.getEmail());
		
	}
}
