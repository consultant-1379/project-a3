package main.RepoHandlers;

import main.Entity.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/repositories")
@CrossOrigin
public class RepositoryRestController {
    private static final Logger logger = LoggerFactory.getLogger(RepositoryRestController.class);

    @Autowired
    private RepoEntityServiceBean repoEntityServiceBean;

    @GetMapping(produces={"application/json","application/xml"})
    public ResponseEntity<Collection<Repository>> getAllRepositories() {
        logger.info("Executing Get on repositories");
        Collection<Repository> result = repoEntityServiceBean.getAllRepositories();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/id/",produces={"application/json","application/xml"})
    public ResponseEntity<Repository> getAllRepositorysById(@RequestParam long id) {
        logger.info("Executing Get id on repositories");
        Repository result = repoEntityServiceBean.findRepositoriesByIdEquals(id);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/products/",produces={"application/json","application/xml"})
    public ResponseEntity<Collection<String>> getAllProducts() {
        logger.info("Executing Get on repositories");
        Collection<Repository> result = repoEntityServiceBean.getAllRepositories();
        Collection<String> products = result.stream().map(Repository::getProduct).collect(Collectors.toSet());
        return ResponseEntity.ok().body(products);
    }

    @GetMapping(value = "/cnas/",produces={"application/json","application/xml"})
    public ResponseEntity<Collection<String>> getAllCNAs() {
        logger.info("Executing Get on repositories");
        Collection<Repository> result = repoEntityServiceBean.getAllRepositories();
        Collection<String> cnas = result.stream().map(Repository::getCna).collect(Collectors.toSet());
        return ResponseEntity.ok().body(cnas);
    }

    @PostMapping(
            consumes={"application/json","application/xml"},
            produces={"application/json","application/xml"})
    public ResponseEntity<Repository> addRepository(@RequestBody Repository repository) {
        logger.info("Executing Post on repositories");
        this.repoEntityServiceBean.addRepositoryToDatabase(repository);
        URI uri = URI.create("/repository/" + repository.getId());
        return ResponseEntity.created(uri).body(repository);
    }

    @DeleteMapping
    public ResponseEntity deleteAllRepositories() {
        logger.info("Executing Delete on repositories");
        repoEntityServiceBean.deleteAllRepositories();
        return ResponseEntity.ok().build();
    }

}
