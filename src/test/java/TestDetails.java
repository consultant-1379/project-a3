import main.Details.Details;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.util.Base64;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestDetails {

    @Test
    public void testUser() {
        String password = "hello1223";
        String encodedString = Base64.getEncoder().encodeToString(password.getBytes());
        Details detail = new Details("neil", encodedString);
        assertEquals("neil", detail.getUser());
    }

    @Test
    public void testPass() {
        String password = "hello1223";
        String encodedString = Base64.getEncoder().encodeToString(password.getBytes());
        Details detail = new Details("neil", encodedString);
        assertEquals("hello1223", detail.getPass());
    }
}
