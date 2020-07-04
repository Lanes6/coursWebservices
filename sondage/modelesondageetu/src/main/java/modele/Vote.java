package modele;

import javax.xml.bind.annotation.XmlRootElement;


public class Vote {

    private static long IDENTIFIANTS = 0;

    private long id;
    private String votant;
    private String choix;

    public Vote() {
        this(null, null);
    }

    public Vote(String votant, String choix) {
        id = IDENTIFIANTS++;
        this.votant = votant;
        this.choix = choix;
    }

    public long getId() {
        return id;
    }

    public String getVotant() {
        return votant;
    }

    public void setVotant(String votant) {
        this.votant = votant;
    }

    public String getChoix() {
        return choix;
    }

    public void setChoix(String choix) {
        this.choix = choix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vote)) return false;
        Vote vote = (Vote) o;
        return !(votant != null ? !votant.equals(vote.votant) : vote.votant != null);
    }

    @Override
    public int hashCode() {
        return votant != null ? votant.hashCode() : 0;
    }
}