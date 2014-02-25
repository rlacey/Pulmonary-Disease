package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Dictionary {
	
	private ArrayList<String> tokens;
	private Map<String, Integer> dictionary;
	
	public Dictionary(ArrayList<String> tokens) {
		this.tokens = tokens;
		dictionary = new HashMap<String, Integer>();		
		create();
	}
	
	private void create() {		
		for(String s: tokens){
			if (!dictionary.containsKey(s)) {
				dictionary.put(s, 0);
			}			
		} 
	}
	
	public int size() {
		return dictionary.size();
	}
	
	public boolean contains(String key) {
		return dictionary.containsKey(key);
	}
	
	public Set<String> keys() {
		return dictionary.keySet();
	}
}
