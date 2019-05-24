package PSOCloud;

import net.sourceforge.jswarm_pso.*;
public class PSO {
	/*
	 * Global Parameters:
	 * ff : represents the fitness function used by all particles in the swarm
	 * swarm : represents the different swarms that are used in multiswarm PSO
	 * particles[i][j] : represents the particles where i is the swarm id and j is particle id
	 * other parameters are defined earlier
	 */
ParticlePSO particles[];
FitnessFunctionPSO ff;
Swarm swarm;
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
	swarm = new Swarm(Constants.NoOfParticles, new ParticlePSO(), ff);
	initializeParticles();
}
public void initializeParticles() {
	particles = new ParticlePSO[Constants.NoOfParticles];
	for(int i=0;i<Constants.NoOfParticles;i++) {
		particles[i]= new ParticlePSO();
	}
}
public double[] run() {
	swarm.setMinPosition(0);//minimum value is the minimum value of vm id
	swarm.setMaxPosition(Constants.NoOfVMs-1);//maximum value of vm id
	swarm.setMaxMinVelocity(1.1);
	swarm.setParticles(particles);
//	swarm.setParticleUpdate(new UpdationPSO(new ParticlePSO()));
	swarm.setParticleUpdate(new ModifiedPSOUpdate(new ParticlePSO()));
	for(int i=0;i<Constants.NoOfIterations;i++) {
		swarm.evolve();
		if(i%10 == 0) {
			System.out.println("Global best at iteration "+i+" :"+swarm.getBestFitness());
		}
	}
	System.out.println("The best fitness value is "+swarm.getBestFitness());
	ParticlePSO bestparticle = (ParticlePSO)swarm.getBestParticle();
	System.out.println(bestparticle.toString());
	return swarm.getBestPosition();	
}
public double printBestFitness() {
	System.out.println("The best fitness value is "+swarm.getBestFitness());
	return swarm.getBestFitness();
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
