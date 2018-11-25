package io.zxnnet.model;

import io.zxnnet.view.Branchinfodata;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;

/**
 * Todo Their have a problem when you open a repo have no init commit, Their will be a wrong you must tell this info!
 */

public class openlocalRepositorie {
    public Branchinfodata openres(String path) throws IOException {

        Branchinfodata branchinfodata = new Branchinfodata();

        Repository existingRepo = new FileRepositoryBuilder()
                .setGitDir(new File(path + File.separator + ".git"))
                .build();

        Git git = Git.open(new File(path));

        Ref head = existingRepo.findRef("HEAD");

        try (RevWalk walk = new RevWalk(existingRepo)){
            if (head.getObjectId() == null){
                InitReadMe.initReadme(path,git);
                branchinfodata.exinfo = "Note! This repo is empty,System init a README.md for you!";
                existingRepo = new FileRepositoryBuilder()
                        .setGitDir(new File(path + File.separator + ".git"))
                        .build();
                head = existingRepo.findRef("HEAD");
            }
            RevCommit commit = walk.parseCommit(head.getObjectId());
            System.out.println(commit.getFullMessage());
//            branchinfodata.id = commit.getFullMessage();
            branchinfodata.name = existingRepo.getBranch();
        }

        branchinfodata.id = head + "";
        return branchinfodata;
    }
}


