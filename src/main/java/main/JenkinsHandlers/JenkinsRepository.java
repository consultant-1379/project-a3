package main.JenkinsHandlers;


import main.Entity.TestFailPassRate;
import main.Entity.TestSummary;
import main.variables.Configuration;

import java.util.List;

public interface JenkinsRepository {

    List<String> returnAllJenkinsUrls(Configuration con);

    TestFailPassRate testReportFailedPassRate(String jobName);

    void testWithHighestFailedReports(Configuration con);

    TestSummary createTestSummaryFromJob(String jobName, Long period);
}
