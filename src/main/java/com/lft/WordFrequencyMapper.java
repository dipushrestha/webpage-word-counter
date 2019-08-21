package com.lft;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.concurrent.ConcurrentHashMap;

public class WordFrequencyMapper {
	
	private final String[] words;
	private final String text;
	private final ConcurrentHashMap<String, Integer> countOfEachWord;
	
	public WordFrequencyMapper(String words[], String text) {
		this.words = words;
		this.text = text;
		this.countOfEachWord = new ConcurrentHashMap<>();
	}
	
	private void mapWordFrequency() {
		final String regex = "\\b(" + String.join("|", words) + ")\\b";
		
		final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		final Matcher matcher = pattern.matcher(text);
			
		while(matcher.find()) {
			final String word = matcher.group().toLowerCase();
			Integer count = countOfEachWord.get(word);
			count = (count == null) ? 1 : ++count;
			countOfEachWord.put(word, count);
		}
	}
	
	public ConcurrentHashMap<String, Integer> getCountOfEachWord() {
		if (countOfEachWord.size() == 0) {
			mapWordFrequency();
		}
		
		return countOfEachWord;
	}
}
