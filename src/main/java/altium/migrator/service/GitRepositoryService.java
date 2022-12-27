package altium.migrator.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Slf4j
@Service
public class GitRepositoryService {

    @Value("${git.repository.url}")
    private String gitRepositoryUrl;

    @Value("${git.repository.directory.name}")
    private String gitDirectoryName;

    @Value("${migration.root.folder}")
    private String destinationPath;

    @SneakyThrows
    public void cloneRepositoryWithChangelog() {
        File destinationFolder = new File(destinationPath);
        if (!destinationFolder.exists()) {
            log.info("Git migration directory not found: {}. Creating new one.", destinationPath);
            FileUtils.forceMkdir(destinationFolder);
        }
        FileUtils.cleanDirectory(destinationFolder);

        log.info("Accessing git repository: {}", gitRepositoryUrl);
        Git.cloneRepository()
                .setURI(gitRepositoryUrl)
                .setDirectory(destinationFolder)
                .setBranch("refs/heads/main")
                .setCloneAllBranches(false)
                .setCloneSubmodules(true)
                .setNoCheckout(true)
                .call()
                .checkout()
                .setStartPoint("origin/main")
                .addPath(gitDirectoryName)
                .call();

        log.info("Git repository successfully cloned to path: {}", destinationFolder.getPath());
    }
}
