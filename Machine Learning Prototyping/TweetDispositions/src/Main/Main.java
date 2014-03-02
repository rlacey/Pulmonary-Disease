package main;

import java.util.ArrayList;
import org.apache.commons.math3.linear.RealMatrix;

import algorithms.Perceptron;


import utils.FeatureMatrix;
import utils.LinearSeparator;
import utils.Parser;

public class Main {

	public static void main(String[] args) {
		ArrayList<String> dictionary = Parser.tokenize("train-tweet");
		ArrayList<String> labels = Parser.vectorizeLines("train-answer");
		RealMatrix featureMatrix = FeatureMatrix.construct("train-tweet", dictionary);	
		
		System.out.println("Training perceptron...");
		LinearSeparator separator = Perceptron.train(featureMatrix, labels);			
		RealMatrix theta = separator.theta();
		double theta_0 = separator.theta_0();
		System.out.println("Training complete.");
		
		System.out.println("Classifying training data..");
		RealMatrix predictions = Perceptron.classify(featureMatrix, theta, theta_0);		
		System.out.println("Classification complete");
		
		System.out.println("Analyzing...");
		double[] pC= predictions.getRow(0);
		double[] lC = new double[labels.size()];
		for (int i=0; i<labels.size(); i++) {
			lC[i] = Double.parseDouble(labels.get(i));
		}
		
		double correct = 0;
		for (int i=0; i<labels.size(); i++) {
			if (pC[i] - lC[i] <= 0.00000001) {
				correct++;
			}
		}
		double percent = correct / labels.size();
		System.out.println("Algoritm got " + correct + " of " + labels.size() + " correct (" + percent*100 + "%)");
	}

}