# Particle-Swarm-Optimization-Based-Cost-Effective-Cloudlet-Scheduling-using-CloudSim

## Problem Statement
The problem at hand is to minimize the total cost of processing cloudlets by optimal scheduling on given Virtual Machines. Our hypothesis is that we can obtain better cost using Particle Swarm Optimization to schedule our tasks as compared to Random Scheduling algorithm.

## Setup Used
1.) Language used - Java

2.) Operating System - Windows 10

3.) RAM - 8 GB

4.) CloudSim version - 3.0.3

5.) JSwarm version - 2.08

6.) Oracle VirtualBox version - 5.1.28

7.) Ubuntu OS version - 14.04.3

## Details
The comparisons were done between Random Scheduling and Particle Swarm Optimization. Also, 4 different variants of PSO were used and tested against **CEC-2013 28 benchmark functions**. 

(Link to CEC-2013 code
![alt_txt]http://web.mysites.ntu.edu.sg/epnsugan/PublicSite/Shared%20Documents/CEC2013/cec13%20java%20code.rar )

The 4 variants are namely - 

1.) Standard PSO heuristic

2.) PSO with Linearly Decreasing Inertia Weight

3.) PSO with improved Constriction Factor

4.) PSO with both Linearly Decreasing Inertia Weight and improved Constriction Factor

Among them, the best variant was selected for Cloudlet Scheduling. Another heuristic of PSO known as MultiSwarm PSO was also used and tested. In this heuristic instead of using a single swarm, the swarm was divided into a number of smaller swarms with their own respective velocities. The 4 variants stated above were also implemented using MultiSwarm heuristic and tested against **CEC CEC-2013 28 benchmark functions** and also the best variant among them is selected for scheduling cloudlets. Both Single Swarm and MultiSwarm PSO were tested against Random Scheduling and it was observed that both performed a lot better than Random Scheduling. Our work was done keeping in mind both independent and dependent tasks. In our code, there are 4 packages - 

1.) **PSOCEC2013** : Which contains 4 variants of PSO codes using JSwarm which are tested on CEC functions.

2.) **PSOCloud** : Which uses the PSO which are tested on CEC to simulate the cloud scenarios for cost effective scheduling.

3.) **MultiSwarm** : Which contains MultiSwarm PSO codes which are tested on CEC functions.

4.) **MultiSwarmPSOCloudTest** :  Which uses the MultiSwarm PSO to simulate the cloud scenarios for cost effective scheduling.


Also we tried to implement the same on virtual machines in Oracle Virtual Box to test and compare our CloudSim reasults on real time environment. We used the CloudSim PSO to obtain the best optimized mapping for in which order and on which virtual machines our cloudlets should run. The shell scripts generated while running the PSOCloud and MultiSwarmPSOCloudTest can be used to run the different corresponding cloudlets on different VMs even in case of independent or dependent tasks.

## How to Set up VirtualBox Instances
1. Create the number of VM instances as specified in the cloud scenario and the number of programs(Cloudlets).

2. Install Ubuntu-OS in VMs.

3. Create a shared folder between the host OS and the VM instances on VirtualBox.

4. After getting a mapping from PSO corresponding shell scripts are generated.

5. Run commands in VM.
	
	1->cd to the shared folder (/media/psobroker) where all the codes are present and then to pso or random
  
	2->javac createconvert.java
  
	3->java createconvert (NoofVMs) runpsovm-(VMNo).sh
  
	4->sh convert.sh

	Do upto step 4 in all the VMs parallely.
	Run step 5 in all the VMs one by one
  
	5->sh runpsovm-(VMNo).sh
  
	6->corresponding output files will be generated in the shared folder which are accessible by host system as well.

**Check for dos to unix conversion in case errors in opening the files between host and VMs**

## Files to run in each package

1.) **PSOCEC2013** : PSO

2.) **PSOCloud** : ResCalc

3.) **MultiSwarm** : PSOSubSwarm

4.) **MultiSwarmPSOCloudTest** : Results
