package io.zxnnet.model;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class InitGitResposity {
    public void init(String workingDirectory) throws IllegalStateException, GitAPIException {
        File file = new File(workingDirectory);
        File initReadMe = new File(workingDirectory + File.separator +"README.md");

        try (Git git = Git.init().setDirectory(file).call()){

            String initmassage = "# " + file.getName() + "\n";
            byte[] initsource = initmassage.getBytes();
            initReadMe.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(initReadMe);
            outputStream.write(initsource);
            outputStream.close();
            git.add().addFilepattern("README.md").call();
            git.commit().setMessage("Init Commit").call();
            System.out.println("Having repository: " + git.getRepository().getDirectory());

            } catch (IOException e) {
                e.printStackTrace();
        }

    }
}
