package io.zxnnet.controller;

import com.jfoenix.controls.*;
import io.zxnnet.model.CloneProject;
import io.zxnnet.model.InitGitResposity;
import io.zxnnet.model.openlocalRepositorie;
import io.zxnnet.view.GitRepoMetaData;
import io.zxnnet.view.RepoInfo;
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
import java.util.ArrayList;
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
    @FXML public JFXListView<Label> commitview;
    @FXML public Button Delete;
    @FXML public Button exitButton;
    @FXML public Button minButton;
    @FXML public Button maxButton;
    @FXML public BorderPane BasePane;
    @FXML public StackPane stackPane;

    private Tooltip tooltip = new Tooltip();
    private openlocalRepositorie localRes = new openlocalRepositorie();
    private InitGitResposity initRes = new InitGitResposity();

    @Override
    public void initialize(URL url, ResourceBundle rb){
        try {
//            JFXPopup jfxPopup = new JFXPopup();
            listview.setExpanded(true);
            listview.depthProperty().set(2);
            commitview.setExpanded(true);
            commitview.depthProperty().set(2);
            //TODO add a way to storage the location
            //System.out.println(Objects.requireNonNull(MainController.class.getClassLoader().getResource("Style/light.css")).getPath());

        }catch (Exception ex){
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    //设置底边栏的快速浏览
    private void setInfoBar(String branchname,String id){
        branchName.setText(branchname);
        branchId.setText(id);
    }

    //TODO 这里新建完一个库之后要导入listview中
    @FXML
    public void initProject() throws GitAPIException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("init Local Git Repository");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = directoryChooser.showDialog(new Stage());
        if (file != null){
            System.out.println(file.getAbsolutePath());
            initRes.init(file.getAbsolutePath());

            JFXSnackbar snackbar = new JFXSnackbar(stackPane);
            snackbar.show("Successful!\nSuccessfully init a Repository!","Close",5000,
                    event -> snackbar.unregisterSnackbarContainer(stackPane));
        }
    }


    //TODO listview 应该修改出去，不要再这里直接调用
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
                RepoInfo data = localRes.openres(file.getAbsolutePath());
                setInfoBar(data.name,data.id);

//                GitRepoMetaData Repo = new GitRepoMetaData();

//                Repo.setRepository(data.repository);
//                Repo.setRevCommit(data.commit);
//                Repo.setRevWalk(data.walk);
//
//                ArrayList<String> message = Repo.getShortMessage();
//
//                for (String x:message) {
//                    System.out.println(x);
//                }


                Label templabel = new Label(file.getName() + "\n" + branchName.getText());

                ImageView tempimag = new ImageView(new Image(Objects.requireNonNull(
                        getClass().getClassLoader().getResource(
                                "Icon/git2.png")).toExternalForm()));

                tempimag.setFitHeight(30);
                tempimag.setFitWidth(30);
                templabel.setGraphic(tempimag);

                listview.getItems().add(templabel);

                if (data.exinfo != null){

                    JFXSnackbar snackbar = new JFXSnackbar(stackPane);
                    snackbar.show("Note!\n" + data.exinfo,"Close",5000,
                            event -> snackbar.unregisterSnackbarContainer(stackPane));
                }
            }
            else {

                JFXSnackbar snackbar = new JFXSnackbar(stackPane);
                snackbar.show("Wrong!\nThis Folder does not init with git\n" +
                                "Please init with Git and try again!","Close",5000,
                        event -> snackbar.unregisterSnackbarContainer(stackPane));
            }
        }
    }

    @FXML
    //TODO 这里克隆完之后要调用open来导入
    public void cloneProject() throws Exception {

        CloneProject cloneProject = new CloneProject();

        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Working..."));
        content.setBody(new Text("Cloning, Please Don't Close The window!"));
        JFXDialog jfxDialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
        jfxDialog.show();

        boolean finised = cloneProject.Clone();

        if (finised){
            jfxDialog.close();
            JFXSnackbar snackbar = new JFXSnackbar(stackPane);
            snackbar.show("Successful!\nSuccessfully Clone a Repository!","Close",5000,
                        event -> snackbar.unregisterSnackbarContainer(stackPane));
        }
        else {
            jfxDialog.close();
            JFXSnackbar snackbar = new JFXSnackbar(stackPane);
            snackbar.show("Fail!\nCheck your information and Try again!","Close",5000,
                        event -> snackbar.unregisterSnackbarContainer(stackPane));
        }
    }


    //删除选中的条目
    @FXML
    public void deleteProject() {
        final int seleteIdx = listview.getSelectionModel().getSelectedIndex();
        if (seleteIdx != -1){
            Label itemToRemove = listview.getSelectionModel().getSelectedItem();

            final int newSelectedIdx =
                    (seleteIdx == listview.getItems().size()-1)
                            ? seleteIdx - 1
                            : seleteIdx;
            listview.getItems().remove(seleteIdx);
            listview.getSelectionModel().select(newSelectedIdx);
            System.out.println("selectIdx: " + seleteIdx);
            System.out.println("item: " + itemToRemove.getText());
        }
    }


    private void ShowAlertDialog(JFXDialogLayout content) {
        JFXDialog dialog = new JFXDialog(stackPane,content,JFXDialog.DialogTransition.CENTER);
        JFXButton button = new JFXButton("Close");
        button.setOnMouseClicked(event -> dialog.close());
        content.setActions(button);
        dialog.show();
    }






    //下面的代码是用来解决Windows的问题

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
