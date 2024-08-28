package main;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.Job;
import main.variables.Configuration;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AlertForBuilds implements Runnable{


    private final Configuration con;
    private String URL;
    public static boolean jenkinsConnected = false;
    public static volatile boolean isRunning;

    public AlertForBuilds(Configuration con, String URL){
        this.con = con;
        this.URL = URL;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            isRunning = true;
            JenkinsServer jenkins = jenkinsConnect(URL);
            if(checkAllBuilds(jenkins)){
                con.setFlag(true);
                System.out.println("help");
                System.out.println(con.toString());
            }
            else
                con.setFlag(false);
        }
    }

    public JenkinsServer jenkinsConnect(String url){
        try {
            return new JenkinsServer(new URI(url), con.getUser(), con.getPass());
        }catch(URISyntaxException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkAllBuilds(JenkinsServer jenkins){
        try{
            Map<String, Job> jobs = new HashMap<>();
            if (jenkins != null) {
                jenkinsConnected = true;
                jobs = jenkins.getJobs();
                for (Job job : jobs.values()) {
                    Build build = iterateThroughBuilds(job);
                    if (build != null) {
                        System.out.println("not null");
                        con.setBuildName(job.getName());
                        return true;
                    } else
                        return false;
                }
            }
            else{
                jenkinsConnected = false;
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    // for testing
    public Job iterateThroughJobs(Collection<Job> jobs){
        for (Job job : jobs) {
            return job;
        }
        return null;
    }

    public Build iterateThroughBuilds(Job job) throws IOException {
        for (Build build : job.details().getAllBuilds()) {
            if (checkBuildDuration(build)) {
                if (checkBuildSuccess(build)) {
                    //System.out.println("\r\nAlert: build Failed and under Threshold\n");
                    return build;
                } else {
                    //System.out.println("Build still running");
                    return null;
                }
            } else {
                //System.out.println("Build over Threshold");
                return null;
            }
        }
        return null;
    }

    public boolean checkBuildSuccess(Build build) throws IOException {
        System.out.println(build.details().getResult().equals("FAILURE"));
        if (build.details().getResult().equals("FAILURE"))
            return true;
        else if (build.details().getResult().equals("SUCCESS"))
            return false;
        return false;
    }

    public boolean checkBuildDuration(Build build) {
        double duration = 0;
        try {
            duration = build.details().getDuration();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(duration);
        return duration <= 86400 * 7;
    }

}
