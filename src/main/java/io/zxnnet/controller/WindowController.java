package io.zxnnet.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class WindowController {

    @FXML Button exitButton;
    @FXML Button minButton;
    @FXML Button maxButton;

    class Delta{
        double x,y;
    }

    final Delta dragDelta = new Delta();

    private Tooltip tooltip = new Tooltip();

    @FXML
    public void getlocation(MouseEvent mouseEvent){
        Stage stage = (Stage)minButton.getScene().getWindow();
        dragDelta.x = stage.getX() - mouseEvent.getSceneX();
        dragDelta.y = stage.getY() - mouseEvent.getScreenY();
    }

    @FXML
    public void movwindow(MouseEvent mouseEvent){
        Stage stage = (Stage)minButton.getScene().getWindow();
        stage.setX(mouseEvent.getScreenX() + dragDelta.x);
        stage.setY(mouseEvent.getScreenY() + dragDelta.y);
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
