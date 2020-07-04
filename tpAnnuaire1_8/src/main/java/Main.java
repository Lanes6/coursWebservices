import service.Annuaire;

import javax.xml.ws.Endpoint;

public class Main {

    public static void main(String args[]) {
        Annuaire server = new Annuaire();
        Endpoint endpoint = Endpoint.publish("http://localhost:9191/annuaire",server);
    }
}
