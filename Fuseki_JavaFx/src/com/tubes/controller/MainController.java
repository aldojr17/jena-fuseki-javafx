package com.tubes.controller;

import com.tubes.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private ComboBox<String> comboModel;
    private ObservableList<String> model;
    private ObservableList<String> statement;
    @FXML
    private TextField txtSubject, txtProperty, txtObject;
    @FXML
    private TextField prefix1;
    @FXML
    private VBox prefixBox;
    private static int pos;
    private ObservableList<TextField> prx;
    @FXML
    private ComboBox<String> comboStatement;

    @FXML
    private void searchAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/output.fxml"));
        Parent root = loader.load();
        OutputController outputController = loader.getController();
        outputController.setStatement(prx,txtSubject.getText().trim(),txtProperty.getText().trim(),
                txtObject.getText().trim(), comboModel.getSelectionModel().getSelectedItem(),
                comboStatement.getSelectionModel().getSelectedItem());
        txtSubject.clear();
        txtProperty.clear();
        txtObject.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        prx = FXCollections.observableArrayList();
        prx.add(prefix1);
        pos = 2;
        statement = FXCollections.observableArrayList(
                "add",
                "delete data",
                "delete where"
        );
        model = FXCollections.observableArrayList(
                "employment",
                "jobs",
                "people",
                "praktikum"
        );
        comboModel.setItems(model);
        comboModel.getSelectionModel().selectFirst();
        comboStatement.setItems(statement);
        comboStatement.getSelectionModel().selectFirst();
    }

    @FXML
    private void addPrefix(ActionEvent actionEvent) {
        TextField prefix = new TextField();
        prefix.setId("prefix"+pos);
        pos++;
        prx.add(prefix);
        prefixBox.getChildren().add(prefix);
    }

    @FXML
    private void checkAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/output.fxml"));
        Parent root = loader.load();
        OutputController outputController = loader.getController();
        outputController.setText(comboModel.getSelectionModel().getSelectedItem());

        Stage outputStage = new Stage();
        outputStage.setTitle("Output");
        outputStage.setScene(new Scene(root));
        outputStage.initModality(Modality.WINDOW_MODAL);
        outputStage.show();
    }
}
