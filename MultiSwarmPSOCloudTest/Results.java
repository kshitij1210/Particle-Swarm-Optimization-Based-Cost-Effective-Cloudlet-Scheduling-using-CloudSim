package MultiSwarmPSOCloudTest;
import java.util.*;
public class Results {

	public static void main(String[] args)throws Exception {
		Scanner sc= new Scanner(System.in);
		System.out.println("Enter the Number of times you want to compare results of PSO based and Random1 Scheduling");
		int n=sc.nextInt();
		double arrres[][]= new double[n][2];
		for(int i=0;i<n;i++) {
			/*
			 * For Static Case of Fixed 10 tasks on 8 vms with fixed specifications
			 * 
			 */
			int tasklength[]= {3000,2000,1000,5000,4000,3500,2500,1500,6000,1300};
			int outputfilesize[] = {300,400,100,500,350,700,400,800,1000,550};
			int mips[]= {80,50,10,10,5,15,20,60};
			double execcost[] = {6,10,2,0.5,0.5,4.5,2,7};
			double waitcost[] = {6,10,2,0.5,0.5,4.5,2,7};
			int graph[][] = { {0,1,1,0,0,0,0,0,0,0},
					   {0,0,0,1,0,0,0,0,0,0},
					   {0,0,0,1,0,0,0,0,0,0},
					   {0,0,0,0,0,0,0,1,0,0},
					   {0,0,0,0,0,0,0,0,0,0},
					   {0,0,0,0,0,0,0,0,0,0},
					   {0,0,0,0,0,0,0,0,0,0},
					   {0,0,0,0,0,0,0,0,1,0},
					   {0,0,0,0,0,0,0,0,0,0},
					   {0,0,0,0,0,0,0,0,0,0} };
			/*
			 * For Dynamic Case of Fixed 10 tasks on 8 vms with fixed specifications
			 * Number of VMs and Tasks can be changed in Constants file
			 * 
			 */
//			int tasklength[]=new int[Constants.NoOfTasks];
//			int outputfilesize[]=new int[Constants.NoOfTasks];
//			int mips[]= new int[Constants.NoOfVMs];
//			double execcost[]= new double[Constants.NoOfVMs];
//			double waitcost[]= new double[Constants.NoOfVMs];
//			int graph[][] = new int[Constants.NoOfTasks][Constants.NoOfTasks];
//			System.out.println("Enter the task length (in mi) for "+Constants.NoOfTasks+" tasks");
//			for(int j=0;j<Constants.NoOfTasks;j++)
//				tasklength[j]=sc.nextInt();
//			System.out.println("Enter the outputfilesize for "+Constants.NoOfTasks+" tasks");
//			for(int j=0;j<Constants.NoOfTasks;j++)
//				outputfilesize[j]=sc.nextInt();
			

//			System.out.println("Enter the MIPS for"+Constants.NoOfVMs+" VMs");
//			for(int j=0;j<Constants.NoOfVMs;j++)
//				mips[j]=sc.nextInt();
//			System.out.println("Enter the Execution Cost for"+Constants.NoOfVMs+" VMs");
//			for(int j=0;j<Constants.NoOfVMs;j++)
//				execcost[j]= sc.nextDouble();
//			System.out.println("Enter the Waiting Cost for"+Constants.NoOfVMs+" VMs");
//			for(int j=0;j<Constants.NoOfVMs;j++)
//				waitcost[j]= sc.nextDouble();
//			System.out.println("Enter the acyclic dependency graph as adjacency matrix for task to task dependency");
//			for(int j=0;i<Constants.NoOfTasks;i++)
//			{
//				for(int k=0;k<Constants.NoOfTasks;k++) {
//					graph[j][k]=sc.nextInt();
//				}
//			}
			TaskScheduler3 obj = new TaskScheduler3();
			double ans[]=obj.func(tasklength,outputfilesize,mips,execcost,waitcost,graph);
			arrres[i][0]=ans[0];
			arrres[i][1]=ans[1];
			System.out.println("Cost of PSO Based Scheduling:"+ans[0]+"\tCost of Random Scheduling:"+ans[1]);
		}
		for(int i=0;i<n;i++)
		{
			System.out.println(arrres[i][0]+"\t"+arrres[i][1]);
		}
	}

}
