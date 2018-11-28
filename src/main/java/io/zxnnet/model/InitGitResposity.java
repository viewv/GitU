package io.zxnnet.model;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;

//这里不应该是空返回函数，应该返回对应的路径
public class InitGitResposity {
    public void init(String workingDirectory) throws IllegalStateException, GitAPIException {
        File file = new File(workingDirectory);

        try (Git git = Git.init().setDirectory(file).call()){
            InitReadMe.initReadme(workingDirectory,git);
            System.out.println("Having repository: " + git.getRepository().getDirectory());
        }
    }
}
