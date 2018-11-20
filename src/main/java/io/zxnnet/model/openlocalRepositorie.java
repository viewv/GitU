package io.zxnnet.model;

import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;

public class openlocalRepositorie {
    public void openres(String path) throws IOException {

        Repository existingRepo = new FileRepositoryBuilder()
                .setGitDir(new File(path + File.separator + ".git"))
                .build();

        Ref head = existingRepo.exactRef("refs/heads/master");
        System.out.println("Ref of refs/heads/master: " + head);
    }
}
