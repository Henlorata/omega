package com.example.utazokgui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class HelloController {

  @FXML
  private FileChooser fileChooser;
  @FXML
  private ListView<String> cityList;
  @FXML
  private ListView<String> utasList;

  ArrayList<Utazok> data;


  @FXML
  private void initialize() {
    fileChooser = new FileChooser();
    fileChooser.setTitle("Megnyitás");
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("csv Files", "*.csv"),
        new FileChooser.ExtensionFilter("All Files", "*.*")
    );
    fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
//    fileChooser.setInitialFileName("utazok.csv");
  }

  @FXML
  private void open() {
    File file = fileChooser.showOpenDialog(cityList.getScene().getWindow());
    if (file == null || !file.exists()) return;
    data = Utazok.readFileLines(file.getAbsolutePath());
    if (data.isEmpty()) {
      System.err.println("No data found in the file.");
      return;
    }
    cityList.getItems().clear();
    data.stream().map(utazok -> utazok.place).distinct().forEach(cityList.getItems()::add);
    cityList.getSelectionModel().select(0);
    show();
  }

  @FXML
  private void show() {
    if (data.isEmpty()) return;
    utasList.getItems().clear();
    data.stream()
        .filter(utazok -> utazok.place.equals(cityList.getSelectionModel().getSelectedItem()))
        .forEach(utazok -> utasList.getItems().add(utazok.name + " (" + utazok.date + " " + utazok.time + ")"));
  }

  @FXML
  private void help() {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Névjegy");
    alert.setHeaderText(null);
    alert.setContentText("Utazók v1.0.0\n(C) Kandó");
    alert.showAndWait();
  }

  @FXML
  private void exit() {
    Platform.exit();
  }

  public static class Utazok {

    String name;
    String place;
    String date;
    String time;

    Utazok(String line) {
      String[] parts = line.split(";");
      this.name = parts[0];
      this.place = parts[1];
      this.date = parts[2];
      this.time = parts[3];
    }

    public static ArrayList<Utazok> readFileLines(String fileName) {
      File file = new File(fileName);
      ArrayList<Utazok> lines = new ArrayList<>();

      if (!file.exists() || !file.isFile()) {
        System.err.println("Error: File '" + fileName + "' not found or is not a regular file.");
        return lines;
      }

      try (FileInputStream fis = new FileInputStream(file);
           InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
           BufferedReader reader = new BufferedReader(isr)) {

        String line;
        while ((line = reader.readLine()) != null) {
          lines.add(new Utazok(line));
        }
      } catch (IOException e) {
        System.err.println("Error reading lines from file '" + fileName + "': " + e.getMessage());
      }
      return lines;
    }
  }
}