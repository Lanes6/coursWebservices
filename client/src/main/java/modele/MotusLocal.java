package modele;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MotusLocal implements Facade {

    private Joueur player;

    public String connexion(String pseudo) {
        if(player==null){
            this.player = new Joueur();
            String json = "";
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            this.player.setPseudo(pseudo);
            RestTemplate restTemplate = new RestTemplate();
            try {
                json = ow.writeValueAsString(this.player);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<String>(json, headers);
            try {
                ResponseEntity<String> response = restTemplate.postForEntity(uriJoueur, httpEntity, String.class);
                if (response.getStatusCode().value() == HttpStatus.CREATED.value()) {
                    return "Le joueur " + this.player.getPseudo() + " a été créé";
                } else {
                    return "Erreur inconnue";
                }
            } catch (HttpClientErrorException e) {
                if (e.getStatusCode().value() == HttpStatus.NOT_FOUND.value()) {
                    return "Requete mal formée";
                }
                if (e.getStatusCode().value() == HttpStatus.UNAUTHORIZED.value()) {
                    this.player.setPseudo(pseudo);
                    return "Reprise de la session du joueur " + this.player.getPseudo();
                }
                return "Erreur inconnue";
            }
        }else{
            return "Vous êtes déja connecté en temps que " + player.getPseudo() + ". Deconnectez vous d'abord!";
        }
    }

    public String deconnexion() {
        if(player==null){
            return "Vous n'êtes pas connecté";
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("pseudo",player.getPseudo());
        RestTemplate restTemplate = new RestTemplate();
        try{
            restTemplate.delete ( uriDeco,  params );
            player=null;
            return "Deconnexion réussie";
        } catch (HttpClientErrorException e) {
            return e.getResponseBodyAsString();
        }
    }


    public String getDicos() {

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uriDicos, String.class);
        return result;
    }

    public String creaPartie(String dico) {
        if(player==null){
            return "Connectez vous avant de créer une partie";
        }
        String json="{\"pseudo\" : \""+player.getPseudo()+"\",\"dico\" : \""+dico+"\"}";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<String>(json, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(uriPartie, httpEntity, String.class);
            return "Creer";
        } catch (HttpClientErrorException e) {
            return "Erreur inconnue";
        }
    }

    public String getPartie() {
        String temp="";
        if(player==null){
            return "Vous n'êtes pas connecté";
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("pseudo",player.getPseudo());
        RestTemplate restTemplate = new RestTemplate();
        String result=restTemplate.getForObject(uriGetPartie,String.class,params);
        result=result.replace("}","");
        result=result.replace("{","");
        result=result.replace("\"","");
        return result.trim();
    }

    public String jouer(String mot) {
        if(player==null){
            return "Connectez vous avant de jouer";
        }
        String json="{\"pseudo\" : \""+player.getPseudo()+"\",\"mot\" : \""+mot+"\"}";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<String>(json, headers);
        try {
            restTemplate.put(uriPartie,httpEntity,String.class);
            return "Jouer";
        } catch (HttpClientErrorException e) {
            return "Erreur inconnue";
        }
    }
}



























        /*
        boolean paf=false;

        if(paf) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            MultiValueMap<String, Joueur> parts = new LinkedMultiValueMap<String, Joueur>();
            parts.add("Joueur", player);
            HttpEntity<MultiValueMap<String, Joueur>> httpEntity = new HttpEntity<MultiValueMap<String, Joueur>>(parts, headers);
            ResponseEntity<String> resultat = restTemplate.postForEntity(uriJoueur, httpEntity, String.class);
        }else{
            String json="";
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            try {
                json = ow.writeValueAsString(player);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<String>(json, headers);
            ResponseEntity<String> resultat = restTemplate.postForEntity(uriJoueur, httpEntity, String.class);
        }




        return "ok";
        */

        /* ARRIVE A SE CONNECTER********************************
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Joueur> parts = new LinkedMultiValueMap<String, Joueur>();
        parts.add("joueur", player);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        restTemplate.postForObject("http://localhost:8080/joueur", parts, Joueur.class);

        return "ok";
        */

        /*RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter jsonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
        //jsonHttpMessageConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        restTemplate.getMessageConverters().add(jsonHttpMessageConverter);



        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
        parts.add("joueur", player);
        Object response = restTemplate.postForObject("http://localhost:8080/joueur", parts, String.class);


*/







        /*
        RestTemplate restTemplate = new RestTemplate();
        //HttpEntity<Joueur> entity = new HttpEntity<Joueur>(player);
        //restTemplate.exchange("http://localhost:8080/joueur", HttpMethod.POST, entity, Joueur.class);

        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        String objectResponse = restTemplate.postForObject("http://localhost:8080/joueur",, String.class);
        */




/*
        MultiValueMap<String, Joueur> map= new LinkedMultiValueMap<String, Joueur>();
        map.put("Joueur", Arrays.asList(player));
        // headers + body
        HttpEntity<MultiValueMap<String,Joueur>> httpEntity = new HttpEntity<MultiValueMap<String,Joueur>>(map , httpHeaders);

        ResponseEntity<String> resultat = restTemplate.postForEntity(uriJoueur, httpEntity, String.class);
        String contenu = resultat.getBody();
*/





/*
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Joueur> entity = new HttpEntity<Joueur>(player,headers);
        restTemplate.exchange("http://localhost:8080/joueur", HttpMethod.POST, entity, String.class);
*/
        //RestTemplate restTemplate=new RestTemplate();
       // ResponseEntity<Joueur> response = restTemplate.exchange(uriJoueur, HttpMethod.POST,request,Joueur.class);


        /*
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        //Add the Jackson Message converter
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        // Note: here we are making this converter to process any kind of response,
        // not only application/*json, which is the default behaviour
        converter.setSupportedMediaTypes(Arrays.asList(new MediaType[]{MediaType.ALL}));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
        */

        //HttpHeaders requestHeaders = new HttpHeaders();
        //requestHeaders.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
        //ResponseEntity<String> st = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);



        //HttpEntity<Joueur> httpEntity = new HttpEntity<Joueur>(player);
        //ResponseEntity<Joueur> responseEntity=restTemplate.exchange(uriJoueur,HttpMethod.POST,httpEntity,Joueur.class);

        /*
        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        HttpEntity<Joueur> request = new HttpEntity<Joueur>(player);
        Joueur joueur = restTemplate.postForObject(uriJoueur, request, Joueur.class);
        */


        /*
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Joueur> request = new HttpEntity<Joueur>(player);
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);


        ResponseEntity<Joueur> response = restTemplate.exchange(uriJoueur, HttpMethod.POST,request,Joueur.class);
        */




        /*
        try{
            ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:8080/joueur",httpEntity,String.class);
        }catch (HttpClientErrorException e){
            System.out.println("aie");
        }*/

//    }

//}
