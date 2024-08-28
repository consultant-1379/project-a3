package main.DBPopulationService;

import java.util.ArrayList;
import java.util.List;

public class CNA {
    private ArrayList<String> codes = new ArrayList<>();
    private ArrayList<String> repos = new ArrayList<>();
    private String product;
    private String name;

    public ArrayList<String> getRepos() {
        return repos;
    }

    public CNA(String name,String product) {
        this.name = name;
        this.product = product;
    }

    public CNA(String name, List<String> codes, List<String> repos){

    }

    public void addCode(String code) {
        codes.add(code);
    }

    public void addRepo(String repo){
        repos.add(repo);
    }

    public ArrayList<String> getCodes() {
        return codes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        String output = "Name: " + name + "\n" +
                        "Product: " + product + "\n";

        for(String repo: this.repos){
            output += repo + "\n";
        }

        return output;
    }

    public String getProduct() {
        return product;
    }
}
