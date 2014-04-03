package tweets;

import java.util.ArrayList;

import org.apache.commons.math3.linear.RealMatrix;

import utils.FeatureMatrix;
import utils.Parser;
import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;

/**
 * Design structure from http://stackoverflow.com/questions/10792576/libsvm-java-implementation
 * 
 * @author Ryan Lacey
 *
 */
public class Main {

	static double[][] train;
	static double[][] test;
	static double[] results = new double[70];
	static int total = 0;
	static int correct = 0;
	
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

// ORIGINAL DATA GENERATION		
//		train = new double[1000][]; 
//		test = new double[10][];		
//		for (int i = 0; i < train.length; i++){
//		    if (i+1 > (train.length/2)){        // 50% positive
//		        double[] vals = {1,0,i+i};
//		        train[i] = vals;
//		    } else {
//		        double[] vals = {0,0,i-i-i-2}; // 50% negative
//		        train[i] = vals;
//		    }           
//		}
				
		// Prepend label to be first element of feature matrix of training data
		double[][] trainData = featureMatrix.getData();
		train = new double[trainLabels.size()][];
		for (int i=0; i<trainLabels.size(); i++) {
			train[i] = new double[trainData[i].length+1];
			// Set label as first element
			train[i][0] = Double.parseDouble(trainLabels.get(i));
			// Map data[i] to [i+1] element
			for (int j=1; j<trainData.length+1; j++) {
				train[i][j] = trainData[i][j];
			}
		}
		
		test = testMatrix.getData();
		
		// Train classifier
		System.out.println("Training...");
		svm_model model = svmTrain();
		
		// Generate predictions
		System.out.println("Evaluating...");
		for (int i=0; i < test.length; i++){
			System.out.print("Node " + i + " ");
			evaluate(test[i], model);
		}
		
		// Analyze results against known test labels
		for (int i=0; i < results.length; i++){
			if (results[i] - Double.parseDouble(testLabels.get(i)) < 0.1) {
				correct++;
			}
		}
		System.out.println("\n" + correct + "/"+total + " correct");		
	}
	
	private static svm_model svmTrain() {
	    svm_problem prob = new svm_problem();
	    int dataCount = train.length;
	    prob.y = new double[dataCount];
	    prob.l = dataCount;
	    prob.x = new svm_node[dataCount][];     

	    for (int i = 0; i < dataCount; i++){            
	        double[] features = train[i];
	        prob.x[i] = new svm_node[features.length-1];
	        for (int j = 1; j < features.length; j++){
	            svm_node node = new svm_node();
	            node.index = j;
	            node.value = features[j];
	            prob.x[i][j-1] = node;
	        }           
	        prob.y[i] = features[0];
	    }               

	    svm_parameter param = new svm_parameter();
	    param.probability = 1;
	    param.gamma = 0.5;
	    param.nu = 0.5;
	    param.C = 1;
	    param.svm_type = svm_parameter.C_SVC;
	    param.kernel_type = svm_parameter.LINEAR;       
	    param.cache_size = 20000;
	    param.eps = 0.001;      

	    svm_model model = svm.svm_train(prob, param);

	    return model;
	}
	
	public static double evaluate(double[] features, svm_model model) 
	{
	    svm_node[] nodes = new svm_node[features.length-1];
	    for (int i = 1; i < features.length; i++)
	    {
	        svm_node node = new svm_node();
	        node.index = i;
	        node.value = features[i];

	        nodes[i-1] = node;
	    }

	    int totalClasses = 2;       
	    int[] labels = new int[totalClasses];
	    svm.svm_get_labels(model,labels);

	    double[] prob_estimates = new double[totalClasses];
	    double v = svm.svm_predict_probability(model, nodes, prob_estimates);

	    for (int i = 0; i < totalClasses; i++){
	        System.out.print("(" + labels[i] + ":" + prob_estimates[i] + ")");
	    }
	    System.out.println("  (Actual:" + features[0] + " Prediction:" + v + ")");   
	    
	    results[total] = v;
	    total++;

	    return v;
	}
}
