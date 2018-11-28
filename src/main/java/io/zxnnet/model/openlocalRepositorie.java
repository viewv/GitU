package io.zxnnet.model;

import io.zxnnet.view.GitRepoMetaData;
import io.zxnnet.view.RepoInfo;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;

public class openlocalRepositorie {
    public RepoInfo openres(String path) throws IOException {

        RepoInfo repoInfo = new RepoInfo();

        Repository existingRepo = new FileRepositoryBuilder()
                .setGitDir(new File(path + File.separator + ".git"))
                .build();

        Git git = Git.open(new File(path));

        Ref head = existingRepo.findRef("HEAD");

        if (head.getObjectId() == null){
            InitReadMe.initReadme(path,git);
            repoInfo.exinfo = "Note! This repo is empty,System init a README.md for you!";
            existingRepo = new FileRepositoryBuilder()
                    .setGitDir(new File(path + File.separator + ".git"))
                    .build();
            head = existingRepo.findRef("HEAD");
        }

        /**
         * @apiNote following code can get git file
         * and commit message, it is very useful
         */
        GitRepoMetaData metaData = new GitRepoMetaData();

        RevWalk walk = new RevWalk(existingRepo);

        AnyObjectId id = existingRepo.resolve("HEAD");

        System.out.println(id);

        RevCommit commit =walk.parseCommit(id);
        //Important!
        walk.markStart(commit);

        metaData.setRevWalk(walk);
        metaData.setRevCommit(commit);
        metaData.setRepository(existingRepo);

        for (String x : metaData.getShortMessage()) {
            System.out.println(x);
        }

        //return a repo repo storage all info you need
        repoInfo.path = path;
        repoInfo.commit = commit;
        repoInfo.repository = existingRepo;
        repoInfo.id = head + "";
        repoInfo.name = existingRepo.getBranch();

        return repoInfo;
    }
}


