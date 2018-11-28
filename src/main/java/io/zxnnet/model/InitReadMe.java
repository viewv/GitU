package io.zxnnet.model;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class InitReadMe {
    public static void initReadme(String path, Git git){
        File file = new File(path);
        File initReadMe = new File(path + File.separator +"README.md");
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
        } catch (FileNotFoundException | NoMessageException | NoFilepatternException | WrongRepositoryStateException | UnmergedPathsException | NoHeadException | ConcurrentRefUpdateException | AbortedByHookException e) {
            e.printStackTrace();
        } catch (IOException | GitAPIException e) {
            e.printStackTrace();
        }
    }
}
