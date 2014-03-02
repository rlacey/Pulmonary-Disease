package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class FeatureMatrix {
	
	public static RealMatrix construct(String filename, ArrayList<String> tokens) {
		RealMatrix matrix = MatrixUtils.createRealMatrix(Parser.countLines(filename), tokens.size());
		int position = 0;
		try (BufferedReader br = new BufferedReader(new FileReader("src/text/" + filename + ".txt"))) {
			String line = br.readLine();
			while (line != null) {				
				if (!line.trim().isEmpty()) {
					String[] splitString = Parser.parseLine(line);
					for (String s : splitString) {
						if (tokens.contains(s)) { matrix.setEntry(position, tokens.indexOf(s), 1); }
					}
					position++;
				}
				line = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return matrix;		
	}

}