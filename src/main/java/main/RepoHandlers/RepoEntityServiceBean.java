package main.RepoHandlers;

import main.Entity.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Lazy
public class RepoEntityServiceBean {

    private RepoEntityEntityRepositoryImpl repository;

    private RepoEntityRepositoryCrud repositoryCrud;

    @Autowired
    public RepoEntityServiceBean(RepoEntityEntityRepositoryImpl repoEntityEntityRepository,RepoEntityRepositoryCrud repositoryCrud){
        this.repository = repoEntityEntityRepository;
        this.repositoryCrud = repositoryCrud;
    }

    public long addRepositoryToDatabase(Repository repositoryEntity){
        return repository.insertRepository(repositoryEntity);
    }

    public List<Repository> getAllRepositories(){
        return repositoryCrud.findRepositoriesByRepoNameNotNull();
    };

    public Repository findRepositoriesByIdEquals(long id){
        return repositoryCrud.findRepositoriesByIdEquals(id);
    };

    public void deleteAllRepositories(){
        repository.deleteRepositories();
    }

    public List<Repository> getRepositoriesByCNA(String cna){
        return repositoryCrud.findRepositoriesByCnaEquals(cna);
    };

    public List<Repository> getRepositoriesByProduct(String product){
        return repositoryCrud.findRepositoriesByProductEquals(product);
    };
}
