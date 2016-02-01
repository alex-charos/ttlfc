package com.spectral.ttlfc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

	public static void main(String args[]) {
		// System.out.println(popchar("aabbba"));
		// System.out.println(sequence(5));
		//System.out.println(scoringStrings("The word needlessly contains two sets of double letters"));
		
		System.out.println(binaryGap(1376796946));
	}

	static int binaryGap(int n) {
		String binaryRep = Integer.toBinaryString(n);
		boolean isSearchingForGap = false;
		int maxGap = 0;
		int currGap = 0;
		for (Character c : binaryRep.toCharArray()) {
			if (c == (char)'1') {
				if (isSearchingForGap) {
					if (currGap > maxGap) {
						maxGap = currGap;
						
					}
					currGap=0;
				} else {
					isSearchingForGap = !isSearchingForGap;
				}
			} else {
				currGap++;
			}
		}

		return maxGap;
	}

	static int scoringStrings(String input) {
		int score = 0;
		String[] words = input.split(" ");
		for (String word : words) {
			int wordScore = 0;
			int wordMultiplier = 1;
			Character prevChar = null;
			for (Character ch : word.toCharArray()) {
				int intValue = 0;
				if ((char) ch >= 65 && ((char) ch <= 90)) {
					intValue = (char) ch - 64;
				}
				if ((char) ch >= 97 && ((char) ch <= 122)) {
					intValue = (char) ch - 96;
				}
				int tempscore = intValue;
				if (prevChar != null && ch.equals(prevChar)) {
					wordMultiplier++;
				}

				wordScore += tempscore;
				prevChar = ch;
			}
			for (int i = 1; i < wordMultiplier; i++) {
				wordScore = wordScore * 2;
			}
			score += (wordScore * wordMultiplier);
		}

		return score;
	}

	static int palindromes(String input) {
		if (input == null || input.length() == 0) {
			return 0;
		}
		String firstHalf = input.substring(0, input.length() / 2);
		int count = 0;
		for (Character ch : firstHalf.toCharArray()) {
			if (!ch.equals(input.charAt(input.length() - (1 + count)))) {
				return 0;
			}
			count++;
		}
		return 1;
	}

	/*
	 * Complete the function below.
	 */
	static int sequence(int input) {
		int prev1 = 0;
		int prev2 = 0;
		int sum = prev2 + prev1;
		for (int i = 0; i <= input; i++) {
			if (i == 1) {
				prev1 = 1;
			}
			sum = prev1 + prev2;
			prev1 = prev2;
			prev2 = sum;
		}

		return sum;
	}

	static String popchar(String input) {
		Map<Character, Integer> counts = new HashMap<Character, Integer>();
		for (char ch : input.toCharArray()) {
			Integer count = counts.get(ch);
			if (count == null) {
				count = 0;
			}
			count++;
			counts.put(ch, count);
		}

		Character charToret = null;
		Integer countMost = 0;
		for (Character ch : counts.keySet()) {
			Integer currentCount = counts.get(ch);
			if (currentCount > countMost) {
				charToret = ch;
				countMost = currentCount;
			}
			if (currentCount == countMost && (charToret != null && charToret.compareTo(ch) > 0)) {
				charToret = ch;
			}

		}

		return charToret.toString();
	}

}
