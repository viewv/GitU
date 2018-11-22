package io.zxnnet.model;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;

public class InitGitResposity {
    public void init(String workingDirectory) throws IOException {
        Repository repo = FileRepositoryBuilder.create(new File(workingDirectory, ".git"));
        repo.create();
    }
}
