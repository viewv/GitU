package io.zxnnet.controller;

import com.jfoenix.controls.*;
import io.zxnnet.model.*;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
    @FXML public JFXListView<String> commitview;
    @FXML public JFXListView<String> historyfiles;
    @FXML public Button Delete;
    @FXML public Button exitButton;
    @FXML public Button minButton;
    @FXML public Button maxButton;
    @FXML public BorderPane BasePane;
    @FXML public StackPane stackPane;


    private ArrayList<File> allRepoLocation = new ArrayList<>();
    private ArrayList<String> shortMessage;
    private ArrayList<ArrayList<String>> commitHistory;
    private ArrayList<String> currfiles ;

    private Tooltip tooltip = new Tooltip();

    @Override
    public void initialize(URL url, ResourceBundle rb){
        try {
//            JFXPopup jfxPopup = new JFXPopup();
            Font.loadFont(Objects.requireNonNull(getClass().getClassLoader().
                    getResource("Data/DejaVuSansMono-Bold.ttf")).toExternalForm(), 12);

            listview.setExpanded(true);
            listview.depthProperty().set(2);
            commitview.setExpanded(true);
            commitview.depthProperty().set(2);
//            currentfiles.setExpanded(true);
//            currentfiles.depthProperty().set(2);
            historyfiles.setExpanded(true);
            historyfiles.depthProperty().set(2);

            File chechfile = new File(System.getProperty("user.home") +
                    File.separator + "temp" + File.separator +  "RepoData.ssr");

            if (chechfile.exists()){
                System.out.println("Rebuild");

                StorgeData storgeData = new StorgeData("RepoData");

                allRepoLocation = storgeData.rebuild();

                for (File x : allRepoLocation){
                    RepoInfo data = openlocalRepositorie.openres(x);
                    assert data != null;
                    setScreen(data);
                }
            }

        }catch (Exception ex){
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    //TODO 这个函数用来刷新界面来显示文件和底边栏信息，刷新当前库信息
    private void setScreen(RepoInfo data){

        Label label = new Label(data.reponame + "\n" + data.branchname);
        ImageView imag = new ImageView(new Image(Objects.requireNonNull(
                getClass().getClassLoader().getResource(
                        "Icon/git2.png")).toExternalForm()));

        imag.setFitHeight(30);
        imag.setFitWidth(30);
        label.setGraphic(imag);

        listview.getItems().add(label);
    }



    //TODO 这里新建完一个库之后要导入listview中
    @FXML
    public void initProject() throws GitAPIException, IOException {

        File file = DirOpener.open();

        if (file != null){

            RepoInfo data =  InitGitResposity.init(file);
            setScreen(data);
            JFXSnackbar snackbar = new JFXSnackbar(stackPane);
            snackbar.show("Successful!\nSuccessfully init a Repository!","Close",5000,
                    event -> snackbar.unregisterSnackbarContainer(stackPane));
        }

        allRepoLocation.add(file);
    }

    @FXML
    public void openProject() throws IOException {

        File file = DirOpener.open();
        RepoInfo data = openlocalRepositorie.openres(file);

        if (data != null) {
            setScreen(data);

            if (data.exinfo != null) {
                JFXSnackbar snackbar = new JFXSnackbar(stackPane);
                snackbar.show("Note!\n" + data.exinfo, "Close", 5000,
                        event -> snackbar.unregisterSnackbarContainer(stackPane));
            }
        } else {
            JFXSnackbar snackbar = new JFXSnackbar(stackPane);
            snackbar.show("Wrong!\nThis Folder does not init with git\n" +
                            "Please init with Git and try again!", "Close", 5000,
                    event -> snackbar.unregisterSnackbarContainer(stackPane));
        }
        allRepoLocation.add(file);
    }

    //TODO 这里克隆完之后要调用open来导入
    @FXML
    public void cloneProject() throws Exception {

        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Working..."));
        content.setBody(new Text("Cloning, Please Don't Close The window!"));
        JFXDialog jfxDialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
        jfxDialog.show();

        File file = DirOpener.open();
        RepoInfo data = CloneProject.Clone(file);

        if (data != null){
            jfxDialog.close();
            setScreen(data);
            JFXSnackbar snackbar = new JFXSnackbar(stackPane);
            snackbar.show("Successful!\nSuccessfully Clone a Repository!","Close",5000,
                        event -> snackbar.unregisterSnackbarContainer(stackPane));
            setScreen(data);
        }
        else {
            jfxDialog.close();
            JFXSnackbar snackbar = new JFXSnackbar(stackPane);
            snackbar.show("Fail!\nCheck your information and Try again!","Close",5000,
                        event -> snackbar.unregisterSnackbarContainer(stackPane));
        }

        allRepoLocation.add(file);
    }

    //TODO 删除选中的条目
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

            allRepoLocation.remove(seleteIdx);
            commitview.getItems().clear();
            historyfiles.getItems().clear();
//            currentfiles.getItems().clear();
            shortMessage.clear();
            commitHistory.clear();
            currfiles.clear();
        }
    }

    @FXML
    public void ChangeRepo() throws IOException, GitAPIException {

        int seleteIdx = listview.getSelectionModel().getSelectedIndex();

        if (seleteIdx != -1){
            File file = allRepoLocation.get(seleteIdx);
            RepoInfo data = openlocalRepositorie.openres(file);
            assert data != null;
            GitRepoMetaData metaData = new GitRepoMetaData();
            metaData.setRepository(data.repository);
            metaData.setRevCommit(data.commit);
            metaData.setRevWalk(data.walk);

            shortMessage = metaData.getShortMessage();
            commitHistory = metaData.getCommitFiles();
            currfiles = metaData.getUncommit();

            branchName.setText(data.branchname);
            branchId.setText(data.id);

//            setCurrentfiles(currfiles);
            setCommitview(shortMessage);
            historyfiles.getItems().clear();
        }
    }

    private void setCommitview(ArrayList<String> Message){

        commitview.getItems().clear();
        for (String x: Message) {
            commitview.getItems().add(x);
        }
    }

//    private void setCurrentfiles(ArrayList<String> curr){
//        historyfiles.getItems().clear();
//        for (String x: curr){
//            historyfiles.getItems().add(x);
//        }
//    }

    private void setFileview(int idx) {

        historyfiles.getItems().clear();
        for (String x :commitHistory.get(idx)) {
            historyfiles.getItems().add(x);
        }
    }

    @FXML
    public void showFile() {
        int seleteIdx = commitview.getSelectionModel().getSelectedIndex();
        if (seleteIdx != -1){
            setFileview(seleteIdx);
        }
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
        StorgeData storgeData = new StorgeData("RepoData");
        System.out.println("Storing!");
        storgeData.store(allRepoLocation);
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
