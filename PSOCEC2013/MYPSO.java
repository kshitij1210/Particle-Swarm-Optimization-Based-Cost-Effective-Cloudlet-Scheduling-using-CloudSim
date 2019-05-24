package PSOCEC2013;

import java.util.Arrays;

import net.sourceforge.jswarm_pso.*;
public class MYPSO {
FitnessFunctionPSO ff ;
Swarm swarm;
private static ParticlePSO particles[];
static double error[][][] = new double[28][51][11];
static double multiply[]={0.01,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0};
double ansfitness[]={-1400,-1300,-1200,-1100,-1000,-900,-800,-700,-600,-500,-400,-300,-200,-100,100,200,300,400,500,600,700,800,900,1000,1100,1200,1300,1400};

int funcno;
int counter;
public MYPSO(int funcno,int run)
{
	this.funcno=funcno;
	this.counter=counter;
	ff = new FitnessFunctionPSO(funcno);
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
	swarm.setMinPosition(-100);//minimum value is the minimum value of vm id
	swarm.setMaxPosition(100);//maximum value of vm id
	swarm.setMaxMinVelocity(1.0);
	swarm.setParticles(particles);
//	swarm.setParticleUpdate(new UpdationPSO(new ParticlePSO()));
	swarm.setParticleUpdate(new ModifiedPSOUpdate(new ParticlePSO()));
	double ansprev=0.0;
	double anscurr=10000.0;
	for(int i=0;i<Constants.NoOfIterations;i++) {
		swarm.evolve();
		anscurr=swarm.getBestFitness();
		if(i%10 == 0) {
			System.out.println("Global best at iteration "+i+" :"+swarm.getBestFitness());
		}
		for(int j=0;j<11;j++){
		if(i==(multiply[j]*Constants.NoOfTasks)){
			error[funcno-1][counter][j]=Math.abs(swarm.getBestFitness()-ansfitness[funcno]);
		}
		}
			
	}
	//System.out.println("The best fitness value is "+swarm.getBestFitness()+"\n The Best position value is "+ swarm.getBestParticle().getBestPosition());
	ParticlePSO bestparticle = (ParticlePSO)swarm.getBestParticle();
	//System.out.println(bestparticle.toString());
	printBestFitness();
	return swarm.getBestPosition();	
	
}

public double printBestFitness() {
	System.out.println("The best fitness value is "+swarm.getBestFitness());
	//double[] best = swarm.getBestPosition();
	//for(int i=0;i<best.length;i++) {
	//	System.out.print(best[i]+"\t");
	//}
		System.out.println();
		return swarm.getBestFitness();
}

public static void main(String args[]) {
	double result[][] = new double[28][51];
	for(int f=0;f<28;f++) {
	for(int count=0;count<51;count++) {
	System.out.println("for function fitness :"+ (f+1)+"\t run:"+ (count+1));
	MYPSO obj = new MYPSO(f+1,count);
	obj.run();
	result[f][count]=obj.printBestFitness();
	}
	}
	double maxf[]= new double[28];
	double minf[] = new double[28];
	double mean[] = new double[28];
	double median[] = new double[28];


	for(int i=0;i<28;i++)
	{
		double maxi=-100000;
		double mini=100000;
		double sum=0;
		double temp[]= new double[51];
	for(int j=0;j<51;j++)
	{
		sum+=result[i][j];
		maxi=Math.max(maxi, result[i][j]);
		mini=Math.min(mini, result[i][j]);
		temp[j]=result[i][j];
	}
	maxf[i]=maxi;
	minf[i]=mini;
	mean[i]=(double)sum/51.0;
	Arrays.sort(temp);
	median[i]=temp[25];
	}
	System.out.println("max\t\t min\t\t mean\t\t median");
	for(int i=0;i<28;i++)
	{
		System.out.println(maxf[i]+"\t"+minf[i]+"\t"+mean[i]+"\t"+median[i]);
	}
	for(int i=0;i<28;i++){
		for(int j=0;j<51;j++){
			for(int k=0;k<11;k++){
				System.out.println("For function :"+(i+1)+ "run :"+(j+1)+"error at:"+(multiply[k]*Constants.NoOfIterations)+" is :"+error[i][j][k]);
			}
		}
	}
}

}
