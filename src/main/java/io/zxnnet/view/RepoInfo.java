package io.zxnnet.view;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public class RepoInfo {
    public String reponame;
    public String id;
    public String branchname;
    public String exinfo;
    public String path;
    public RevWalk walk;
    public Repository repository;
    public RevCommit commit;
}
