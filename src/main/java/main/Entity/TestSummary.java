package main.Entity;

public class TestSummary {
    private String jenkinsJob;
    private int testsRun;
    private int testsPassed;
    private Long period;
    private double failureRate;

    public TestSummary(String jenkinsJob, int testsRun, int testsPassed, Long period) {
        this.jenkinsJob = jenkinsJob;
        this.testsRun = testsRun;
        this.testsPassed = testsPassed;
        this.failureRate = calculateFailureRate();
        this.period = period;
    }

    public TestSummary(String jobName, Long period) {
        this.jenkinsJob = jobName;
        this.period = period;
    }

    public Long getPeriod() {
        return period;
    }

    private double calculateFailureRate() {
        if(testsPassed == 0){
            return 0;
        }
        return ((100/testsRun) * testsPassed);
    }

    public String getRepoName() {
        return jenkinsJob;
    }

    public int getTestsRun() {
        return testsRun;
    }

    public int getTestsPassed() {
        return testsPassed;
    }

    @Override
    public String toString() {
        return "TestSummary{" +
                "jenkinsJob='" + jenkinsJob + '\'' +
                ", testsRun=" + testsRun +
                ", testsPassed=" + testsPassed +
                ", period=" + period + "Days" +
                '}';
    }
}
