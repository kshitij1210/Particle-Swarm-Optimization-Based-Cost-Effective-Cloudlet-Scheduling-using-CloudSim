package MultiSwarm;

import java.util.Random;

/*
 * Class Name: Particle of PSO 
 * Purpose: This is the particle whose position and velocity is updated and fitness is calculated.
 * 
 */

import net.sourceforge.jswarm_pso.*;
public class ParticlePSO extends Particle{

/*
 * Global Parameters:
 * count : refers to the number of iterations that has been completed by a particle
 * position[] : represents the position of the particle in n-dimension.
 * velocity[] : represents the velocity of the particle in n-dimension.
 */	
public int count=0;
double position[] = new double[Constants.NoOfTasks];
double velocity[] = new double[Constants.NoOfTasks];

public ParticlePSO(){
	super(Constants.NoOfTasks);	
	initialize();
}
void initialize() {
	for(int i=0;i<Constants.NoOfTasks;i++) {
		Random random = new Random();
		/*
		 * Search space of CEC-2013 function is bounded from -100.0 to 100.0 along any direction
		 */
		position[i] = (Math.random()-0.5)*200;
		velocity[i] = (Math.random()-0.5)*(0.5);
	}
	setPosition(position);
	setVelocity(velocity);
}

}


