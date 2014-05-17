import SVMTools.Classify;
import SVMTools.Data;
import SVMTools.Parameter;
import SVMTools.Problem;
import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_parameter;
import libsvm.svm_problem;

public class Main {

	private static final String DEFAULT_PARAM = "-t 2 -c 100";	
	
	private static final int CLASSES = 2;
	
	private static double[][] train = {
		{1, 79,  435},
		{1, 81,  401},
		{1, 106, 415},
		{2, 321, 168},
		{2, 360, 431}
	};
	
	private static double[][] test = {
		{1, 81, 430},
		{2, 321, 195}
	};

	public static void main(String[] args) {
		Data data = new Data(train);
		double[][] normTrain = data.getNormalizedData();
		double[][] normTest = data.normalizeExternalDataset(test);
		
		svm_parameter parameter = Parameter.generate(DEFAULT_PARAM);		
		
		svm_problem problem = Problem.generate(parameter, normTrain);

		svm_model model = svm.svm_train(problem, parameter);

		System.out.println("\nEvaluating...");
		for (int i = 0; i < normTest.length; i++) {
			System.out.printf("Node %d", i);
			Classify.evaluate(normTest[i], CLASSES, model);
		}
	}
}