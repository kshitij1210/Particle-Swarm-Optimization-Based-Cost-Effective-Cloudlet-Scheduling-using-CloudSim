package PSOCEC2013;

import java.io.*;
/*
 * Class Name: PSO
 * Purpose : Used for running the PSO simulation
 * 
 */
import java.util.Arrays;

import net.sourceforge.jswarm_pso.*;
public class PSO {
	/*
	 * Global Parameters:
	 * ff : represents the fitness function used by all particles in the swarm
	 * swarm : represents the swarms that are used in PSO
	 * particles[i] : represents the particles where i is particle id
	 * error[][][] : stores the error for all 28 fitness function
	 * ansplot[][][] : stores the fitness value for all 28 fitness function
	 * ansfitness[] : represents the best possible fitness value of the function (minimum)
	 * funcno : stores the function number of CEC function to be checked
	 */	
FitnessFunctionPSO ff ;
Swarm swarm;
private static ParticlePSO particles[];
static double error[][][] = new double[28][7][11];
static double ansplot[][][] = new double[28][7][11];
static double multiply[]={0.01,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0};
double ansfitness[]={-1400,-1300,-1200,-1100,-1000,-900,-800,-700,-600,-500,-400,-300,-200,-100,100,200,300,400,500,600,700,800,900,1000,1100,1200,1300,1400};

int funcno;
int counter;

public PSO(int funcno,int counter)
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
	/*
	 * Defining the parameters of swarm 
	 * 
	 */
	swarm.setMinPosition(-100);
	swarm.setMaxPosition(100);
	swarm.setMaxMinVelocity(1.0);
	swarm.setParticles(particles);
	swarm.setParticleUpdate(new ModifiedPSOUpdate(new ParticlePSO()));
	for(int i=1;i<=Constants.NoOfIterations;i++) {
		swarm.evolve();
		if(i%10 == 0) {
			
			System.out.println("Global best at iteration "+i+" :"+swarm.getBestFitness());
		}		
		for(int j=0;j<11;j++){
		if(i==(multiply[j]*Constants.NoOfIterations)){
			error[funcno-1][counter][j]=Math.abs(swarm.getBestFitness()-ansfitness[funcno-1]);
			ansplot[funcno-1][counter][j] = swarm.getBestFitness();
		}
		}
			
	}
	//System.out.println("The best fitness value is "+swarm.getBestFitness()+"\n The Best position value is "+ swarm.getBestParticle().getBestPosition());
	ParticlePSO bestparticle = (ParticlePSO)swarm.getBestParticle();
	printBestFitness();
	return swarm.getBestPosition();	
	
}

/*
 * Printing the best fitness value for all the swarm
 * 
 */
public double printBestFitness() {
	System.out.println("The best fitness value is "+swarm.getBestFitness()+"\n The Best position value is ");
	double[] best = swarm.getBestPosition();
	for(int i=0;i<best.length;i++) {
		System.out.print(best[i]+"\t");
	}
		System.out.println();
		return swarm.getBestFitness();
}

public static void main(String args[]) {
	double result[][] = new double[28][7];
	for(int f=0;f<28;f++) {
	for(int count=0;count<7;count++) {
	System.out.println("for function fitness :"+ (f+1)+"\t run:"+ (count+1));
	PSO obj = new PSO(f+1,count);
	
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
		double temp[]= new double[7];
	for(int j=0;j<7;j++)
	{
		sum+=result[i][j];
		maxi=Math.max(maxi, result[i][j]);
		mini=Math.min(mini, result[i][j]);
		temp[j]=result[i][j];
	}
	maxf[i]=maxi;
	minf[i]=mini;
	mean[i]=(double)sum/7.0;
	Arrays.sort(temp);
	median[i]=temp[3];
	}
	try {
		/*
		 * Change the path of output files
		 */
		FileWriter fw = new FileWriter("C:\\Users\\user\\Desktop\\OutputFiles       \\5-D_Mean_Median.txt", true);
	    BufferedWriter bw = new BufferedWriter(fw);
	    PrintWriter out = new PrintWriter(bw);
	
	out.println("max\t\t min\t\t mean\t\t median");
	for(int i=0;i<28;i++)
	{
		
	String a = (new Double(maxf[i]).toString())+"\t"+(new Double(minf[i]).toString())+"\t"+(new Double(mean[i]).toString())+"\t"+(new Double(median[i]).toString());
	   out.println(a);
	
		
	}
	out.close();
	}
	catch(Exception e)
	{
		System.out.println("Error has occured while writing in file");
	}
	
	
	try {
		/*
		 * Change the path of output files
		 */
		FileWriter fw = new FileWriter("C:\\Users\\user\\Desktop\\OutputFiles\\5-D_Error.txt", true);
	    BufferedWriter bw = new BufferedWriter(fw);
	    PrintWriter out = new PrintWriter(bw);
		for(int i=0;i<28;i++){
			for(int j=0;j<7;j++){
			
				for(int k=0;k<11;k++){
					String a = "For function :"+(new Integer(i+1).toString())+ "run :"+(new Integer(j+1).toString())+" error at:"+(new Double(multiply[k]*Constants.NoOfIterations).toString())+" is :"+(new Double(error[i][j][k]).toString());
					out.println(a);
				}
			}
		}
		out.close();
		}
		catch(Exception e)
		{
			System.out.println("Error has occured while writing in file");
		}
	try {
		for(int i=0;i<28;i++){
			/*
			 * Change the path of output files
			 */
				FileWriter fw = new FileWriter("C:\\Users\\user\\Desktop\\OutputFiles\\f"+(i+1)+".txt", true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw);
				for(int k=0;k<11;k++){
					String p = new Double(multiply[k]*Constants.NoOfIterations).toString()+" ";
					for(int j=0;j<7;j++){
					p=p+new Double(ansplot[i][j][k]).toString()+" ";
				}
					out.println(p);
			}
				out.close();
		}
		
		}
		catch(Exception e)
		{
			System.out.println("Error has occured while writing in file");
		}
	
	
	
}
}
