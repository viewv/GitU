package io.zxnnet.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

public class WindowController {

    @FXML Button exitButton;
    @FXML Button minButton;
    @FXML Button maxButton;

    private Tooltip tooltip = new Tooltip();

    @FXML
    public void exitalert(){
        tooltip.setText("Exit GitU");
        exitButton.setTooltip(tooltip);
    }

    @FXML
    public void  minalert(){
        tooltip.setText("Minimized Window");
        minButton.setTooltip(tooltip);
    }

    @FXML
    public void  maxalert(){
        tooltip.setText("Max Window");
        maxButton.setTooltip(tooltip);
    }

    @FXML
    public void exitapp(){
        Platform.exit();
    }

    @FXML
    public void minapp(){
        Stage stage = (Stage)minButton.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void maxapp(){
        Stage stage = (Stage)maxButton.getScene().getWindow();
        stage.setMaximized(!stage.isMaximized());
    }
}
