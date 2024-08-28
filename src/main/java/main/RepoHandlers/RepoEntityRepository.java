package main.RepoHandlers;

import main.Entity.Repository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Component
public interface RepoEntityRepository {

    Collection<Repository> getRepositories();

    Repository getRepository(long id);

    @Transactional
    long insertRepository(Repository repository);

    @Transactional
    void deleteRepositories();
}
