package main.JenkinsHandlers;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;
import main.Entity.TestFailPassRate;
import main.Entity.TestSummary;
import main.RepoHandlers.RepoEntityServiceBean;
import main.variables.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JenkinsRepo implements JenkinsRepository {

    @Autowired
    RepoEntityServiceBean database;

    private static final String URL = "https://fem106-eiffel004.lmera.ericsson.se:8443/jenkins/";
    private static final String URL2 = "https://fem104-eiffel004.lmera.ericsson.se:8443/jenkins";
    private static final String URL3 = "https://fem141-eiffel004.lmera.ericsson.se:8443/jenkins/";

    Configuration con;

    Map<String, Job> jobs = new HashMap<>();

    @Override
    public List<String> returnAllJenkinsUrls(Configuration con) {
        List<String> urls = new ArrayList<>();
        try {
            JenkinsServer jenkins = new JenkinsServer(new URI(URL), con.getUser(), con.getPass());

            for(Job job: jobs.values()){
                urls.add(job.getUrl());
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return urls;
    }

    public TestSummary createTestSummaryFromJob(String jobName, Long period){
        try {
            JenkinsServer jenkins = new JenkinsServer(new URI(URL3));
            JobWithDetails job = jenkins.getJob(jobName);
            if(job == null){
                return new TestSummary(jobName,0,0, period);
            }
            List<Build> builds = job.getBuilds();
            List<BuildWithDetails> buildDetails = new ArrayList<>();
            for(Build build: builds){
                BuildWithDetails buildWithDetails = build.details();
                if(period == null){
                    buildDetails.add(buildWithDetails);
                }
                else{
                    if (buildWithDetails.getDuration() <= 86400 * period){
                        buildDetails.add(buildWithDetails);
                    }
                }

            }

            TestSummary testSummary = processBuildsAndCreateSummary(jobName,buildDetails,period);
            return testSummary;
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private TestSummary processBuildsAndCreateSummary(String jobName, List<BuildWithDetails> buildDetails, Long period) {
        int testsRun = 0;
        int testsPassed = 0;
        for(BuildWithDetails buildWithDetails: buildDetails){
            testsRun++;
            if(buildWithDetails.getResult().equals("SUCCESS")){
                testsPassed++;
            }
        }

        return new TestSummary(jobName,testsRun,testsPassed,period);
    }

    public TestSummary returnDefault(String jobName, Long period){
        return new TestSummary(jobName, 0,0, period);
    }

    public List<String> findJobNameFromVariousServers(String repoPiece, Configuration con){
        try {
            this.con = con;
//            JenkinsServer jenkins = new JenkinsServer(new URI(URL), con.getUser(), con.getPass());
//            JenkinsServer jenkins2 = new JenkinsServer(new URI(URL2), con.getUser(), con.getPass());
            JenkinsServer jenkins = new JenkinsServer(new URI(URL3), con.getUser(), con.getPass());

            List<String> jobNames = findJobName(repoPiece,jenkins);
//                                    jobNames.addAll(findJobName(repoPiece,jenkins2));
//                                    jobNames.addAll(findJobName(repoPiece,jenkins3));

            return jobNames;

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public List<String> findJobName(String usefulPiece,JenkinsServer server) throws IOException {
        return server.getJobs().keySet().stream()
                    .filter(s -> s.toLowerCase()
                    .contains(usefulPiece.toLowerCase()))
                    .collect(Collectors.toList());
    }

    @Override
    public TestFailPassRate testReportFailedPassRate(String jobName) {
        TestSummary summary = createTestSummaryFromJob(jobName, null);
        return new TestFailPassRate(summary.getRepoName(), summary.getTestsRun(), summary.getTestsPassed());


    }

    @Override
    public void testWithHighestFailedReports(Configuration con) {

    }

}
