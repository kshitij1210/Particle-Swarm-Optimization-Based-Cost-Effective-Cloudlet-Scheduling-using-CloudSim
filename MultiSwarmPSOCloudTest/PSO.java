package MultiSwarmPSOCloudTest;

import MultiSwarmPSOCloudTest.*;
import net.sourceforge.jswarm_pso.*;
	
public class PSO {
	/*
	 * Global Parameters:
	 * ff : represents the fitness function used by all particles in the swarm
	 * swarm : represents the different swarms that are used in multiswarm PSO
	 * particles[i][j] : represents the particles where i is the swarm id and j is particle id
	 * other parameters are defined earlier
	 */
ParticlePSO particles[][];
static int minimum=0;
FitnessFunctionPSO ff;
Swarm swarm[] = new Swarm[Constants.NoOfSwarms];
int[] tasklength;int[] outputfilesize;int[] mips;double[] execcost,waitcost;
int[][] graph;
public PSO(int[] tasklength,int[] outputfilesize,int[] mips,double[] execcost,double[] waitcost,int[][] graph)
{
	this.tasklength=tasklength;
	this.outputfilesize=outputfilesize;
	this.mips=mips;
	this.execcost=execcost;
	this.graph=graph;
	this.waitcost=waitcost;
	ff = new FitnessFunctionPSO(execcost,waitcost,mips,outputfilesize,tasklength,graph);
	for(int q=0;q<Constants.NoOfSwarms;q++)
		swarm[q] = new Swarm(Constants.NoOfParticles/Constants.NoOfSwarms, new ParticlePSO(), ff);
	initializeParticles();
}
public void initializeParticles() {
	particles = new ParticlePSO[Constants.NoOfSwarms][Constants.NoOfParticles/Constants.NoOfSwarms];
	for(int q=0;q<Constants.NoOfSwarms;q++) {
		for(int i=0;i<Constants.NoOfParticles/Constants.NoOfSwarms;i++) {
			particles[q][i]= new ParticlePSO();
		}
	}
}
public double[] run() {
	for(int q=0;q<Constants.NoOfSwarms;q++) {
	swarm[q].setMinPosition(0);//minimum value is the minimum value of vm id
	swarm[q].setMaxPosition(Constants.NoOfVMs-1);//maximum value of vm id
	swarm[q].setMaxMinVelocity(1.1);
	swarm[q].setParticles(particles[q]);
	swarm[q].setParticleUpdate(new ModifiedPSOUpdate(new ParticlePSO()));
	}
	for(int i=0;i<=Constants.NoOfIterations;i++) {
		for(int q=0;q<Constants.NoOfSwarms;q++) {
			swarm[q].evolve();
			if(i%10 == 0) {
				System.out.println("Global best at iteration "+i+" : for swarm :"+q+" "+swarm[q].getBestFitness());
			}
		}
	
		if(i==Constants.NoOfIterations){
		int min=0;
			for(int w=1;w<Constants.NoOfSwarms;w++)
			{
				if(swarm[w].getBestFitness()<swarm[min].getBestFitness())
					min=w;
			}
			minimum=min;
		}
	}
	System.out.println("Minimum"+minimum);
	ParticlePSO bestparticle = (ParticlePSO)swarm[minimum].getBestParticle();
	return swarm[minimum].getBestPosition();	
}
public double printBestFitness() {
	System.out.println("The best fitness value is "+swarm[minimum].getBestFitness());
	return swarm[minimum].getBestFitness();
}


public double[][] getexecutiontimematrix(){
	return ff.getexecutiontimematrix();
}
public double[][] getcommunicationtimematrix(){
	return ff.getcommunicationtimematrix();
}
public double[][] getdatatransfermatrix(){
	return ff.getdatatransfermatrix();
}
public double[][] getcommcost(){
	return ff.getcommcost();
}
public double[][] gettaskoutputfilematrix(){
	return ff.gettaskoutputfilematrix();
}

}
