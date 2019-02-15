package modele;

import com.sun.org.apache.xerces.internal.util.EntityResolver2Wrapper;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;

public class MotusLocal {


    private String pseudo;



    public static String getDicos(){
        final String uri = "http://localhost:8080/dicos";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        return result;
    }


    public static Joueur connexion (String pseudo){
        final String uriJoueur = "http://localhost:8080/joueur";
        Joueur player=new Joueur();
        player.setPseudo(pseudo);


        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Joueur> entity = new HttpEntity<Joueur>(player,headers);
        restTemplate.exchange("http://localhost:8080/joueur", HttpMethod.POST, entity, String.class);

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
        return player;

    }

}
