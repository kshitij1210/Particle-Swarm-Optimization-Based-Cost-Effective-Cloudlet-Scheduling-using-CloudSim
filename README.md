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
The comparisons were done between Random Scheduling and Particle Swarm Optimization. Also, 4 different variants of PSO were used and tested against **CEC-2013 28 benchmark functions**. Among them, the best variant was selected for Cloudlet Scheduling. Another heuristic of PSO known as MultiSwarm PSO was also used and tested. In this heuristic instead of using a single swarm, the swarm was divided into a number of smaller swarms with their own respective velocities. The 4 variants stated above were also implemented using MultiSwarm heuristic and tested against **CEC CEC-2013 28 benchmark functions** and also the best variant among them is selected for scheduling cloudlets. Both Single Swarm and MultiSwarm PSO were tested against Random Scheduling and it was observed that both performed a lot better than Random Scheduling. Our work was done keeping in mind both independent and dependent tasks. In our code, there are 4 packages - 
1.) **PSOCEC2013** : Which contains 4 variants of PSO codes using JSwarm which are tested on CEC functions.
2.) **PSOCloud** : Which uses the PSO which are tested on CEC to simulate the cloud scenarios for cost effective scheduling.
3.) **MultiSwarm** : Which contains MultiSwarm PSO codes which are tested on CEC functions.
4.) **MultiSwarmPSOCloudTest** :  Which uses the MultiSwarm PSO to simulate the cloud scenarios for cost effective scheduling.

Also we tried to implement the same on virtual machines in Oracle Virtual Box to test and compare our CloudSim reasults on real time environment. 

