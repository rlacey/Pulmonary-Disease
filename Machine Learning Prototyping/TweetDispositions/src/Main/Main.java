package Main;

import java.util.ArrayList;

import utils.Dictionary;
import utils.FeatureMatrix;
import utils.Tokenizer;


public class Main {

	public static void main(String[] args) {
		Tokenizer tokenizer = new Tokenizer("train-tweet", true);
		ArrayList<String> tokens = tokenizer.getTokens();
//		for(String s: tokens){
//			  System.out.println(s);
//		}
		Dictionary dictionary = new  Dictionary(tokens);
		FeatureMatrix matrix = new FeatureMatrix("train-tweet", dictionary);
//		for (int i=0; i<matrix.rows(); i++) {
//			for (int j=0; j<matrix.cols(); j++) {
//				System.out.println(matrix.get(i,j));
//			}
//		}
	}

}
