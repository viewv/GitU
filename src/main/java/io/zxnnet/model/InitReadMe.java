package io.zxnnet.model;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

class InitReadMe {
    static void initReadme(File file, Git git){

        File initReadMe = new File(file.getAbsolutePath() + File.separator +"README.md");
        try {
            String initmassage = "# " + file.getName() + "\n";
            // init commit!
            byte[] initsource = initmassage.getBytes();
            initReadMe.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(initReadMe);
            outputStream.write(initsource);
            outputStream.close();
            git.add().addFilepattern(".").call();
            git.commit().setMessage("Init Commit").call();
        } catch (IOException | GitAPIException e) {
            e.printStackTrace();
        }
    }
}
