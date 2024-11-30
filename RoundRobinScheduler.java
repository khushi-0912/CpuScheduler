package controller;

import model.Process;

import java.util.*;

public class RoundRobinScheduler {
    public void schedule(List<Process> processes, int quantum) {
        Queue<Process> queue = new LinkedList<>();
        int currentTime = 0;

        // Initialize processes
        for (Process process : processes) {
            process.setRemainingTime(process.getBurstTime());
            process.setWaitingTime(0); // Reset waiting time
        }

        // Add processes that have arrived at time 0 to the queue
        for (Process process : processes) {
            if (process.getArrivalTime() <= currentTime) {
                queue.add(process);
            }
        }

        while (!queue.isEmpty()) {
            // Get the next process to execute
            Process current = queue.poll();

            // Execute the process for the time quantum or until it finishes
            int executionTime = Math.min(current.getRemainingTime(), quantum);
            currentTime += executionTime;
            current.setRemainingTime(current.getRemainingTime() - executionTime);

            // Check if the process has finished
            if (current.getRemainingTime() == 0) {
                // Process completed
                current.setTurnaroundTime(currentTime - current.getArrivalTime()); // Calculate turnaround time
            }

            // Increment waiting time for other processes in the queue
            for (Process process : queue) {
                process.setWaitingTime(process.getWaitingTime() + executionTime);
            }

            // If the process is not finished, re-add it to the queue
            if (current.getRemainingTime() > 0) {
                queue.add(current);
            }

            // Add newly arrived processes to the queue
            for (Process process : processes) {
                if (process.getArrivalTime() <= currentTime &&
                        process.getRemainingTime() > 0 &&
                        !queue.contains(process)) {
                    queue.add(process);
                }
            }
        }

        // Final calculation for waiting time (if not already handled)
        for (Process process : processes) {
            process.setWaitingTime(process.getTurnaroundTime() - process.getBurstTime());
        }
    }
}
