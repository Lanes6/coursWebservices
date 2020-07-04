package modele;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Sondage {

    private static int IDENTIFIANTS = 0;

    private long id;
    private String question;
    private List<String> reponses;
    private List<Vote> votes;

    public Sondage() {
        this("");
    }

    public Sondage(String question) {
        this(question, new ArrayList<String>());
    }

    public Sondage(String question, List<String> reponses) {
        this.id = IDENTIFIANTS++;
        this.question = question;
        this.reponses = reponses;
        this.votes = new ArrayList<Vote>();
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<String> getReponses() {
        return reponses;
    }

    public void setReponses(List<String> reponses) {
        this.reponses = reponses;
    }

    public void addReponses(String... rep) {
        for (String r : rep) {
            reponses.add(r);
        }
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public void voter(Vote vote) {
        if (reponses.contains(vote.getChoix())) {
            votes.add(vote);
        }
    }
}