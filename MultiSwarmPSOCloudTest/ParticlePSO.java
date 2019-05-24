package MultiSwarmPSOCloudTest;

import java.util.Random;

import MultiSwarmPSOCloudTest.Constants;
import net.sourceforge.jswarm_pso.*;
public class ParticlePSO extends Particle{
	/*
	 * Global Parameters:
	 * count : refers to the number of iterations that has been completed by a particle
	 * position[] : represents the position of the particle in n-dimension.
	 * velocity[] : represents the velocity of the particle in n-dimension.
	 */	
	int count=0;
	public ParticlePSO(){
	super(Constants.NoOfTasks);
	double position[] = new double[Constants.NoOfTasks];
	double velocity[] = new double[Constants.NoOfTasks];
	
	for(int i=0;i<Constants.NoOfTasks;i++) {
		Random random = new Random();
		position[i] = random.nextInt(Constants.NoOfVMs);
		velocity[i] = Math.random()*Constants.NoOfVMs;
//		position[i] = (Math.random())*Constants.NoOfVMs;
//		velocity[i] = (Math.random()-0.5)>0?1.0:-1.0;
	}
	setPosition(position);
	setVelocity(velocity);
}
public String toString() {
	String output = "";
	for(int i=0;i<Constants.NoOfVMs;i++) {
		String tasks = "";
		int number_of_tasks = 0;
		for(int j=0;j<Constants.NoOfTasks;j++) {
			if( i== (int)getPosition()[j]) {
				tasks +=(tasks.isEmpty() ? " " : " " ) + j;
				++number_of_tasks;
			}
		}
		if(tasks.isEmpty())
			output += "NO Tasks is in VM "+ i+"\n";
		else
			output += number_of_tasks +" Tasks is in VM "+i +" Tasks id = " +tasks +"\n";
	}
	return output;
}
}
