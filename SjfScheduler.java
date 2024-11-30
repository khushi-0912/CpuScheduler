package controller;

import model.Process;

import java.util.ArrayList;
import java.util.List;

public class SjfScheduler {
    public void schedule(List<Process> processes) {
        // Track the current time
        int currentTime = 0;

        // Create a list for the remaining processes
        List<Process> remainingProcesses = new ArrayList<>(processes);

        while (!remainingProcesses.isEmpty()) {
            // Get all processes that have arrived up to the current time
            List<Process> availableProcesses = new ArrayList<>();
            for (Process process : remainingProcesses) {
                if (process.getArrivalTime() <= currentTime) {
                    availableProcesses.add(process);
                }
            }

            if (!availableProcesses.isEmpty()) {
                // Sort available processes by burst time (Shortest Job First)
                availableProcesses.sort((p1, p2) -> Integer.compare(p1.getBurstTime(), p2.getBurstTime()));

                // Pick the process with the shortest burst time
                Process shortestProcess = availableProcesses.get(0);

                // Update its waiting time and turnaround time
                shortestProcess.setWaitingTime(currentTime - shortestProcess.getArrivalTime());
                currentTime += shortestProcess.getBurstTime();
                shortestProcess.setTurnaroundTime(shortestProcess.getWaitingTime() + shortestProcess.getBurstTime());

                // Remove the process from the remaining list
                remainingProcesses.remove(shortestProcess);
            } else {
                // If no process is ready, increment the current time
                currentTime++;
            }
        }
    }
}
