package io.zxnnet.view;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.util.io.DisabledOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author rvvaidya
 * 这是一个github上的作者，这里引用其部分代码
 */

public class GitRepoMetaData {

    Repository repository;
    RevCommit commit;
    RevWalk walk;
    ArrayList<String> shortMessage;
    ArrayList<ArrayList<String>> commitHistory;
    ArrayList<String> tempCommitHistory;
    ArrayList<String> commitSHA;
    final String ADD = "ADD";
    final String MODIFY= "MODIFY";
    String diff = null;

    //There should be a better way to get this count
    int commitCount = 0;

    public GitRepoMetaData() {
        shortMessage = new ArrayList<>();
        commitHistory = new ArrayList<>();
        tempCommitHistory = new ArrayList<>();
        commitSHA = new ArrayList<>();
    }

    //TODO 可见这里需要我去初始化这三个变量
    public void setRevWalk(RevWalk walk) {
        this.walk = walk;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public void setRevCommit(RevCommit commit) {
        this.commit = commit;
    }

    //Gets the short messages to be printed on Titled Pane
    //Also populates the commitHistory container to show history in the accordion
    public ArrayList<String> getShortMessage() {
        for (RevCommit revision : walk) {
            shortMessage.add(revision.getShortMessage());

            DiffFormatter df = new DiffFormatter(DisabledOutputStream.INSTANCE);
            df.setRepository(repository);
            df.setDiffComparator(RawTextComparator.DEFAULT);
            df.setDetectRenames(true);
            RevCommit parent;
            if(revision.getParentCount()!=0) {
                try {
                    parent = walk.parseCommit(revision.getParent(0).getId());
                    RevTree tree = revision.getTree();
                    List<DiffEntry> diffs = df.scan(parent.getTree(), revision.getTree());
                    for (DiffEntry diff : diffs) {
                        String changeType = diff.getChangeType().name();
                        if(changeType.equals(ADD)|| changeType.equals(MODIFY))
                        {
                            tempCommitHistory.add(diff.getNewPath());
                        }
                    }
                }catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            commitSHA.add(commitCount,revision.name());
            commitHistory.add(commitCount++, new ArrayList<>(tempCommitHistory));
            tempCommitHistory.clear();
        }
        walk.reset();
        return shortMessage;
    }

    private static AbstractTreeIterator prepareTreeParser(Repository repository, String objectId) throws IOException {
        RevWalk walk = new RevWalk(repository) ;
        RevCommit commit = walk.parseCommit(ObjectId.fromString(objectId));
        RevTree tree = walk.parseTree(commit.getTree().getId());
        CanonicalTreeParser oldTreeParser = new CanonicalTreeParser();
        ObjectReader oldReader = repository.newObjectReader();
        oldTreeParser.reset(oldReader, tree.getId());
        walk.dispose();
        return oldTreeParser;
    }

    //Given a commit index this API returns the diff between a commit and its parent.
    //Assuming only 1 parent.
    public String getDiffBetweenCommits(int commitIndex) throws IOException,GitAPIException{
        if(commitIndex+1==commitCount)
            return "Nothing to Diff. This is first commit";
        AbstractTreeIterator current = prepareTreeParser(repository,commitSHA.get(commitIndex));
        AbstractTreeIterator parent = prepareTreeParser(repository,commitSHA.get(++commitIndex));
        ObjectReader reader = repository.newObjectReader();
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        // finally get the list of changed files
        Git git = new Git(repository) ;
        List<DiffEntry> diff = git.diff().
                setOldTree(parent).
                setNewTree(current).call();
                //TODO Set the path filter to filter out the selected file
                //setPathFilter(PathFilter.create("README.md")).
        for (DiffEntry entry : diff) {
            System.out.println("Entry: " + entry + ", from: " + entry.getOldId() + ", to: " + entry.getNewId());
            DiffFormatter formatter = new DiffFormatter(byteStream) ;
            formatter.setRepository(repository);
            formatter.format(entry);
        }
        // System.out.println(byteStream.toString());
        String diffContent = byteStream.toString();
        return byteStream.toString();
    }

    private ArrayList<String> unCommit = new ArrayList<>();

    public ArrayList<String> getUncommit() throws GitAPIException {
        try (Git git = new Git(repository)){
            Status status = git.status().call();
            Set<String> uncommittedChanges = status.getUncommittedChanges();
            unCommit.addAll(uncommittedChanges);
            return unCommit;
        }
    }

    public ArrayList<ArrayList<String>> getCommitFiles() {
        return commitHistory;
    }

//    public ArrayList<String> getCommitRef(){
//        return commitSHA;
//    }

    public String getRepoName() {
        String repoPath = repository.getDirectory().getParent();
        int index = repoPath.lastIndexOf("/");
        return repoPath.substring(index + 1);
    }

//    //Gets commit count for this repository
//    public int getCommitCount() {
//        return commitCount;
//    }
}
