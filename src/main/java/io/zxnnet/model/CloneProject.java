package io.zxnnet.model;

import javafx.scene.control.TextInputDialog;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.eclipse.jgit.api.Git;

import java.io.File;
import java.util.Optional;

public class CloneProject {

    public boolean Clone() throws Exception {

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Set Clone Location");
        // use user.name to cross platform
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = directoryChooser.showDialog(new Stage());

        if (file != null && file.exists()){

            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Input Repo UrL");
            dialog.setHeaderText("Input Your ");
            dialog.setContentText("Please enter your Repo Location:");
            Optional<String> result = dialog.showAndWait();
            final String[] URLocation = new String[1];

            result.ifPresent(name -> {
                URLocation[0] = name;
                System.out.println("UrL: " + name);
            });

            //这里是设置文件名，这样不会导致克隆一堆文件到用户的目录中
            String url = URLocation[0];
            int index = url.lastIndexOf("/");
            int dotindex = url.lastIndexOf(".");
            char[] ch = url.toCharArray();
            String flodername = String.copyValueOf(ch, index + 1, dotindex - index - 1);
            String dirpath = file.getAbsolutePath() + File.separator + flodername;
            File dir =new File(dirpath);

            System.out.println(URLocation[0]);

            if (dir.exists() && dir.isDirectory()){
                return false;
            } else{
                try (Git answer = Git.cloneRepository()
                        .setURI(URLocation[0])
                        .setDirectory(dir)
                        .call()){
                    answer.close();
                    System.out.println("Having repo: " + answer.getRepository().getDirectory());
                    return true;
                }
            }
        }else {
            return false;
        }
    }
}
