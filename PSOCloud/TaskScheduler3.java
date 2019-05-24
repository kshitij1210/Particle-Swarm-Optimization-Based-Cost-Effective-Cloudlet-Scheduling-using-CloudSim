package PSOCloud;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.io.*;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletSchedulerSpaceShared;
import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;


public class TaskScheduler3 {

	/** The cloudlet list. */
	private static List<Cloudlet> cloudletList;

	/** The vmlist. */
	private static List<Vm> vmlist;
	/*
	 * mapping: represents the mapping of cloudlets to vm based on MultiSwarmPSO
	 * mapping2: represents the mapping of cloudlets to vm based on Random Scheduling
	 * resultcost: stores the cost of cloudlet execution based on the mappings
	 * other parameters are defined in FitnessFunction
	 */
	public static double mapping[];
	public static double[][] executiontimematrix;
	public static double[][] communicationtimematrix;
	public static double[][] datatransfermatrix;
	public static double[][] taskoutputfilematrix;
 	public static double[][] commcost;
	public static double[] mapping2 = new double[Constants.NoOfTasks];
	public static int depgraph[][] = new int[Constants.NoOfTasks][Constants.NoOfTasks];

	
	public static double[] resultcost= new double[2];

	/**
	 * Creates main() to run this example
	 */
	public double[] getPSOMapping() {
		return mapping;
	}

	/*
	 * This function is used to create shell script which will run scripts of respective vm
	 * for both the mappings
	 * 
	 */
	public static void createvmrunscript(int vmno)throws Exception{
		FileWriter fw = new FileWriter("C:\\Users\\admin\\Desktop\\psobroker\\pso\\runpsovm-"+vmno+".sh");
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter pw = new PrintWriter(bw);
		pw.println("#!/bin/bash");
		pw.println("sh vm-"+vmno+".sh");
		pw.close();
		bw.close();
		fw.close();
		FileWriter fw1 = new FileWriter("C:\\Users\\admin\\Desktop\\psobroker\\random\\runrandomvm-"+vmno+".sh");
		BufferedWriter bw1 = new BufferedWriter(fw1);
		PrintWriter pw1 = new PrintWriter(bw1);
		pw1.println("#!bin/bash");
		pw1.println("sh vm-"+vmno+".sh");
		pw1.close();
		bw1.close();
		fw1.close();	
}
	
	/*
	 * This function creates shell scripts to run java programs if all the dependent output
	 * files are present. It calculates the time to execute the program for each vm as well.
	 * It also stores the output in the corresponding output file as well.
	 *  
	 */
	public static void createscript(double mapping[],String Folder)throws Exception{
		int no_of_vms=Constants.NoOfVMs;
		for(int i=0;i<no_of_vms;i++){
		FileWriter fw = new FileWriter("C:\\Users\\admin\\Desktop\\psobroker\\"+Folder+"\\vm-"+i+".sh");
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter pw = new PrintWriter(bw);
		pw.println("#!/bin/bash");
		pw.println("vmno="+i);
		pw.println("echo \"Results for vm $vmno \" ");
		pw.println("start=$(date +\"%T\")");
		for(int j=0;j<mapping.length;j++){
		if(i==(int)mapping[j]){
			for(int k=0;k<Constants.NoOfTasks;k++) {
				if(depgraph[j][k]==1) {
					pw.println("while [ ! -f ans_p"+k+".txt ]\r\n" + 
							"do\n" + 
							"echo \" waiting for dependent task output \" \n" + 
							"done\n");
				}
			}
			pw.println("javac p"+(int)j+".java");
			pw.println("java p"+(int)j);
		}
		}
		pw.println("stop=$(date +\"%T\")");
		pw.println("echo \"Start Time : $start\"");
		pw.println("echo \"Stop Time : $stop\"");
		pw.close();
		bw.close();
		fw.close();
		}
	}
	
	/*
	 * This function is used for the simulation of Cloud Scenarios using CloudSim.
	 */
	
	public static double[] func(int[] tasklength,int[] outputfilesize,int[] mips,double[] execcost,double[] waitcost,int[][] graph) throws Exception {
		/*
		 * Depgraph denotes that a task requires output files from which tasks
		 */
		for(int j=0;j<Constants.NoOfTasks;j++) {
			for(int k=0;k<Constants.NoOfTasks;k++) {
				depgraph[k][j]=graph[j][k];
			}
		}
		/*
		 * Run the PSO to obtain the mapping of cloudlets to VM
		 */
		PSO PSOScheduler = new PSO(tasklength,outputfilesize,mips,execcost,waitcost,graph);
		 mapping= PSOScheduler.run();
		 for(int i=0;i<Constants.NoOfVMs;i++)
		 createvmrunscript(i);
		 createscript(mapping,"pso");
		 executiontimematrix = PSOScheduler.getexecutiontimematrix();
		 communicationtimematrix = PSOScheduler.getcommunicationtimematrix();
	     datatransfermatrix = PSOScheduler.getdatatransfermatrix();
	     commcost = PSOScheduler.getcommcost();
	     taskoutputfilematrix =PSOScheduler.gettaskoutputfilematrix();
		Log.printLine("Divyanshu Mishra (20155043)");
		
		Log.printLine(" Starting TaskScheduler having 1 datacenter with different VMs...");

		try {
			// First step: Initialize the CloudSim package. It should be called
			// before creating any entities.
			int num_user = 1;   // number of cloud users
			Calendar calendar = Calendar.getInstance();
			boolean trace_flag = false;  // mean trace events

			// Initialize the CloudSim library
			CloudSim.init(num_user, calendar, trace_flag);

			// Second step: Create Datacenters
			//Datacenters are the resource providers in CloudSim. We need at list one of them to run a CloudSim simulation
			@SuppressWarnings("unused")
			Datacenter datacenter0 = createDatacenter("Datacenter_0");

			//Third step: Create Broker
			MyDataCenterBroker broker = createBroker();

			int brokerId = broker.getId();

			//Fourth step: Create one virtual machine
			vmlist = new ArrayList<Vm>();

			//VM description
			int vmid = 0;
			long size = 10000; //image size (MB)
			int ram = 256; //vm memory (MB)
			long bw = 1000;
			int pesNumber = 1; //number of cpus
			String vmm = "Xen"; //VMM name

			//create two VMs
			for(int i=0;i<Constants.NoOfVMs;i++)
			{Vm vm = new Vm(vmid, brokerId, mips[i], pesNumber, ram, bw, size, vmm, new CloudletSchedulerSpaceShared());
			vmid++;
			//add the VMs to the vmList
			vmlist.add(vm);
			}

			//submit vm list to the broker
			broker.submitVmList(vmlist);


			//Fifth step: Create two Cloudlets
			cloudletList = new ArrayList<Cloudlet>();

			//Cloudlet properties
			
			long fileSize = 300;
//			long outputSize[] = outputfilesize;
			UtilizationModel utilizationModel = new UtilizationModelFull();
			for(int id=0;id<Constants.NoOfTasks;id++) {
			Cloudlet cloudlet1 = new Cloudlet(id, tasklength[id] , pesNumber, fileSize, outputfilesize[id], utilizationModel, utilizationModel, utilizationModel);
			cloudlet1.setUserId(brokerId);
			cloudletList.add(cloudlet1);

			}

			//submit cloudlet list to the broker
			broker.submitCloudletList(cloudletList);
			
			double delay[] = new double[Constants.NoOfTasks];
			delay[0]=0;
			for(int i=0;i<Constants.NoOfTasks;i++){
				for(int j=i+1;j<Constants.NoOfTasks;j++){
					if(taskoutputfilematrix[i][j]!=0) {
					delay[j]=Math.max(delay[j],delay[i]+executiontimematrix[i][(int)mapping[i]]+communicationtimematrix[i][j]);
					}
				}
			}


			broker.submitMapping(mapping);
			broker.submitDelay(delay);
			

			// Sixth step: Starts the simulation
			CloudSim.startSimulation();


			// Final step: Print results when simulation is over
			List<Cloudlet> newList = broker.getCloudletReceivedList();

			CloudSim.stopSimulation();

        	printCloudletList(newList);
        	
        	resultcost[0]=PSOScheduler.printBestFitness();
			Log.printLine("Simulation of Task Scheduler using PSO is finished!");
			for(int i=0;i<Constants.NoOfTasks;i++)
				System.out.print(mapping[i]);
			System.out.println();
			
			for(int i=0;i<Constants.NoOfTasks;i++) {
				int vm = (int) mapping [i];
				System.out.print(""+executiontimematrix[i][vm]+"*"+execcost[vm]+"\n");
			}
			
			randomscheduler(executiontimematrix,communicationtimematrix,datatransfermatrix,commcost,taskoutputfilematrix,tasklength,outputfilesize,mips,execcost,waitcost,graph);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			Log.printLine("The simulation has been terminated due to an unexpected error");
		}
		return resultcost;
	}
	
	public static void randomscheduler(double[][] executiontimematrix, double[][] communicationtimematrix, double[][] datatransfermatrix, double[][] commcost,double[][] taskoutputfilematrix,int[] tasklength,int[] outputfilesize,int[] mips,double[] execcost,double[] waitcost,int[][] graph)throws Exception {
	
		double[] mapping2 = new double[Constants.NoOfTasks];
		
		Log.printLine(" Starting TaskScheduler having 1 datacenter with different VMs...");

		try {
			// First step: Initialize the CloudSim package. It should be called
			// before creating any entities.
			int num_user = 1;   // number of cloud users
			Calendar calendar = Calendar.getInstance();
			boolean trace_flag = false;  // mean trace events

			// Initialize the CloudSim library
			CloudSim.init(num_user, calendar, trace_flag);

			// Second step: Create Datacenters
			//Datacenters are the resource providers in CloudSim. We need at list one of them to run a CloudSim simulation
			@SuppressWarnings("unused")
			Datacenter datacenter0 = createDatacenter("Datacenter_0");

			//Third step: Create Broker
			MyDataCenterBroker broker = createBroker();
			int brokerId = broker.getId();

			//Fourth step: Create one virtual machine
			vmlist = new ArrayList<Vm>();

			//VM description
			int vmid = 0;
			
			//int mips[] = {80, 50, 10, 10, 5, 15, 20, 60};
			long size = 10000; //image size (MB)
			int ram = 256; //vm memory (MB)
			long bw = 1000;
			int pesNumber = 1; //number of cpus
			String vmm = "Xen"; //VMM name

			//create two VMs
			for(int i=0;i<Constants.NoOfVMs;i++)
			{Vm vm = new Vm(vmid, brokerId, mips[i], pesNumber, ram, bw, size, vmm, new CloudletSchedulerSpaceShared());
			vmid++;
			//add the VMs to the vmList
			vmlist.add(vm);
			}

			//submit vm list to the broker
			broker.submitVmList(vmlist);


			//Fifth step: Create two Cloudlets
			cloudletList = new ArrayList<Cloudlet>();

			//Cloudlet properties
			//This is the mips rating(Million Instructions per second which are processed) of each VM that is being called
			//This is the cost of execution on different VM per unit time.
			
			UtilizationModel utilizationModel = new UtilizationModelFull();
			for(int i=0;i<Constants.NoOfTasks;i++)
        		mapping2[i]=(int)(Math.random()*(Constants.NoOfVMs));
			
			createscript(mapping2,"random");
			
			for(int i=0;i<10;i++)
				System.out.print((int)mapping2[i]+" ");

			long fileSize = 300;
			for(int id=0;id<Constants.NoOfTasks;id++) {
			Cloudlet cloudlet1 = new Cloudlet(id, tasklength[id], pesNumber, fileSize, outputfilesize[id], utilizationModel, utilizationModel, utilizationModel);
			cloudlet1.setUserId(brokerId);
			cloudletList.add(cloudlet1);

			}

			//submit cloudlet list to the broker
			

			broker.submitCloudletList(cloudletList);
			double delay2[] = new double[Constants.NoOfTasks];
			delay2[0]=0;
			
			for(int i=0;i<Constants.NoOfTasks;i++){
				for(int j=i+1;j<Constants.NoOfTasks;j++){
					if(taskoutputfilematrix[i][j]!=0) {
					delay2[j]=Math.max(delay2[j],delay2[i]+executiontimematrix[i][(int)mapping2[i]]+communicationtimematrix[i][j]);
					}
				}
			}
			broker.submitMapping(mapping2);
			broker.submitDelay(delay2);
			//bind the cloudlets to the vms. This way, the broker
			// will submit the bound cloudlets only to the specific VM
			
			
			double cost = 0.0;
			double[] vmworkingcost = new double[Constants.NoOfVMs];

			double waiting =0.0;
			for(int i=0;i<Constants.NoOfTasks;i++) {
				int vm = (int) mapping2 [i];
				vmworkingcost[vm]+=(executiontimematrix[i][vm])*execcost[vm];
				System.out.println(""+executiontimematrix[i][vm]+"*"+execcost[vm]);
			}
			for(int i=0;i<Constants.NoOfTasks;i++) {
				int vm = (int) mapping2 [i];
				for(int j=i+1;j<Constants.NoOfTasks;j++) {
				vmworkingcost[vm]+=(communicationtimematrix[i][j])*commcost[i][j];
				}
			}
			for(int i=0;i<Constants.NoOfVMs;i++)
				cost+=vmworkingcost[i];
			for(int i=0;i<Constants.NoOfTasks;i++) {
				cost+= delay2[i]*waitcost[(int)mapping2[i]];
			}
						
			System.out.println("the cost for random scheduling is "+ cost);
			
			// Sixth step: Starts the simulation
			CloudSim.startSimulation();


			// Final step: Print results when simulation is over
			List<Cloudlet> newList2 = broker.getCloudletReceivedList();

			CloudSim.stopSimulation();

        	printCloudletList(newList2);
        	
        	resultcost[1]=cost;
			Log.printLine("Simulation of Task Scheduler using Random scheduling is finished!");
		}
		catch (Exception e) {
			e.printStackTrace();
			Log.printLine("The simulation has been terminated due to an unexpected error");
		}

	}

	

	private static Datacenter createDatacenter(String name){

		// Here are the steps needed to create a PowerDatacenter:
		// 1. We need to create a list to store
		//    our machine
		List<Host> hostList = new ArrayList<Host>();

		// 2. A Machine contains one or more PEs or CPUs/Cores.
		// In this example, it will have only one core.
		List<Pe> peList = new ArrayList<Pe>();

		int mips = 1000;

		// 3. Create PEs and add these into a list.
		peList.add(new Pe(0, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating

		//4. Create Hosts with its id and list of PEs and add them to the list of machines
		int hostId=0;
		int ram = 2048; //host memory (MB)
		long storage = 1000000; //host storage
		int bw = 10000;

		hostList.add(
    			new Host(
    				hostId,
    				new RamProvisionerSimple(ram),
    				new BwProvisionerSimple(bw),
    				storage,
    				peList,
    				new VmSchedulerTimeShared(peList)
    			)
    		); // This is our first machine

		//create another machine in the Data center
		List<Pe> peList2 = new ArrayList<Pe>();

		peList2.add(new Pe(0, new PeProvisionerSimple(mips)));

		hostId++;

		hostList.add(
    			new Host(
    				hostId,
    				new RamProvisionerSimple(ram),
    				new BwProvisionerSimple(bw),
    				storage,
    				peList2,
    				new VmSchedulerTimeShared(peList2)
    			)
    		); // This is our second machine



		// 5. Create a DatacenterCharacteristics object that stores the
		//    properties of a data center: architecture, OS, list of
		//    Machines, allocation policy: time- or space-shared, time zone
		//    and its price (G$/Pe time unit).
		String arch = "x86";      // system architecture
		String os = "Linux";          // operating system
		String vmm = "Xen";
		double time_zone = 10.0;         // time zone this resource located
		double cost = 3.0;              // the cost of using processing in this resource
		double costPerMem = 0.05;		// the cost of using memory in this resource
		double costPerStorage = 0.001;	// the cost of using storage in this resource
		double costPerBw = 0.0;			// the cost of using bw in this resource
		LinkedList<Storage> storageList = new LinkedList<Storage>();	//we are not adding SAN devices by now

        DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
                arch, os, vmm, hostList, time_zone, cost, costPerMem, costPerStorage, costPerBw);

		// 6. Finally, we need to create a PowerDatacenter object.
		Datacenter datacenter = null;
		try {
			datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return datacenter;
	}

	// Broker policy using PSO
	private static MyDataCenterBroker createBroker(){

		MyDataCenterBroker broker = null;
		try {
			broker = new MyDataCenterBroker("Broker");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return broker;
	}
	/**
	 * Prints the Cloudlet objects
	 * @param list  list of Cloudlets
	 */
	private static void printCloudletList(List<Cloudlet> list) {
		int size = list.size();
		Cloudlet cloudlet;

		String indent = "    ";
		Log.printLine();
		Log.printLine("========== OUTPUT ==========");
		Log.printLine("Cloudlet ID" + indent + "STATUS" + indent +
				"Data center ID" + indent + "VM ID" + indent + "Time" + indent + "Start Time" + indent + "Finish Time");

		DecimalFormat dft = new DecimalFormat("###.##");
		for (int i = 0; i < size; i++) {
			cloudlet = list.get(i);
			Log.print(indent + cloudlet.getCloudletId() + indent + indent);

			if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS){
				Log.print("SUCCESS");

				Log.printLine( indent + indent + cloudlet.getResourceId() + indent + indent + indent + cloudlet.getVmId() +
						indent + indent + dft.format(cloudlet.getActualCPUTime()) + indent + indent + dft.format(cloudlet.getExecStartTime())+
						indent + indent + dft.format(cloudlet.getFinishTime()));
			}
		}

	}
}
