import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.Job;
import main.AlertForBuilds;
import main.Details.Details;
import main.Details.GetDetailsBean;
import main.variables.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestsAlertForBuilds {

    @Mock
    static Configuration con;

    @Mock
    GetDetailsBean getDetailsBean;

    static AlertForBuilds alert;

    private static Thread thread;

    @BeforeAll
    static void startTests() throws IOException {

        Details details = GetDetailsBean.getDataFromFile();
        con = new Configuration(details.getUser(), details.getPass());
        alert = new AlertForBuilds(con, "");
        thread = new Thread(alert);
    }

    @Test
    @Order(1)
    public void start(){
        thread.start();
        assertEquals(thread.isAlive(),true);
    }

    @Test
    @Order(2)
    public void checkJenkinsApiUrlCorrect(){
        JenkinsServer jenkins = alert.jenkinsConnect("https://fem104-eiffel004.lmera.ericsson.se:8443/jenkins");
        assertEquals(jenkins.isRunning(),true);
    }

    @Test
    @Order(3)
    public void checkAllBuildsForCondition(){
        JenkinsServer jenkins = alert.jenkinsConnect("https://fem104-eiffel004.lmera.ericsson.se:8443/jenkins");
        alert.checkAllBuilds(jenkins);
        assertEquals(AlertForBuilds.jenkinsConnected,true);
    }

    @Test
    @Order(4)
    public void checkAllBuildsForElseCondition(){
        JenkinsServer jenkins = null;
        alert.checkAllBuilds(jenkins);
        assertEquals(AlertForBuilds.jenkinsConnected,false);
    }


    @Test
    @Order(5)
    public void TestCheckBuildSuccessCondition() throws IOException {
        Map<String, Job> jobs = new HashMap<>();
        JenkinsServer jenkins = alert.jenkinsConnect("https://fem104-eiffel004.lmera.ericsson.se:8443/jenkins");
        jobs = jenkins.getJobs();
        Job job = alert.iterateThroughJobs(jobs.values());
        for (Build build : job.details().getAllBuilds())
            if(build.details().getResult().equals("FAILURE"))
                assertEquals(alert.checkBuildSuccess(build),false);

    }

    @Test
    @Order(6)
    public void TestCheckBuildSuccessElseCondition() throws IOException {
        Map<String, Job> jobs = new HashMap<>();
        JenkinsServer jenkins = alert.jenkinsConnect("https://fem104-eiffel004.lmera.ericsson.se:8443/jenkins");
        jobs = jenkins.getJobs();
        Job job = alert.iterateThroughJobs(jobs.values());
        for (Build build : job.details().getAllBuilds())
            if(build.details().getResult().equals("Success"))
                assertEquals(alert.checkBuildSuccess(build),true);
    }

    @Test
    @Order(7)
    public void stop() throws InterruptedException {
        thread.interrupt();
        thread.join();

    }
}
