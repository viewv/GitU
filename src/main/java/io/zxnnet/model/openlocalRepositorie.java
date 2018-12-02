package io.zxnnet.model;

import io.zxnnet.view.RepoInfo;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;

public class openlocalRepositorie {
    public static RepoInfo openres(File file) throws IOException {

        String path = file.getAbsolutePath();
        File tempjument = new File(file.getAbsolutePath() + File.separator + ".git");
        if (!tempjument.exists() || !tempjument.isDirectory()){
            return null;
        }

        RepoInfo repoInfo = new RepoInfo();
        Repository existingRepo = new FileRepositoryBuilder()
                .setGitDir(new File(path + File.separator + ".git"))
                .build();

        Git git = Git.open(new File(path));
        Ref head = existingRepo.findRef("HEAD");
        if (head.getObjectId() == null){
            InitReadMe.initReadme(new File(path),git);
            repoInfo.exinfo = "Note! This repo is empty,System init a README.md for you!";
        }

        repoInfo = SetRepoInfo.set(file);

        return repoInfo;
    }
}


