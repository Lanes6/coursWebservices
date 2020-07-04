package fr.miage.orleans.wsi.clientserviceauthentifie;

import fr.miage.orleans.wsi.clientserviceauthentifie.controller.Controleur;

public class ClientserviceauthentifieApplication {

    public static void main(String[] args) {

        new Controleur();
/*		RestTemplate restTemplate = new RestTemplate();

		String login = "yo";
		String password = "yo";

		// headers
		HttpHeaders httpHeaders = new HttpHeaders();
// body
		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.put("login", Arrays.asList(login));
		map.put("password",Arrays.asList(password));



		// headers + body
		HttpEntity<MultiValueMap<String,String>> httpEntity = new HttpEntity<MultiValueMap<String,String>>(map , httpHeaders);
// REST call for Location
		ResponseEntity<String>  resultat = restTemplate.postForEntity("http://localhost:8000/login",httpEntity,String.class);


		String token = (String)resultat.getHeaders().get("Authorization").get(0);




		RestTemplate restTemplate1 = new RestTemplate();

		// headers
		HttpHeaders httpHeaders2 = new HttpHeaders();
		httpHeaders2.put("token",Arrays.asList(token));


		// body
		MultiValueMap<String, String> map2= new LinkedMultiValueMap<String, String>();



		// headers + body
		HttpEntity<MultiValueMap<String,String>> httpEntity2 = new HttpEntity<MultiValueMap<String,String>>(map2 , httpHeaders2);
// REST call for Location
		ResponseEntity<String>  resultat2 = restTemplate.exchange("http://localhost:8080/sondages", HttpMethod.GET,httpEntity2,String.class);



		System.out.println(resultat2);
*/

    }


}
