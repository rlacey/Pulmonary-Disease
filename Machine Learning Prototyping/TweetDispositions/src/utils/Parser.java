package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {
	
	public static ArrayList<String> tokenize(String filename) {
		ArrayList<String> tokens = new ArrayList<String>();
		try(BufferedReader br = new BufferedReader(new FileReader("src/text/" + filename + ".txt"))) {		
			String line = br.readLine();
			while (line != null) {
				String[] newTokens = parseLine(line);	
				for (String s : newTokens) { 
					if (!tokens.contains(s.toLowerCase())) { tokens.add(s.toLowerCase()); }
				}
				line = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tokens;			
	}
	
	public static String[] parseLine(String line) {
		return line.split("[\\p{Punct}\\s]+");
	}
	
	public static int countLines(String filename) {
		int numberOfLines = 0;
		try (BufferedReader br = new BufferedReader(new FileReader("src/text/" + filename + ".txt"))) {
			String line = br.readLine();
			while (line != null) {
				if (!line.trim().isEmpty()) { numberOfLines++; }
				line = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return numberOfLines;
	}	
	
	public static ArrayList<String> vectorizeLines(String filename) {
		ArrayList<String> vector = new ArrayList<String>();
		try(BufferedReader br = new BufferedReader(new FileReader("src/text/" + filename + ".txt"))) {		
			String line = br.readLine();
			while (line != null) {
				if (!line.trim().isEmpty()) { vector.add(line); }
				line = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return vector;		
	}

}
