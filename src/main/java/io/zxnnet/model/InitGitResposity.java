package io.zxnnet.model;

import io.zxnnet.view.RepoInfo;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;

//这里不应该是空返回函数，应该返回对应的路径
public class InitGitResposity {
    public static RepoInfo init(File file) throws IllegalStateException, GitAPIException, IOException {

        try (Git git = Git.init().setDirectory(file).call()){
            InitReadMe.initReadme(file,git);
            System.out.println("Having repository: " + git.getRepository().getDirectory());

            RepoInfo repoInfo = SetRepoInfo.set(git.getRepository().getDirectory());
            return repoInfo;
        }
    }
}
