package io.zxnnet.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListView;
import io.zxnnet.model.InitGitResposity;
import io.zxnnet.model.openlocalRepositorie;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;


public class MainController {

    @FXML public Button Import;
    @FXML public Button Init;
    @FXML public Button Clone;
    @FXML public Button Push;
    @FXML public Label branchId;
    @FXML public Label branchName;
    @FXML public JFXListView<Label> listview;
    @FXML Button exitButton;
    @FXML Button minButton;
    @FXML Button maxButton;
    @FXML BorderPane BasePane;
    @FXML public StackPane stackPane;

    //@FXML private JFXPopup popup;

    private Tooltip tooltip = new Tooltip();
    private openlocalRepositorie localRes = new openlocalRepositorie();
    private InitGitResposity initRes = new InitGitResposity();

    @FXML
    public void initProject() throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("init Local Git Repositor");
        File file = directoryChooser.showDialog(new Stage());
        if (file != null){
            System.out.println(file.getAbsolutePath());
            initRes.init(file.getAbsolutePath());
        }
    }

    class Delta{
        double x,y;
    }

    private final Delta dragDelta = new Delta();

    @FXML
    public void openProject() throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Open Local Git Repositor");
        File file = directoryChooser.showDialog(new Stage());
        if (file != null){
            //Add a temp to judge wether the dir have .git file
            File tempjument = new File(file.getAbsolutePath() + File.separator + ".git");

//            System.out.println(tempjument.getAbsolutePath());
//            System.out.println(tempjument.exists());
//            System.out.println(tempjument.isDirectory());

            if (tempjument.exists() && tempjument.isDirectory()){
//                System.out.println(file.getAbsolutePath());
                branchName.setText(localRes.openres(file.getAbsolutePath()).name);
                branchId.setText(localRes.openres(file.getAbsolutePath()).id);
                Label templabel = new Label(file.getName() + "\n" + branchName.getText());
                /**
                 * @TODO need to resize the size of the image in icon file!
                 */
//                templabel.setGraphic(new ImageView(new Image(
//                        Objects.requireNonNull(
//                                getClass().getClassLoader().getResource(
//                                        "Icon/git2.png")).toExternalForm())
//                ));
                ImageView tempimag = new ImageView(new Image(Objects.requireNonNull(
                        getClass().getClassLoader().getResource(
                                "Icon/git2.png")).toExternalForm()));

                tempimag.setFitHeight(30);
                tempimag.setFitWidth(30);
                templabel.setGraphic(tempimag);

                templabel.setStyle(Objects.requireNonNull(
                        getClass().getClassLoader().getResource("Style/list.css")).toExternalForm());

                listview.getItems().add(templabel);
            }
            else {
//                System.out.println("Now init a dialog");
                // alert user !
                JFXDialogLayout content = new JFXDialogLayout();
                content.setHeading(new Text("Wrong!"));
                content.setBody(new Text("This Floder does not init with git\n" +
                        "Please init with Git and try again!"));
                JFXDialog dialog = new JFXDialog(stackPane,content,JFXDialog.DialogTransition.CENTER);
                JFXButton button = new JFXButton("Close");
                button.setOnMouseClicked(event -> dialog.close());
                content.setActions(button);
                dialog.show();
            }
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
