package main.RepoHandlers;

import main.Entity.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RepoEntityRepositoryCrud extends CrudRepository<Repository,Long> {

    List<Repository> findRepositoriesByRepoNameNotNull();

    List<Repository> findRepositoriesByCnaEquals(String cna);

    List<Repository> findRepositoriesByProductEquals(String product);

    Repository findRepositoriesByIdEquals(long id);



}
