package tweets;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

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
public class TesterMain {
	
	static final String DEFAULT_PARAM="-t 2 -c 100";
	
	static Vector<point> point_list = new Vector<point>();
	static class point {
		point(double x, double y, byte value)
		{
			this.x = x;
			this.y = y;
			this.value = value;
		}
		double x, y;
		byte value;
	}
	
	private static double atof(String s)
	{
		return Double.valueOf(s).doubleValue();
	}
	
	private static int atoi(String s)
	{
		return Integer.parseInt(s);
	}

	static void run(String args) {
		svm_parameter param = new svm_parameter();
		
		// default values
		param.svm_type = svm_parameter.C_SVC;
		param.kernel_type = svm_parameter.RBF;
		param.degree = 3;
		param.gamma = 0;
		param.coef0 = 0;
		param.nu = 0.5;
		param.cache_size = 40;
		param.C = 1;
		param.eps = 1e-3;
		param.p = 0.1;
		param.shrinking = 1;
		param.probability = 1;
		param.nr_weight = 0;
		param.weight_label = new int[0];
		param.weight = new double[0];
		
		// parse options
		StringTokenizer st = new StringTokenizer(args);
		String[] argv = new String[st.countTokens()];
		for(int i=0;i<argv.length;i++)
			argv[i] = st.nextToken();

		for(int i=0;i<argv.length;i++)
		{
			if(argv[i].charAt(0) != '-') break;
			if(++i>=argv.length)
			{
				System.err.print("unknown option\n");
				break;
			}
			switch(argv[i-1].charAt(1))
			{
				case 's':
					param.svm_type = atoi(argv[i]);
					break;
				case 't':
					param.kernel_type = atoi(argv[i]);
					break;
				case 'd':
					param.degree = atoi(argv[i]);
					break;
				case 'g':
					param.gamma = atof(argv[i]);
					break;
				case 'r':
					param.coef0 = atof(argv[i]);
					break;
				case 'n':
					param.nu = atof(argv[i]);
					break;
				case 'm':
					param.cache_size = atof(argv[i]);
					break;
				case 'c':
					param.C = atof(argv[i]);
					break;
				case 'e':
					param.eps = atof(argv[i]);
					break;
				case 'p':
					param.p = atof(argv[i]);
					break;
				case 'h':
					param.shrinking = atoi(argv[i]);
					break;
				case 'b':
					param.probability = atoi(argv[i]);
					break;
				case 'w':
					++param.nr_weight;
					{
						int[] old = param.weight_label;
						param.weight_label = new int[param.nr_weight];
						System.arraycopy(old,0,param.weight_label,0,param.nr_weight-1);
					}

					{
						double[] old = param.weight;
						param.weight = new double[param.nr_weight];
						System.arraycopy(old,0,param.weight,0,param.nr_weight-1);
					}

					param.weight_label[param.nr_weight-1] = atoi(argv[i-1].substring(2));
					param.weight[param.nr_weight-1] = atof(argv[i]);
					break;
				default:
					System.err.print("unknown option\n");
			}
		}
		
//		point_list.add(new point(0.158, 0.87,  (byte) 1));
//		point_list.add(new point(0.162, 0.802, (byte) 1));
//		point_list.add(new point(0.212, 0.83,  (byte) 1));
//		point_list.add(new point(0.642, 0.336, (byte) 2));
//		point_list.add(new point(0.72,  0.862, (byte) 2));
		
		point_list.add(new point(-1, 1,  (byte) 1));
		point_list.add(new point(-0.985765124555, 0.74531835206, (byte) 1));
		point_list.add(new point(-0.807829181495, 0.850187265918,  (byte) 1));
		point_list.add(new point(0.722419928826, -1, (byte) 2));
		point_list.add(new point(1,  0.970037453184, (byte) 2));		
		
		
		
		// build problem
		svm_problem prob = new svm_problem();
		prob.l = point_list.size();
		prob.y = new double[prob.l];

		if(param.gamma == 0) param.gamma = 0.5;
		prob.x = new svm_node [prob.l][2];
		for(int i=0;i<prob.l;i++)
		{
			point p = point_list.elementAt(i);
			prob.x[i][0] = new svm_node();
			prob.x[i][0].index = 1;
			prob.x[i][0].value = p.x;
			prob.x[i][1] = new svm_node();
			prob.x[i][1].index = 2;
			prob.x[i][1].value = p.y;
			prob.y[i] = p.value;
		}

		// build model & classify
		svm_model model = svm.svm_train(prob, param);
		svm_node[] x = new svm_node[2];
		x[0] = new svm_node();
		x[1] = new svm_node();
		x[0].index = 1;
		x[1].index = 2;

		int totalClasses = 2;       
	    int[] labels = new int[totalClasses];
	    svm.svm_get_labels(model,labels);
		
		double[][] test = {
				{1, -0.985765124555, 0.962546816479},
				{1, 0.722419928826, -0.797752808989}
		};
		
		System.out.println("Evaluating...");	
		for (int i=0; i < test.length; i++){
			System.out.print("Node " + i + " ");
			evaluate(test[i], model);
		}
		
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

	    return v;
	}
	
	public static void main(String[] argv)
	{
		
		run(DEFAULT_PARAM);
	}
}
