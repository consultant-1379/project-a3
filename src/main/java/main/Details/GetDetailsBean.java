package main.Details;

import jdk.internal.loader.Resource;
import main.variables.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Base64;
import java.util.Scanner;

@Component
public class GetDetailsBean {

    public static Configuration getInput(){
        String filename = "src\\main\\java\\main\\Details\\Details.txt";
        File tmpDir = new File(filename);
        boolean exists = tmpDir.exists();
        if (exists){
            tmpDir.delete();
        }
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a username: ");
        String user= sc.nextLine();
        System.out.print("Enter a password: ");
        String pass= sc.nextLine();

        return new Configuration(user, pass);
    }

    public static void saveDataToFile(Configuration con) throws IOException {
        String user = con.getUser();
        String encodedString = Base64.getEncoder().encodeToString(con.getPass().getBytes());
        BufferedWriter writer = new BufferedWriter(new FileWriter("src\\main\\java\\main\\Details\\Details.txt"));
        writer.write(user + "," + encodedString);
        writer.close();
    }

    public static Details getDataFromFile() throws FileNotFoundException {
        File myObj = getResourceAsFile("Details.txt");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            String[] list = data.split(",");
            String user = list[0];
            String pass = list[1];
            return new Details(user, pass);
        }
        myReader.close();
        return null;
    }

    public static File getResourceAsFile(String resourcePath) {
        try {
            ClassPathResource resource = new ClassPathResource("Details.txt");

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
