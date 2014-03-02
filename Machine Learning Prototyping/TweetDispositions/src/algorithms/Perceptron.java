package algorithms;

import java.util.ArrayList;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import utils.LinearSeparator;

public class Perceptron {
	
	public static LinearSeparator train(RealMatrix featureMatrix, ArrayList<String> labels) {
		RealMatrix theta = MatrixUtils.createRealMatrix(1, featureMatrix.getColumnDimension());
		double theta_0 = 0;
		int counter = 0;
		boolean misclassification = true;
		
		while (misclassification && counter < 5000) {
			misclassification = false;
			counter ++;
			for (int i=0; i<featureMatrix.getRowDimension(); i++) {				
				int label = Integer.parseInt(labels.get(i));
				RealMatrix featureVector = featureMatrix.getRowMatrix(i);
				double loss = featureVector.multiply(featureVector.transpose()).getEntry(0, 0) + theta_0;
				if (label * loss <= 0) {
					RealMatrix offset = featureVector.scalarMultiply((double) label);
					theta = theta.add(offset);
					theta_0 += label;
					misclassification = true;
				}
			}
		}
		LinearSeparator separator = new LinearSeparator(theta, theta_0);
		return separator;		
	}

	public static RealMatrix classify(RealMatrix featureMatrix, RealMatrix theta, double theta_0) {
		RealMatrix labels = MatrixUtils.createRealMatrix(1, featureMatrix.getRowDimension());		
		for (int i=0; i<featureMatrix.getRowDimension(); i++) {
			RealMatrix featureVector = featureMatrix.getRowMatrix(i);
			double prediction = theta.multiply(featureVector.transpose()).getEntry(0, 0) + theta_0;
			if (prediction > 0) {
				labels.setEntry(0, i, 1);
			} else {
				labels.setEntry(0, i, -1);
			}
		}
		return labels;
	}
}