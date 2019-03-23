package com.etu.servtokenn;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

public class SecurityMap {

    private Map<String, String> map;
    private Date expiration = new Date(3600 * 1000);
    private String secretKey = "key";


    public String encode(String login) {
        try {
            String jwt = Jwts.builder()
                    .setSubject("login")
                    .setExpiration(new Date(System.currentTimeMillis() + expiration.getTime()))
                    .claim("login", login)
                    .signWith(SignatureAlgorithm.HS256, secretKey.getBytes("UTF-8"))
                    .compact();
            map.put(login, jwt);
            return jwt;
        } catch (Exception e) {
            return null;
        }
    }

    public String decode(String token) {
        try {
            String jwt = token;
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey.getBytes("UTF-8"))
                    .parseClaimsJws(jwt);
            String login = map.get(claims.getBody().get("login"));
            return login;
/*            if(login==null){
                return null;
            }
            return jwt;*/
        } catch (Exception e) {
            return null;
        }
    }





        /*
        Claims claims= Jwts.claims().setSubject(login);
        claims.put("roles","joueur");
        String token=Jwts.builder().setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis()+ expiration.getTime()))
                .signWith(SignatureAlgorithm.HS256,secretKey.getBytes())
                .compact();
        return token;
        */
/*
        Jwts jwsClaims = Jwts.parser()
                .setSigningKey(secretKey.getBytes("UTF-8"))
                .parseClaimsJws(token).getBody()
                .;



                .setSigningKey(DatatypeConverter.parseBase64Binary(apiKey.getSecret()))
                .parseClaimsJws(jwt).getBody();
        System.out.println("ID: " + claims.getId());
        System.out.println("Subject: " + claims.getSubject());
        System.out.println("Issuer: " + claims.getIssuer());
        System.out.println("Expiration: " + claims.getExpiration());
        */


}
