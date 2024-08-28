package main;

import main.DBPopulationService.DatabasePopulationService;
import main.Details.Details;
import main.Details.GetDetailsBean;
import main.JenkinsHandlers.JenkinsRepo;
import main.JenkinsHandlers.JenkinsRepository;
import main.variables.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


import java.io.FileNotFoundException;
import java.io.IOException;

@SpringBootApplication
public class Application {

    public Application(){}

    public static void main(String[] args) throws FileNotFoundException {
        String URL = "https://fem104-eiffel004.lmera.ericsson.se:8443/jenkins";
        ApplicationContext context = SpringApplication.run(Application.class, args);

//		Configuration con = GetDetailsBean.getInput();
//		GetDetailsBean.saveDataToFile(con)

        Details detail = null;
        detail = GetDetailsBean.getDataFromFile();

//      AlertForBuilds alert = new AlertForBuilds(con, URL);
//      Thread t = new Thread(alert);
//      t.start();

        Configuration con = new Configuration(detail.getUser(),detail.getPass());

        //Need this when application first run to populate the db;
		DatabasePopulationService databasePopulationService = context.getBean(DatabasePopulationService.class);
		databasePopulationService.populateDatabase(con);
    }
}
