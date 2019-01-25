package fonctionnel;

import exceptions.PseudoDejaPrisException;
import facade.FacadeMotusStatic;
import facade.FacadeMotus;
import org.junit.Assert;
import org.junit.Test;

public class Connexion {


    private static final String DICO_OK_1= "dico7lettres";
    private static final String DICO_OK_2="dicosimple7lettres";
    private static final String DICO_KO="larousse";



    @Test
    public void testConnexionOK1() throws PseudoDejaPrisException {
        FacadeMotus facadeMotus = new FacadeMotusStatic();
        facadeMotus.connexion("babar");
        Assert.assertTrue(true);
    }



    @Test(expected = PseudoDejaPrisException.class)
    public void testConnexionK0() throws PseudoDejaPrisException {
        FacadeMotus facadeMotus = new FacadeMotusStatic();
        facadeMotus.connexion("babar");
        facadeMotus.connexion("babar");
    }




}
