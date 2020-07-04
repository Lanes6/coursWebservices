package service;

import exeptions.pasTrouve;
import modele.Personne;

import java.util.Collection;
import java.util.HashMap;
import javax.jws.WebService;

@WebService
public class Annuaire {
    private static HashMap<String, Personne> annuaire;
    static {
        annuaire = new HashMap<String,Personne>();
        annuaire.put("moal", new Personne("moal","frederic","0238000000"));
        annuaire.put("exbrayat", new Personne("exbrayat","matthieu","0238000000"));
    };
    public String searchTelephone(String nom) throws pasTrouve {
        Personne p =  annuaire.get(nom);
        if (p==null) {
            //return "Pas trouve";
            throw new pasTrouve();
        }
        return p.getTelephone();
    }
    public void addPersonne(Personne p) {
        annuaire.put(p.getNom(), p);
    }
    public void addPersonneList(String nom, String prenom, String tel) {
        Personne p = new Personne(nom,prenom,tel);
        annuaire.put(p.getNom(), p);
    }
    public Collection<String> getAllNom() {
        return annuaire.keySet();
    }
}