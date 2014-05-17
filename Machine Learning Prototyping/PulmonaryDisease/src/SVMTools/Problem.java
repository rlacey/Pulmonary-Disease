package SVMTools;

import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;

public class Problem {

	public static svm_problem generate(svm_parameter param, double[][] train) {
		// build problem
		svm_problem prob = new svm_problem();
		prob.l = train.length;
		prob.y = new double[prob.l];

		if (param.gamma == 0) {
			param.gamma = 0.5;
		}
		
		prob.x = new svm_node[prob.l][train[0].length-1];
		for (int i = 0; i < prob.l; i++) {
			for(int j=0; j<train[0].length-1; j++) {
				prob.x[i][j] = new svm_node();
				prob.x[i][j].index = j+1;				
				prob.x[i][j].value = train[i][j+1];							
			}	
			prob.y[i] = train[i][0];
		}
		return prob;
	}
}
