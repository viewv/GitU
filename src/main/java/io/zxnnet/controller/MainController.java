package io.zxnnet.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;

public class MainController {

    @FXML
    public void handlerBtnClick(ActionEvent event) {
        Button btnSource = (Button) event.getSource();
        btnSource.setText("I am clicked!");
    }
}
