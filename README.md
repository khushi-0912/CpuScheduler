--->OPERTING SYSTEM DESIGN PROJECT: CPU SCHEDULING<---

-Table of Contents:

1. Overview
2. Problem Statement
3. Features
4. Files and Directories
5. Prerequisites 
6. Installation 
7. Usage


-->OVERVIEW:

This project implements and compares three CPU scheduling algorithms: First Come First Served (FCFS), Shortest Job First (SJF), and Round Robin (RR). The goal was to evaluate their performance in managing process execution based on waiting time and turnaround time.

FCFS schedules processes in the order they arrive, but can lead to high waiting times, especially if long processes arrive first. SJF aims to minimize waiting time by selecting the shortest process, but requires knowledge of burst times in advance. Round Robin ensures fairness by giving each process a fixed time slice, but its performance depends on the time quantum chosen.

The comparison highlights each algorithm's strengths and weaknesses, providing insights into their optimal use cases for effective CPU scheduling.

-->PROBLEM STATEMENT:

Choosing the right CPU scheduling algorithm for a specific scenario is a critical challenge in operating systems, as different algorithms have distinct characteristics that affect system performance in various ways. The performance of a scheduling algorithm is evaluated based on several criteria, such as waiting time and turnaround time. Depending on the situation, some criteria may take precedence over others. The key challenge lies in selecting the most suitable scheduling algorithm based on these varying performance criteria.

This project aims to tackle this challenge by providing a CPU Scheduling Simulator, which allows users to compare the performance of three key CPU scheduling algorithms: First-Come, First-Served (FCFS), Shortest Job First (SJF), and Round Robin (RR). The simulator enables users to experiment with different algorithms by selecting the number of processes, inputting relevant process details such as CPU burst time and arrival time and observing the execution behavior of the processes. By using this tool, users can gain a deeper understanding of how different scheduling algorithms perform under various conditions and make informed decisions based on their specific needs.

--> FEATURES:

1. Comparison of Multiple Algorithms:

The simulator allows users to compare the performance of three important CPU scheduling algorithms: First-Come, First-Served (FCFS), Shortest Job First (SJF), and Round Robin (RR). This provides flexibility to evaluate the algorithms in different scenarios.

2. Customizable Process Inputs:

Users can define the number of processes and enter key parameters for each process, including CPU burst time and arrival time. This allows for a wide variety of test cases and scenarios to be simulated. 

3. In-Built Graphical User Interface:

The simulator is built as a basic Java application with an in-built Graphical User Interface (GUI). This allows for easy interaction with the scheduling algorithms. The Java-based application ensures that the simulation runs smoothly and provides an intuitive platform for users to input process details, select scheduling algorithms, and visualize the execution of processes in real-time.

-->FILES AND DIRECTORIES:

1. app.java
2. process.java
3. SjfSchedular.java
4. FcfsSchedular.java
5. RoundRobinSchedular.java
5. style.css

--> PREREQUISITES:

1. Java Development Kit (JDK) 17 or higher.
2. JavaFX dependencies.
3. Any Java IDE like IntelliJ or Eclipse.

--> INSTALLATION:

1. Clone the repository to your local machine using:

2. Navigate to the project directory:

3. Open the project in your preferred Java IDE.

--> USAGE:

1. Compile the project:
 
Ensure all .java files are compiled.

2. Run the Simulator:

Execute the main class in src/main/ to launch the simulator's user interface. Choose the desired CPU scheduling algorithm and input the process data.

3. View Results:

The simulator will display the sequence of process execution and calculate the waiting time and turnaround time.



