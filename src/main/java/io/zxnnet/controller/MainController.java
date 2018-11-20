package io.zxnnet.controller;

import io.zxnnet.model.openlocalRepositorie;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class MainController {

    @FXML public Button Import;
    @FXML public Button Init;
    @FXML public Button Clone;
    @FXML public Button Push;
    @FXML Button exitButton;
    @FXML Button minButton;
    @FXML Button maxButton;
    @FXML BorderPane BasePane;

    class Delta{
        double x,y;
    }

    private final Delta dragDelta = new Delta();

    private Tooltip tooltip = new Tooltip();
    private openlocalRepositorie localRes = new openlocalRepositorie();


    @FXML
    public void openProject() throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Open Local Git Repositor");
        File file = directoryChooser.showDialog(new Stage());
        if (file != null){
           System.out.println(file.getAbsolutePath());
           localRes.openres(file.getAbsolutePath());
        }
    }

    @FXML
    public void getlocation(MouseEvent mouseEvent){
        dragDelta.x = mouseEvent.getSceneX();
        dragDelta.y = mouseEvent.getScreenY();
    }

    @FXML
    public void movwindow(MouseEvent mouseEvent){
        Stage stage = (Stage)BasePane.getScene().getWindow();
        stage.setX(mouseEvent.getScreenX() - dragDelta.x);
        stage.setY(mouseEvent.getScreenY() - dragDelta.y);
    }

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
        stage.setFullScreenExitHint("esc to quit max windows");
        stage.setFullScreen(!stage.isFullScreen());
    }
}
