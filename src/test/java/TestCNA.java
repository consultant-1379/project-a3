
import main.DBPopulationService.CNA;
import org.junit.jupiter.api.BeforeAll;
;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCNA {
    public CNA cna = new CNA("Ricky Quillinan","ENM-NHM");;

    @Test
    public void testAddCode() {
        List<String> codes = new ArrayList<>();
        codes.add("Code");
        cna.addCode("Code");
        assertEquals(codes, cna.getCodes());
    }

    @Test
    public void testGetCodes() {
        List<String> codes = new ArrayList<>();
        codes.add("Code");
        cna.addCode("Code");
        assertEquals(codes, cna.getCodes());
    }

    @Test
    public void testAddRepo() {
        List<String> repos = new ArrayList<>();
        repos.add("Repo");
        cna.addRepo("Repo");
        assertEquals(repos, cna.getRepos());
    }

    @Test
    public void testGetRepos() {
        List<String> repos = new ArrayList<>();
        repos.add("Repo");
        cna.addRepo("Repo");
        assertEquals(repos, cna.getRepos());
    }

    @Test
    public void testGetName() {
        assertEquals("Ricky Quillinan", cna.getName());
    }

    @Test
    public void testToString() {
        assertEquals("Name: Ricky Quillinan\nProduct: ENM-NHM\n", cna.toString());
    }
}
