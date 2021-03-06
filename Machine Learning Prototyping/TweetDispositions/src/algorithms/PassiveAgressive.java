package algorithms;

import java.util.ArrayList;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import utils.LinearSeparator;

public class PassiveAgressive {
	/**
	 * Trains linear separator given a feature matrix and manually preclasssified labels.
	 * 
	 * @param featureMatrix (m, n) Matrix of input vectors (m data points and n features)
	 * @param labels Length n vector
	 * @param T Number of iterations
	 * @return (theta, theta_0) Weight vector and offset
	 */
	public static LinearSeparator train(RealMatrix featureMatrix, ArrayList<String> labels, int T) {
		RealMatrix theta = MatrixUtils.createRealMatrix(1, featureMatrix.getColumnDimension());
		double theta_0 = 0;
		for (int t=0; t<T; t++) {
			for (int i=0; i<featureMatrix.getRowDimension(); i++) {				
				int label = Integer.parseInt(labels.get(i));
				RealMatrix featureVector = featureMatrix.getRowMatrix(i);
				double loss = 0;
				double hingeResult = label * (theta.multiply(featureVector.transpose()).getEntry(0, 0) + theta_0);
				if(hingeResult <= 1) {
					loss = 1 - hingeResult;
				}
				double alpha = loss / (featureVector.multiply(featureVector.transpose()).getEntry(0, 0) + 1);
				theta = theta.add(featureVector.scalarMultiply(alpha*label));
				theta_0 += alpha*label;
			}
		}
		LinearSeparator separator = new LinearSeparator(theta, theta_0);
		return separator;		
	}

	/**
	 * Classifies a set of data points given a weight vector and offset.
	 * 
	 * @param featureMatrix (m, n) Matrix of input vectors (m data points and n features)
	 * @param theta Length n parameter vector
	 * @param theta_0 Real number offset
	 * @return Length m label vector
	 */
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
