package main.variables;

public class Configuration {

    private final String user;
    private final String pass;
    private boolean Flag;
    private String buildName;

    public boolean isFlag() {
        return Flag;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public void setFlag(boolean steFlag) {
        this.Flag = steFlag;
    }

    public Configuration(String user, String pass){
        this.user = user;
        this.pass = pass;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "Alert=" + Flag +
                " for buildName='" + buildName + '\'' +
                '}';
    }

}
