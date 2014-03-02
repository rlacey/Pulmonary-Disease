package utils;

import java.util.ArrayList;
import java.util.Arrays;

public class FeatureMatrix {
	
	private Tokenizer tokenizer;
	private Dictionary dictionary;
	ArrayList<String> keys;
	int[][] matrix;
	int rows;
	int cols;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public FeatureMatrix(String filename, Dictionary dictionary) {
		this.tokenizer = new Tokenizer(filename, true);
		this.dictionary = dictionary;
		keys = new ArrayList(Arrays.asList(dictionary.keys()));
		create();
	}
		
	private void create() {
		rows = tokenizer.countLines();
		cols = dictionary.size();
		matrix = new int[rows][cols];
		int index = 0;
		for(String line: tokenizer.lines()) {
			String[] splitString = line.split("[\\p{Punct}\\s]+");
			for (String s : splitString) {
				if (keys.contains(s)) {
					matrix[index][keys.indexOf(s)] = 1;					
				}				
			}
			index++;
		}
	}
	
	public int[][] getMatrix() {
		return matrix;
	}
	
	public int rows() {
		return rows;
	}
	
	public int cols() {
		return cols;
	}
	
	public int get(int x, int y) {
		return matrix[x][y];
	}
	
}
