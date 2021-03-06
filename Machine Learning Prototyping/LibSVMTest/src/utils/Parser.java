package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {
	
	/**
	 * Read input file and split text into words.
	 * 
	 * @param filename Name of file in TEXT package
	 * @return List of word tokens
	 */
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
	
	/**
	 * Remove punctuation from input string and split upon whitespace.
	 * 
	 * @param line Line of text from file
	 * @return Array of words
	 */
	public static String[] parseLine(String line) {
		return line.replaceAll("\\p{Punct}", "").split("\\s+");
	}
	
	/**
	 * Count the number of non-empty lines of file.
	 * 
	 * @param filename Name of file in TEXT package
	 * @return Total line count
	 */
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
	
	/**
	 * Read input file and split text into lines.
	 * 
	 * @param filename Name of file in TEXT package
	 * @return List of lines
	 */
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
