package fr.miage.orleans.wsi.clientserviceauthentifie.proxyservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.miage.orleans.wsi.clientserviceauthentifie.proxyservice.exceptions.BadTokenException;
import fr.miage.orleans.wsi.clientserviceauthentifie.proxyservice.exceptions.NoAuthenticationServerIsKnownException;
import fr.miage.orleans.wsi.clientserviceauthentifie.proxyservice.exceptions.NoServerFoundException;
import fr.miage.orleans.wsi.clientserviceauthentifie.proxyservice.exceptions.UnknownUserException;
import modele.Sondage;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static services.Constantes.*;

public class ProxyService {


    public static String getToken(String urlAuthentication, String login, String password) throws ResourceAccessException, NoAuthenticationServerIsKnownException, NoServerFoundException, UnknownUserException {

        if (urlAuthentication == null || urlAuthentication.isEmpty()) {
            throw new NoAuthenticationServerIsKnownException();
        }

        RestTemplate restTemplate = new RestTemplate();
        // headers
        HttpHeaders httpHeaders = new HttpHeaders();
// body
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.put(LOGIN, Arrays.asList(login));
        map.put(PASSWORD, Arrays.asList(password));
        // headers + body
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String, String>>(map, httpHeaders);
// REST call for Location

        try {
            ResponseEntity<String> resultat = restTemplate.postForEntity(urlAuthentication, httpEntity, String.class);
            String token = (String) resultat.getHeaders().get(AUTHORIZATION).get(0);
            return token;

        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == HttpStatus.NOT_FOUND.value()) {
                throw new NoServerFoundException();
            }
            if (e.getStatusCode().value() == HttpStatus.UNAUTHORIZED.value()) {
                throw new UnknownUserException();
            }
        }
        return null;
    }


    public static List<Sondage> getListeSondage(String token) throws ResourceAccessException, BadTokenException, NoServerFoundException {
        RestTemplate restTemplate1 = new RestTemplate();
        // headers
        HttpHeaders httpHeaders2 = new HttpHeaders();
        httpHeaders2.put(TOKEN, Arrays.asList(token));
        // body
        MultiValueMap<String, String> map2 = new LinkedMultiValueMap<String, String>();
        // headers + body
        HttpEntity<MultiValueMap<String, String>> httpEntity2 = new HttpEntity<MultiValueMap<String, String>>(map2, httpHeaders2);
// REST call for Location
        ResponseEntity<String> resultat2 = null;
        try {
            resultat2 = restTemplate1.exchange(URL_SONDAGE, HttpMethod.GET, httpEntity2, String.class);

        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() != HttpStatus.OK.value()) {
                if (e.getStatusCode().value() == HttpStatus.NOT_FOUND.value()) {
                    throw new NoServerFoundException();
                }

                throw new BadTokenException(e.getResponseHeaders().get(LOCATION).get(0));
            }

        }


        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Sondage> liste = objectMapper.readValue(resultat2.getBody(), objectMapper.getTypeFactory().constructCollectionType(List.class, Sondage.class));
            return liste;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();

    }
}
