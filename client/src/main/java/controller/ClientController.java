package controller;

import modele.Facade;
import modele.MotusLocal;

import java.util.Scanner;

public class ClientController {
    public static void main(String args[]) {
        int choix;
        Scanner sc = new Scanner(System.in);
        Facade motus = new MotusLocal();
        while (true){
            System.out.println("-----------------------");
            System.out.println("Actions=> 1-Connexion  2-Deconnexion  3-Dicos  4-Nouvelle partie  5-Jouer  6-Info Partie 0-Quitter");
            choix=Integer.parseInt(sc.nextLine());
            switch (choix){
                case 1: System.out.println("Nom de joueur:");
                case 2: System.out.println(motus.deconnexion());
                case 3: System.out.println(motus.getDicos());
                case 4: System.out.println("-");
                case 5: System.out.println("-");
                case 6: System.out.println("-");
                case 7: System.out.println("-");
                case 0: break;
            }

        }
        /*
        String resConnect="";

        resConnect = motus.connexion("J1");
        System.out.println(resConnect);
        sc.nextLine();

        resConnect = motus.deconnexion();
        System.out.println(resConnect);
        sc.nextLine();

        resConnect = motus.connexion("J2");
        System.out.println(resConnect);
        sc.nextLine();

*/

        //String resDico=getDicos();


    }
}




































        //Bordel
        /*final String uriJoueur = "http://localhost:8080/joueur";
        String test="paf";
        Joueur player=new Joueur();
        player.setPseudo("paf");
        String json="";

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            json = ow.writeValueAsString(player);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(json);


        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Joueur> parts = new LinkedMultiValueMap<String, Joueur>();
        parts.add("test", player);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        restTemplate.postForObject("http://localhost:8080/joueur", parts, Joueur.class);

*/



        /*
        MappingJackson2HttpMessageConverter jsonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
        jsonHttpMessageConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        restTemplate.getMessageConverters().add(jsonHttpMessageConverter);
*/
        /*

        MultiValueMap<String, Joueur> parts = new LinkedMultiValueMap<String, Joueur>();
        parts.add("test", player);
        restTemplate.postForObject("http://localhost:8080/test", parts, Joueur.class);


*/


        //System.out.println(response);


        //Joueur player= new Joueur();
        //player.createJoueur("ps");



   /* public static Joueur connexion(String pseudo, String mdp){
        final String uri = "http://localhost:8080/joueur";
        RestTemplate restTemplate = new RestTemplate();



    }*/
        /*RestTemplate restTemplate = new RestTemplate();
        // headers
        HttpHeaders httpHeaders = new HttpHeaders();
        // body
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.put("login", Arrays.asList(login));
        // headers + body
        HttpEntity<MultiValueMap<String,String>> httpEntity = new HttpEntity<MultiValueMap<String,String>>(map , httpHeaders);
        // Appel rest pour récupérer un objet String si tout va bien. La requête Post nécessite ici un paramềtre login



        try {
            ResponseEntity<String> resultat = restTemplate.postForEntity(urlAuthentication, httpEntity, String.class);
            String contenu = resultat.getBody();

        }
        catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == HttpStatus.NOT_FOUND.value()) {
                throw new NoServerFoundException();
            }
            if (e.getStatusCode().value() == HttpStatus.UNAUTHORIZED.value()) {
                throw new UnknownUserException();
            }
        }
            */

