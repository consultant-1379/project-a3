package main.RepoHandlers;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Collection;


@Repository
public class RepoEntityEntityRepositoryImpl implements RepoEntityRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public Collection<main.Entity.Repository> getRepositories() {
        String jpql = "select repo from repository repo";
        TypedQuery<main.Entity.Repository> query = entityManager.createQuery(jpql, main.Entity.Repository.class);
        return query.getResultList();
    }

    @Override
    public main.Entity.Repository getRepository(long id) {
        return null;
    }

    @Override
    public long insertRepository(main.Entity.Repository repository) {
        entityManager.persist(repository);
        entityManager.flush();
        return repository.getId();
    }

    @Override
    public void deleteRepositories() {
        Query query = entityManager.createQuery("delete from repository ");
        query.executeUpdate();
    }

}
