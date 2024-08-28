package main.DBPopulationService;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ExcelProcessingService {

    private XSSFSheet sheet;

    private static String EXCEL_PATH = "export.xlsx";

    public List<CNA> extractRepositoryDataFromExcel(){
        // column 4: Number, column 11: CNA Funct. Resp, column 16: RPM
        // RPM column in the exported excel file is actually the repos
        List<CNA> cnas = new ArrayList<>();
        List<String> productCol = this.getColumn(10);
        List<String> cnaCol = this.getColumn(11);
        List<String> codes = this.getColumn(4);
        List<String> repos = this.getColumn(16);
        List<String> reposNoNulls = this.getColumn(16);

        // remove column titles and first few rows that don't seem to have any CNA
        for(int i = 0; i < 4; i++) {
            productCol.remove(0);
            cnaCol.remove(0);
            codes.remove(0);
            repos.remove(0);
        }

        for(String repo: repos) {
            if(repo == "") {
                reposNoNulls.remove(repos.indexOf(repo));
            }
        }

        CNA newCNA = null;
        int counter = 0;
        int cnaCounter = 0;

        for(String name : cnaCol) {
            if(name != "" && productCol.get(cnaCounter).contains("ENM") ) {
                newCNA = new CNA(name.trim(),productCol.get(cnaCounter));
                cnas.add(newCNA);
            }
            newCNA.addCode(codes.get(counter));
            if(repos.get(counter).contains("com.ericsson")) {
                newCNA.addRepo(repos.get(counter));
            }
            counter++;
            cnaCounter++;
        }

        return cnas;
    }

    public static void setExcelPath(String path){
        EXCEL_PATH = path;
    }

    private XSSFSheet extractSheetFromProductMatrix() {
        try {
            // Create an object representing the sheet of the Excel file
            FileInputStream fis = new FileInputStream(getResourceAsFile(EXCEL_PATH));
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            sheet = wb.getSheetAt(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sheet;
    }

    public List<String> getColumn(int col) {
        XSSFSheet sheet = extractSheetFromProductMatrix();
        String cellVal;
        Row row;
        Cell cell;
        List<String> cnas = new ArrayList<>();

        Iterator<Row> rowIter = sheet.rowIterator();
        while(rowIter.hasNext()) {
            row = rowIter.next();
            cell =  row.getCell(col);
            cellVal = cell.getStringCellValue();
            cnas.add(cellVal);
        }
        return cnas;
    }

    public static File getResourceAsFile(String resourcePath) {
        try {
            ClassPathResource resource = new ClassPathResource(resourcePath);

            InputStream in = resource.getInputStream();

            if (in == null) {
                return null;
            }

            File tempFile = File.createTempFile(String.valueOf(in.hashCode()), ".tmp");
            tempFile.deleteOnExit();

            try (FileOutputStream out = new FileOutputStream(tempFile)) {
                //copy stream
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
            return tempFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
