package io.zxnnet.model;

import io.zxnnet.view.GitRepoMetaData;
import io.zxnnet.view.RepoInfo;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;

public class SetRepoInfo {
    public static RepoInfo set(File file) throws IOException {
        RepoInfo repo = new RepoInfo();
        String path = file.getAbsolutePath();

        Repository existingRepo = new FileRepositoryBuilder()
                .setGitDir(new File(path + File.separator + ".git"))
                .build();

        GitRepoMetaData metaData = new GitRepoMetaData();

        Ref head = existingRepo.findRef("HEAD");
        RevWalk walk = new RevWalk(existingRepo);
        AnyObjectId id = existingRepo.resolve("HEAD");
        RevCommit commit =walk.parseCommit(id);
        walk.markStart(commit);

        metaData.setRevWalk(walk);
        metaData.setRevCommit(commit);
        metaData.setRepository(existingRepo);

        repo.reponame = metaData.getRepoName();
        repo.walk = walk;
        repo.path = path;
        repo.commit = commit;
        repo.repository = existingRepo;
        repo.id = head + "";
        repo.branchname = existingRepo.getBranch();

        return repo;
    }
}
