package view;

import controller.FcfsScheduler;
import controller.SjfScheduler;
import controller.RoundRobinScheduler;
import model.Process;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class SchedulerUI extends Application {
    private List<Process> processes = new ArrayList<>();
    private TableView<Process> resultTable = new TableView<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("CPU Scheduler");

        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f4f4f4; -fx-font-family: 'Arial';");

        Label title = new Label("CPU Scheduler Simulator");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        HBox algorithmBox = new HBox(10);
        Label algorithmLabel = new Label("Select Algorithm:");
        algorithmLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #555;");
        ComboBox<String> algorithmDropdown = new ComboBox<>();
        algorithmDropdown.getItems().addAll("FCFS", "SJF", "Round Robin");
        algorithmDropdown.setValue("FCFS");
        algorithmDropdown.setStyle("-fx-font-size: 14px;");

        TextField quantumField = new TextField();
        quantumField.setPromptText("Time Quantum (RR)");
        quantumField.setVisible(false);
        quantumField.setStyle("-fx-font-size: 14px;");

        algorithmDropdown.setOnAction(e -> quantumField.setVisible(algorithmDropdown.getValue().equals("Round Robin")));
        algorithmBox.getChildren().addAll(algorithmLabel, algorithmDropdown, quantumField);

        TextField processField = new TextField();
        processField.setPromptText("Enter number of processes");
        processField.setStyle("-fx-font-size: 14px; -fx-padding: 5;");

        Button generateButton = new Button("Generate");
        generateButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5 15;");
        VBox inputContainer = new VBox(10);

        generateButton.setOnAction(e -> {
            try {
                int count = Integer.parseInt(processField.getText());
                inputContainer.getChildren().clear();
                processes.clear();

                for (int i = 0; i < count; i++) {
                    HBox row = new HBox(10);
                    Label processLabel = new Label("P" + (i + 1) + ":");
                    processLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #555;");
                    TextField arrivalField = new TextField();
                    arrivalField.setPromptText("Arrival Time");
                    arrivalField.setStyle("-fx-font-size: 14px;");
                    TextField burstField = new TextField();
                    burstField.setPromptText("Burst Time");
                    burstField.setStyle("-fx-font-size: 14px;");

                    row.getChildren().addAll(processLabel, arrivalField, burstField);
                    inputContainer.getChildren().add(row);

                    processes.add(new Process(i + 1, 0, 0)); // Placeholder values
                }
            } catch (NumberFormatException ex) {
                showErrorDialog("Invalid Input", "Please enter a valid number of processes.");
            }
        });

        setupResultTable();

        Button calculateButton = new Button("Calculate");
        calculateButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5 15;");
        calculateButton.setOnAction(e -> {
            try {
                for (int i = 0; i < inputContainer.getChildren().size(); i++) {
                    HBox row = (HBox) inputContainer.getChildren().get(i);
                    TextField arrivalField = (TextField) row.getChildren().get(1);
                    TextField burstField = (TextField) row.getChildren().get(2);

                    processes.get(i).setArrivalTime(Integer.parseInt(arrivalField.getText()));
                    processes.get(i).setBurstTime(Integer.parseInt(burstField.getText()));
                }

                switch (algorithmDropdown.getValue()) {
                    case "FCFS":
                        new FcfsScheduler().schedule(processes);
                        break;
                    case "SJF":
                        new SjfScheduler().schedule(processes);
                        break;
                    case "Round Robin":
                        if (quantumField.getText().isEmpty() || !quantumField.getText().matches("\\d+")) {
                            showErrorDialog("Invalid Input", "Please enter a valid time quantum for Round Robin.");
                            return;
                        }
                        int quantum = Integer.parseInt(quantumField.getText());
                        new RoundRobinScheduler().schedule(processes, quantum);
                        break;
                }

                displayResults(processes);

            } catch (NumberFormatException ex) {
                showErrorDialog("Invalid Input", "Please ensure all fields are filled with valid numbers.");
            }
        });

        root.getChildren().addAll(title, algorithmBox, processField, generateButton, inputContainer, calculateButton, resultTable);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setupResultTable() {
        TableColumn<Process, Integer> idColumn = new TableColumn<>("Process");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Process, Integer> arrivalColumn = new TableColumn<>("Arrival");
        arrivalColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));

        TableColumn<Process, Integer> burstColumn = new TableColumn<>("Burst");
        burstColumn.setCellValueFactory(new PropertyValueFactory<>("burstTime"));

        TableColumn<Process, Integer> waitingColumn = new TableColumn<>("Waiting");
        waitingColumn.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));

        TableColumn<Process, Integer> turnaroundColumn = new TableColumn<>("Turnaround");
        turnaroundColumn.setCellValueFactory(new PropertyValueFactory<>("turnaroundTime"));

        resultTable.getColumns().clear();
        resultTable.getColumns().addAll(idColumn, arrivalColumn, burstColumn, waitingColumn, turnaroundColumn);
        resultTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        resultTable.setStyle("-fx-font-size: 14px;");
    }

    private void displayResults(List<Process> processes) {
        Platform.runLater(() -> {
            resultTable.getItems().clear();
            resultTable.setItems(FXCollections.observableArrayList(processes));
        });

        double avgWaitingTime = processes.stream().mapToInt(Process::getWaitingTime).average().orElse(0);
        double avgTurnaroundTime = processes.stream().mapToInt(Process::getTurnaroundTime).average().orElse(0);

        Label averagesLabel = new Label(String.format("Average Waiting Time: %.2f | Average Turnaround Time: %.2f",
                avgWaitingTime, avgTurnaroundTime));
        averagesLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 10px;");

        VBox root = (VBox) resultTable.getParent();
        if (root.getChildren().size() > 7) root.getChildren().remove(7);
        root.getChildren().add(averagesLabel);
    }

    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
