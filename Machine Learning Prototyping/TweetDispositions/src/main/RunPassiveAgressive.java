package main;

import java.util.ArrayList;

import org.apache.commons.math3.linear.RealMatrix;

import utils.FeatureMatrix;
import utils.LinearSeparator;
import utils.Parser;
import algorithms.PassiveAgressive;

public class RunPassiveAgressive {

	public static void main(String[] args) {
		/**
		 * INITIALIZE
		 */
		// Bag of words
		ArrayList<String> dictionary = Parser.tokenize("train-tweet");
		// Training data labels
		ArrayList<String> trainLabels = Parser.vectorizeLines("train-answer");
		// Test data labels
		ArrayList<String> testLabels = Parser.vectorizeLines("test-answer");
		// Training feature matrix
		RealMatrix featureMatrix = FeatureMatrix.construct("train-tweet", dictionary);
		// Training feature matrix
		RealMatrix testMatrix = FeatureMatrix.construct("test-tweet", dictionary);
		
		System.out.println("FEATURE MATRIX: " + featureMatrix.getRow(0)[0]);
		for (double d : featureMatrix.getRow(40)) {
			System.out.print(d+ " ");
		}
		
		/**
		 * TRAIN PASSIVE AGGRESSIVE
		 */
		System.out.println("Training passive aggressive...");
		// Calculate linear separator
		LinearSeparator separator = PassiveAgressive.train(featureMatrix, trainLabels, 25);	
		// Weights
		RealMatrix theta = separator.theta();
		// Offset parameter
		double theta_0 = separator.theta_0();
		System.out.println("Training complete.");
		
		/**
		 * CLASSIFY TRAINING DATA
		 */
		System.out.println("Classifying training data..");
		// Labels assigned to training data by separator
		RealMatrix trainingPredictionsMatrix = PassiveAgressive.classify(featureMatrix, theta, theta_0);		
		System.out.println("Classification complete");
		
		/**
		 * ANALYZE TRAINING DATA
		 */
		System.out.println("Analyzing...");	
		// Convert prediction labels to integer array
		double[] trainingPredictions = trainingPredictionsMatrix.getRow(0);
		// Convert actual labels to integer array
		double[] trainingActuals = new double[trainLabels.size()];
		for (int i=0; i<trainLabels.size(); i++) {
			trainingActuals[i] = Double.parseDouble(trainLabels.get(i));
		}
		// Counter of correct predictions
		double correct = 0;
		// Find which prediction labels matched actual labels
		for (int i=0; i<trainLabels.size(); i++) {
			if (Math.abs(trainingPredictions[i] - trainingActuals[i]) <= 0.00000001) {
				correct++;
			}
		}
		double percent = correct / trainLabels.size();
		System.out.println("Algorithm got " + correct + " of " + trainLabels.size() + " correct (" + percent*100 + "%)");

		/**
		 * CLASSIFY TEST DATA
		 */
		System.out.println("Classifying test data..");
		// Labels assigned to test data by separator
		RealMatrix testPredictionsMatrix = PassiveAgressive.classify(testMatrix, theta, theta_0);		
		System.out.println("Classification complete");
		
		/**
		 * ANALYZE TEST DATA
		 */
		System.out.println("Analyzing...");	
		// Convert prediction labels to integer array
		double[] testPredictions = testPredictionsMatrix.getRow(0);
		// Convert actual labels to integer array
		double[] testActuals = new double[testLabels.size()];
		for (int i=0; i<testLabels.size(); i++) {
			testActuals[i] = Double.parseDouble(testLabels.get(i));
		}
		// Counter of correct predictions
		double correctT = 0;
		// Find which prediction labels matched actual labels
		for (int i=0; i<testLabels.size(); i++) {
			if (Math.abs(testPredictions[i] - testActuals[i]) <= 0.00001) {
				correctT++;
			}
		}
		double percentT = correctT / testLabels.size();
		System.out.println("Algorithm got " + correctT + " of " + testLabels.size() + " correct (" + percentT*100 + "%)");
	}

}
