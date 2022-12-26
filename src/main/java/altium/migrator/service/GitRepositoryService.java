package altium.migrator.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;

@Slf4j
@Service
public class GitRepositoryService {

    @Value("${git.repository.url}")
    private String gitRepositoryUrl;

    @Value("${git.repository.directory.name}")
    private String gitDirectoryName;

    @Value("${migration.root.folder}")
    private String migrationRootFolder;

    @SneakyThrows
    public void cloneRepositoryWithChangelog() {
        log.info("Accessing git repository: {}", gitRepositoryUrl);

        File destinationFolder = ResourceUtils.getFile("classpath:" + migrationRootFolder);
        FileUtils.cleanDirectory(destinationFolder);
        Git gitRepository = Git.cloneRepository()
                .setURI(gitRepositoryUrl)
                .setDirectory(destinationFolder)
                .setBranch("refs/heads/main")
                .setCloneAllBranches(false)
                .setCloneSubmodules(true)
                .setNoCheckout(true)
                .call();
        
        gitRepository.checkout()
                .setStartPoint("origin/main")
                .addPath(gitDirectoryName)
                .call();

        log.info("Git repository successfully cloned to path: {}", destinationFolder.getPath());
    }
}
