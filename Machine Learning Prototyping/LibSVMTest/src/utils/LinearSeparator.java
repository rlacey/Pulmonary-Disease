package utils;

import org.apache.commons.math3.linear.RealMatrix;

public class LinearSeparator {
	
	RealMatrix theta;
	double theta_0;
	
	/**
	 * Constructs linear separator which consists of weight vector and an offset.
	 * 
	 * @param theta Weight vector
	 * @param theta_0 Offset
	 */
	public LinearSeparator (RealMatrix theta, double theta_0) {
		this.theta = theta;
		this.theta_0 = theta_0;
	}

	/**
	 * Gets theta vector
	 * 
	 * @return Weight vector of linear separator
	 */
	public RealMatrix theta() {
		return theta.copy();
	}
	
	/**
	 * Gets theta_0
	 * 
	 * @return Real number offset of linear separator
	 */
	public double theta_0() {
		return theta_0;
	}
}
