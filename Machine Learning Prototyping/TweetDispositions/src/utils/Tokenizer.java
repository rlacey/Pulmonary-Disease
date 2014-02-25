package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Tokenizer {
	
	private String filename;
	private boolean splitPunctuation;
	private ArrayList<String> tokens;
	private int numberOfLines;
	
	public Tokenizer(String filename, boolean splitPunctuation) {
		this.filename = filename;
		this.splitPunctuation = splitPunctuation;
		tokens = new ArrayList<String>();
		tokenize();
	}
	
	private void tokenize() {
		try(BufferedReader br = new BufferedReader(new FileReader("src/text/" + filename + ".txt"))) {		
	      String line = br.readLine();
	      while (line != null) {
	    	  numberOfLines++;
	    	  parseLine(line);
	    	  line = br.readLine();
	      }
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
		
	private void parseLine(String line) {		
		if (splitPunctuation) {
			String[] splitString = line.split("[\\p{Punct}\\s]+");
			for (String s : splitString) {
				tokens.add(s);
			}
		} else {
			StringTokenizer st = new StringTokenizer(line);
			while (st.hasMoreTokens()) {
				tokens.add(st.nextToken());
			}
		}					
	}
	
	public ArrayList<String> lines() {
		ArrayList<String> lines = new ArrayList<String>();
		try(BufferedReader br = new BufferedReader(new FileReader("src/text/" + filename + ".txt"))) {		
	      String line = br.readLine();
	      while (line != null) {
	    	  lines.add(line);
	    	  line = br.readLine();
	      }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;	
	}
	
	public ArrayList<String> getTokens() {
		return tokens;
	}
	
	public int countLines() {
		return numberOfLines;
	}
}
