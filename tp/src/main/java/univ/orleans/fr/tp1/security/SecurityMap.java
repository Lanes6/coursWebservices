package univ.orleans.fr.tp1.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;

import java.util.Date;
import java.util.Map;

public class SecurityMap {

    private Map<String, String> map;
    private Date expiration=new Date(3600*1000);
    private String secretKey="key";


    public String encode(String login){
        Claims claims= Jwts.claims().setSubject(login);
        claims.put("roles","joueur");
        String token=Jwts.builder().setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis()+ expiration.getTime()))
                .signWith(SignatureAlgorithm.HS256,secretKey.getBytes())
                .compact();
        return token;
    }

    public String decode(String token){
        Jwts<Claims> jwsClaims = Jwts.parser().setSigningKey(secretKey.getBytes())
                .parseClaimsJws(
    }


}
