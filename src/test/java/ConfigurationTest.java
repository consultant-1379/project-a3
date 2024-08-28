import main.variables.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ConfigurationTest {

    @Test
    public void testUserName(){
        Configuration con = new Configuration("Neil", "");
        assertEquals("Neil", con.getUser());
    }

    @Test
    public void testPassword(){
        Configuration con = new Configuration("", "******");
        assertEquals("******", con.getPass());
    }

    @Test
    public void testSetAlert(){
        Configuration con = new Configuration("", "");
        con.setFlag(true);
        assertEquals(true, con.isFlag());
    }

    @Test
    public void testIfIsJob(){
        Configuration con = new Configuration("", "");
        con.setBuildName("job");
        assertEquals(con.getBuildName(), "job");
    }

    @Test
    public void testToString(){
        Configuration con = new Configuration("", "");
        con.setFlag(true);
        con.setBuildName("job one");
        assertEquals(con.toString(), "Configuration{" +
                "Alert=" + con.isFlag() +
                " for buildName='" + con.getBuildName() + '\'' +
                '}');
    }

}
