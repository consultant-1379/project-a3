package main.DBPopulationService;

import main.Entity.Repository;
import main.JenkinsHandlers.JenkinsRepo;
import main.RepoHandlers.RepoEntityServiceBean;
import main.variables.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabasePopulationService implements IDatabasePopulationService{

    private static final Logger logger = LoggerFactory.getLogger(DatabasePopulationService.class);

    @Autowired
    ExcelProcessingService excelProcessingService;

    @Autowired
    RepoEntityServiceBean repoEntityServiceBean;

    @Autowired
    JenkinsRepo jenkinsRepo;

    @Override
    public void populateDatabase(Configuration con) {
        List<Repository> repositories = this.generateAllRepositoryValues(con);
        logger.info("Inserting  " + String.valueOf(repositories.size()) +" repositories into the database", repositories.size());
        for(Repository repository: repositories){
            repoEntityServiceBean.addRepositoryToDatabase(repository);
        }
    }

    public List<Repository> generateAllRepositoryValues(Configuration con) {
        List<Repository> repositories = new ArrayList<>();
        List<CNA> repositoriesByCNA = excelProcessingService.extractRepositoryDataFromExcel();
        for(CNA cna: repositoriesByCNA) {
            for(String repository: cna.getRepos()){
                String jobName = findSuitableJobName(repository,con);
                repositories.add(new Repository(repository,cna.getProduct(),cna.getName(),jobName));
            }
        }

        return repositories;
    }

    private String findSuitableJobName(String repository,Configuration con) {
        String usefulSegment = extractUsefulSegmentFromRepoName(repository);
        List<String> jobNames= jenkinsRepo.findJobNameFromVariousServers(usefulSegment,con);
        System.out.println(repository +  " " + usefulSegment + " " + jobNames);
        for(String jobName: jobNames){
            if(jobName.contains("PCR") || jobName.contains("PreCodeReview")){
                return jobName;
            }
        }
        if(!jobNames.isEmpty()){
            return jobNames.get(0);
        }

        return "";
    }

    private String extractUsefulSegmentFromRepoName(String repository) {
        String[] split = repository.split("/");
        String[] split1 = {"",""};
        if(split.length > 1) {
            split1 = split[1].split("-");
        }
        return split1[0];
    }
}
