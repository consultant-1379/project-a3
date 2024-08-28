
import main.DBPopulationService.CNA;
import main.DBPopulationService.ExcelProcessingService;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TestReadExcel {

    private ExcelProcessingService excelProcessingService;
    private XSSFSheet sheet;

    CNA cna1 = new CNA("Ricky Quillinan",
            new ArrayList<>(Arrays.asList("CNA 403 3144", "CXP 903 1393", "CXP 903 1394")),
            new ArrayList<>(Arrays.asList("", "com.ericsson.oss.mediation.nodediscovery/node-discovery-module", "com.ericsson.oss.mediation.nodediscovery/node-discovery-handlers")));
    CNA cna2 = new CNA("Paola Delsante",
            new ArrayList<>(Arrays.asList("CNA 403 3010", "CXP 903 0672", "CXP 903 3143")),
            new ArrayList<>(Arrays.asList("", "com.ericsson.oss.services.fm.models/fm-mediation-event-model", "com.ericsson.oss.mediation.fm/snmp-fm-engine-model")));



    @Test
    public void testGetColumn() throws IOException {
        excelProcessingService = new ExcelProcessingService();
        ExcelProcessingService.setExcelPath("testSheet.xlsx");
        FileInputStream fis = new FileInputStream("testSheet.xlsx");
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        sheet = wb.getSheetAt(0);

        List<String> col = new ArrayList<>(Arrays.asList(
                "Number",
                "",
                "",
                "",
                "CNA 403 3144",
                "CXP 903 1393",
                "CXP 903 1394",
                "CNA 403 3010",
                "CXP 903 0672",
                "CXP 903 3143"));
        assertEquals(col, excelProcessingService.getColumn(0));
    }

}
