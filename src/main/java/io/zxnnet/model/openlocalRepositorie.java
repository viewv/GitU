package io.zxnnet.model;

import io.zxnnet.view.Branchinfodata;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;

/**
 * Todo Their have a problem when you open a repo have no init commit, Their will be a wrong you must tell this info
 */

public class openlocalRepositorie {
    public Branchinfodata openres(String path) throws IOException {

        Branchinfodata branchinfodata = new Branchinfodata();

        Repository existingRepo = new FileRepositoryBuilder()
                .setGitDir(new File(path + File.separator + ".git"))
                .build();

        Ref head = existingRepo.findRef("HEAD");

        try (RevWalk walk = new RevWalk(existingRepo)){
            RevCommit commit = walk.parseCommit(head.getObjectId());
            System.out.println(commit.getFullMessage());
//            branchinfodata.id = commit.getFullMessage();
            branchinfodata.name = existingRepo.getBranch();
        }

         branchinfodata.id = head + "";
        return branchinfodata;
    }
}


