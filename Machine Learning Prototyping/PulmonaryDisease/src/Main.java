import java.awt.FileDialog;
import java.awt.Frame;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import SVMTools.Classify;
import SVMTools.Parameter;
import SVMTools.Problem;
import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_parameter;
import libsvm.svm_problem;

public class Main {

	private static final String DEFAULT_PARAM = "-t 2 -c 100";	
	
	private final static int CLASSES = 2;
	
	private static double[][] train = {
//		NORMALIZED
//		 {1, -1, 1},
//		 {1, -0.985765124555, 0.74531835206},
//		 {1, -0.807829181495, 0.850187265918},
//		 {2, 0.722419928826, -1},
//		 {2, 1, 0.970037453184}
//		UNSCALED
		{1, 79,  435},
		{1, 81,  401},
		{1, 106, 415},
		{2, 321, 168},
		{2, 360, 431}
	};
	
	private static double[][] test = {
//		NORMALIZED
//		{1, -0.985765124555, 0.962546816479},
//		{2, 0.722419928826, -0.797752808989}
//		UNSCALED
		{1, 81, 430},
		{2, 321, 195}
	};
	public static int l1 = train.length;
	public static int l2 = train[0].length;
	
	private static double norm(double[] weights, double value) {	
		if(value == weights[2])
			value = weights[0];
		else if(value == weights[3])
			value = weights[1];
		else
			value = weights[0] + (weights[1]-weights[0]) * 
				(value-weights[2])/
				(weights[3]-weights[2]);
		return value;
	}

	public static void main(String[] args) {
		FileDialog dialog = new FileDialog(new Frame(), "Save", FileDialog.SAVE);
		dialog.setVisible(true);
		String filename = dialog.getDirectory() + dialog.getFile();
		try {
			DataOutputStream fp = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filename)));
			int n = train.length;
			for (int i = 0; i < n; i++) {
				fp.writeBytes(""+train[i][0]);
				for (int j = 1; j < train[0].length; j++) {
					fp.writeBytes(" " + j + ":" + train[i][j]);
				}
				fp.writeBytes("\n");
			}
			fp.close();
		} catch (IOException e) {
			System.err.print(e);
		}
		
		SVMTools.svm_scale s = new SVMTools.svm_scale();
		double[][][] weights = null;
		try {
			weights = s.run(new String[]{filename}, l1, l2, test);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		train = weights[0];
		test = weights[1];
		
		svm_parameter parameter = Parameter.generate(DEFAULT_PARAM);		
		
		svm_problem problem = Problem.generate(parameter, train);

		svm_model model = svm.svm_train(problem, parameter);

		System.out.println("Evaluating...\n");
		for (int i = 0; i < test.length; i++) {
			System.out.printf("Node %d", i);
			Classify.evaluate(test[i], CLASSES, model);
		}
	}
}
