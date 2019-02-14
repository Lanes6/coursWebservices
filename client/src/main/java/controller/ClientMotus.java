package controller;

//import com.sun.org.slf4j.internal.Logger;
//import com.sun.org.slf4j.internal.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ClientMotus {

    //private static final Logger log = LoggerFactory.getLogger(ClientMotus.class);

    public static void main(String args[]) {
        //SpringApplication.run(ClientMotus.class);
    }
/*
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        String url = "http://localhost:8080/user/1";

        RestTemplate restTemplate = new RestTemplate();


        /*User user = restTemplate.getForObject(url, User.class);

        System.out.println("GET User :" + user.getFirstName() + " " + user.getLastName());
    }
    */
}
