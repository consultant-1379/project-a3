package main.TestDataHandlers;

import main.Entity.Repository;
import main.Entity.TestFailPassRate;
import main.Entity.TestSummary;
import main.JenkinsHandlers.JenkinsRepo;
import main.RepoHandlers.RepoEntityServiceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@Lazy
public class TestDataServiceBean {

    @Autowired
    RepoEntityServiceBean repoEntityServiceBean;

    @Autowired
    JenkinsRepo jenkinsRepo;

    // for each entry on the db. create a test summary object from that jenkins job
    public Collection<TestSummary> getAllTestSummaries(Long period) {
        List<Repository> allRepositories = repoEntityServiceBean.getAllRepositories();
        List<TestSummary> testSummaries = new ArrayList<>();
        for(Repository repository: allRepositories){
            testSummaries.add(jenkinsRepo.createTestSummaryFromJob(repository.getJenkinsURL(), period));
        }

        return testSummaries;
    }

    public Collection<TestSummary> getTestSummariesForCNA(String cna, Long period) {
        List<Repository> allRepositories = repoEntityServiceBean.getRepositoriesByCNA(cna);
        List<TestSummary> testSummaries = new ArrayList<>();
        for(Repository repository: allRepositories){
            System.out.println(repository);
            testSummaries.add(jenkinsRepo.createTestSummaryFromJob(repository.getJenkinsURL(), period));
            System.out.println(testSummaries);
        }

        return testSummaries;
    }

    public Collection<TestSummary> getTestSummariesForProduct(String product, Long period) {
        List<Repository> allRepositories = repoEntityServiceBean.getRepositoriesByCNA(product);
        List<TestSummary> testSummaries = new ArrayList<>();
        for(Repository repository: allRepositories){
            testSummaries.add(jenkinsRepo.createTestSummaryFromJob(repository.getJenkinsURL(), period));
        }

        return testSummaries;
    }

    public Collection<TestSummary> returnMessage(String product, Long period) {
        List<Repository> allRepositories = repoEntityServiceBean.getRepositoriesByCNA(product);
        List<TestSummary> testSummaries = new ArrayList<>();
        for(Repository repository: allRepositories){
            testSummaries.add(jenkinsRepo.returnDefault(repository.getJenkinsURL(), period));
        }
        testSummaries.add(jenkinsRepo.returnDefault(product, period));
        return testSummaries;
    }

    public Collection<TestFailPassRate> getAllTestPassFailRate() {
        List<Repository> allRepositories = repoEntityServiceBean.getAllRepositories();
        List<TestFailPassRate> testPassFail = new ArrayList<>();
        for(Repository repository: allRepositories){
            testPassFail.add(jenkinsRepo.testReportFailedPassRate(repository.getJenkinsURL()));
        }

        return testPassFail;
    }

    public Collection<TestFailPassRate> getPassFailRateForCNA(String cna) {
        List<Repository> allRepositories = repoEntityServiceBean.getRepositoriesByCNA(cna);
        List<TestFailPassRate> testPassFail = new ArrayList<>();
        for(Repository repository: allRepositories){
            System.out.println(repository);
            testPassFail.add(jenkinsRepo.testReportFailedPassRate(repository.getJenkinsURL()));
            System.out.println(testPassFail.toString());
        }

        return testPassFail;
    }

    public Collection<TestFailPassRate> getPassFailRateForProduct(String product) {
        List<Repository> allRepositories = repoEntityServiceBean.getRepositoriesByCNA(product);
        List<TestFailPassRate> testPassFail = new ArrayList<>();
        for(Repository repository: allRepositories){
            testPassFail.add(jenkinsRepo.testReportFailedPassRate(repository.getJenkinsURL()));
        }

        return testPassFail;
    }



}
