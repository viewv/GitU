package io.zxnnet.controller;

import com.jfoenix.controls.*;
import io.zxnnet.model.InitGitResposity;
import io.zxnnet.model.openlocalRepositorie;
import io.zxnnet.view.Branchinfodata;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainController implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle rb){
        try {
//            JFXPopup jfxPopup = new JFXPopup();
            listview.setExpanded(true);
            listview.depthProperty().set(2);
        }catch (Exception ex){
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    @FXML
    public void initProject() throws GitAPIException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("init Local Git Repository");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = directoryChooser.showDialog(new Stage());
        if (file != null){
            System.out.println(file.getAbsolutePath());
            initRes.init(file.getAbsolutePath());

            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text("Successful"));
            content.setBody(new Text("Successfully init a Repository!"));
            ShowAlertDialog(content);

        }
    }

    private void ShowAlertDialog(JFXDialogLayout content) {
        JFXDialog dialog = new JFXDialog(stackPane,content,JFXDialog.DialogTransition.CENTER);
        JFXButton button = new JFXButton("Close");
        button.setOnMouseClicked(event -> dialog.close());
        content.setActions(button);
        dialog.show();
    }

    @FXML
    public void openProject() throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Open Local Git Repository");
        // use user.name to cross platform
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = directoryChooser.showDialog(new Stage());
        if (file != null){

            File tempjument = new File(file.getAbsolutePath() + File.separator + ".git");

            if (tempjument.exists() && tempjument.isDirectory()){

                Branchinfodata data = localRes.openres(file.getAbsolutePath());

                branchName.setText(data.name);
                branchId.setText(data.id);
                Label templabel = new Label(file.getName() + "\n" + branchName.getText());

                ImageView tempimag = new ImageView(new Image(Objects.requireNonNull(
                        getClass().getClassLoader().getResource(
                                "Icon/git2.png")).toExternalForm()));

                // set the image size to fit into the label
                tempimag.setFitHeight(30);
                tempimag.setFitWidth(30);
                templabel.setGraphic(tempimag);

                listview.getItems().add(templabel);

                if (data.exinfo != null){
                    JFXDialogLayout content = new JFXDialogLayout();
                    content.setHeading(new Text("Note!"));
                    content.setBody(new Text(data.exinfo));
                    ShowAlertDialog(content);
                }
            }
            else {

                JFXDialogLayout content = new JFXDialogLayout();
                content.setHeading(new Text("Wrong!"));
                content.setBody(new Text("This Folder does not init with git\n" +
                        "Please init with Git and try again!"));
                ShowAlertDialog(content);
            }
        }
    }


    class Delta{
        double x,y;
    }

    private final Delta dragDelta = new Delta();

    @FXML
    public void getlocation(MouseEvent mouseEvent){
        dragDelta.x = mouseEvent.getSceneX();
        dragDelta.y = mouseEvent.getScreenY();
    }

    @FXML
    public void movwindow(MouseEvent mouseEvent){
        mouseEvent.consume();
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
