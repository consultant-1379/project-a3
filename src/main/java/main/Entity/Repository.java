package main.Entity;


import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "repository")
@Table(name = "repository")
public class Repository implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @Column(name = "repoName")
    private String repoName;

    @Column(name = "product")
    private String product;

    @Column(name = "cna")
    private String cna;

    @Column(name = "jenkinsURL")
    private String jenkinsURL;

    public Repository(String repoName, String product, String cna, String jenkinsURL) {
        this.repoName = repoName;
        this.product = product;
        this.cna = cna;
        this.jenkinsURL = jenkinsURL;
    }

    public Repository() {

    }

    public Long getId(){
        return this.id;
    }

    public String getRepoName() {
        return repoName;
    }

    public String getProduct() {
        return product;
    }

    public String getCna() {
        return cna;
    }

    public String getJenkinsURL() {
        return jenkinsURL;
    }

    @Override
    public String toString() {
        return "Repository{" +
                "id=" + id +
                ", repoName='" + repoName + '\'' +
                ", product='" + product + '\'' +
                ", cna='" + cna + '\'' +
                ", jenkinsURL='" + jenkinsURL + '\'' +
                '}';
    }
}
