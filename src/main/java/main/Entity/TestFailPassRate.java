package main.Entity;

public class TestFailPassRate {
    private String jenkinsJob;
    private int testsRun;
    private int testsPassed;
    private double failureRate;

    public TestFailPassRate(String jenkinsJob, int testsRun, int testsPassed) {
        this.jenkinsJob = jenkinsJob;
        this.testsRun = testsRun;
        this.testsPassed = testsPassed;
        this.failureRate = calculateFailureRate();
    }

    @Override
    public String toString() {
        return "TestFailPassRate{" +
                "jenkinsJob='" + jenkinsJob + '\'' +
                ", testsRun=" + testsRun +
                ", failureRate=" + failureRate +
                '}';
    }

    public int getTestsRun() {
        return testsRun;
    }

    public String getJenkinsJob() {
        return jenkinsJob;
    }

    public double getFailureRate() {
        return failureRate;
    }

    private double calculateFailureRate() {
        if (testsRun == 0){
            return 0;
        }
        else if (testsPassed == 0)
        {
            return 100;
        }
        else{
            return ((100/testsRun) * testsPassed);
        }
    }
}
