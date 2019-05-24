package MultiSwarm;

import MultiSwarm.*;
import net.sourceforge.jswarm_pso.*;


/*
 * Class Name: Fitness Function
 * Purpose: It provides the function used to test the convergence of our PSO Algorithm
 * 
 */
public class FitnessFunctionPSO extends FitnessFunction{

	/*
	 * Global Parameters:
	 * funcno : refers to the function number corresponding to different functions in CEC-2013
	 * 
	 */
	int funcno;
	FitnessFunctionPSO(int funcno)
	{
		/*
		 * Here false in super denotes that our objective is to minimize the function.
		 */
		super(false);
		this.funcno=funcno;
	}

	/*
	 * evaluate is the function which returns the fitness value of the position
	 * the fitness value of the position is returned in f[0]
	 */
	public double evaluate(double[] position) {
		testfunc tf = new testfunc();
		double f[]= {0.0,0.0};

		try {
			tf.test_func(position,f, Constants.NoOfTasks, 1, funcno);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f[0];
		
	}
}
