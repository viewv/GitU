package io.zxnnet.model;

import io.zxnnet.view.Branchinfodata;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;

/**
 * @author viewv
 * @apiNote open a local git repo
 */

public class openlocalRepositorie {
    public Branchinfodata openres(String path) throws IOException {

        Branchinfodata branchinfodata = new Branchinfodata();

        Repository existingRepo = new FileRepositoryBuilder()
                .setGitDir(new File(path + File.separator + ".git"))
                .build();

        Ref head = existingRepo.exactRef("refs/heads/master");

        branchinfodata.id = head + "";
        branchinfodata.name = existingRepo.getBranch();
        return branchinfodata;
    }
}


