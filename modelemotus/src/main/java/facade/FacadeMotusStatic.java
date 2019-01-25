package facade;

import exceptions.MaxNbCoupsException;
import exceptions.MotInexistantException;
import exceptions.PseudoDejaPrisException;
import exceptions.PseudoNonConnecteException;
import modele.Dico;
import modele.Partie;

import java.util.*;

public class FacadeMotusStatic implements FacadeMotus {

    private Map<String, Partie> parties = new HashMap<>();

    private Collection<String> pseudos= new ArrayList<>();




    private void checkConnecte(String pseudo) throws PseudoNonConnecteException {
        if (!this.pseudos.contains(pseudo)) {
            throw new PseudoNonConnecteException();
        }
    }

    @Override
    public void connexion(String pseudo) throws PseudoDejaPrisException {
        if (pseudos.contains(pseudo)) {
            throw new PseudoDejaPrisException();
        }
        this.pseudos.add(pseudo);
    }

    @Override
    public void deconnexion(String pseudo) throws PseudoNonConnecteException {
        this.checkConnecte(pseudo);
        this.parties.remove(pseudo);
        this.pseudos.remove(pseudo);
    }

    @Override
    public String jouer(String pseudo, String mot) throws MotInexistantException, MaxNbCoupsException, PseudoNonConnecteException {
        this.checkConnecte(pseudo);
        Partie p = parties.get(pseudo);
        return p.jouer(mot);
    }


    @Override
    public void nouvellePartie(String pseudo, String dicoName) throws PseudoNonConnecteException {
        this.checkConnecte(pseudo);
        this.parties.put(pseudo,new Partie(Dico.getInstance(dicoName)));
    }

    @Override
    public Collection<String> getListeDicos(){
        return Arrays.asList("dico7lettres","dicosimple7lettres");
    }

    @Override
    public Partie getPartie(String pseudo) throws PseudoNonConnecteException {
        this.checkConnecte(pseudo);
        return parties.get(pseudo);
    }

}
