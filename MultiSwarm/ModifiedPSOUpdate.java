package MultiSwarm;

import MultiSwarm.Constants;
import MultiSwarm.ParticlePSO;
import net.sourceforge.jswarm_pso.Particle;
import net.sourceforge.jswarm_pso.ParticleUpdate;
import net.sourceforge.jswarm_pso.Swarm;

/*
 * Class Name: PSO Position and Velocity Update
 * Purpose: It is used to update the position and velocity of the given particle
 * 
 */

public class ModifiedPSOUpdate extends ParticleUpdate{

	/*
	 * Global Parameters: 
	 * obj : is the object used to store the particle as an object.
	 * 
	 */
	ParticlePSO obj;
	ModifiedPSOUpdate(ParticlePSO particle){
		super(particle);
		this.obj=particle;
	}
	
	/*
	 * update : provided by JSwarm and used to update the position and velocity
	 * 
	 */
	public void update(Swarm swarm,Particle particle) {
		
		double v[]=particle.getVelocity();
		double x[]=particle.getPosition();
		double pbest[]=particle.getBestPosition();
		double gbest[]=swarm.getBestPosition();
		/*
		 * count : stores the number of iterations of each particle has been updated. 
		 */
		obj.count=obj.count+1;
		int it=obj.count;
		/*
		 * w : represents inertia weight which has been calculated based on number of iterations
		 * k : represents the constriction factor which has been calculated based on number of iterations
		 * 
		 */
		double w=0.857143+(1-0.857143)*(1-it/Constants.NoOfIterations);
		double k=(Math.cos((Math.PI/Constants.NoOfIterations)*it)+2.5)/4.0;

		for(int i=0;i<Constants.NoOfTasks;i++) {
			/*
			 * There are 4 velocity update functions 
			 *  1 -> Standard Velocity Update Function
			 *  2 -> Velocity Update using Modified Inertia Weight
			 *  3 -> Velocity Update using Constriction Factor
			 *  4 -> Velocity Update using both Inertia Weight and Constriction Factor
			 */
			/*
			 * Uncomment the update function that is required to change the update function.
			 */
			// 1.
			v[i]=0.729844*v[i]+2*Math.random()*(pbest[i]-x[i])+2*Math.random()*(gbest[i]-x[i]);
			// 2.
//			v[i]=w*v[i]+2*Math.random()*(pbest[i]-x[i])+2*Math.random()*(gbest[i]-x[i]);
			// 3.
//			v[i]=k*(v[i]+2*Math.random()*(pbest[i]-x[i])+2*Math.random()*(gbest[i]-x[i]));
			// 4.
//			v[i]=k*(w*v[i]+2*Math.random()*(pbest[i]-x[i])+2*Math.random()*(gbest[i]-x[i]));

			particle.setVelocity(v);
			x[i]=(x[i]+v[i]);
			particle.setPosition(x);
		}
		
	}
}

