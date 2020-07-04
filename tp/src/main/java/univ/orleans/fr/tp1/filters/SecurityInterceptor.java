package univ.orleans.fr.tp1.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class SecurityInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(SecurityInterceptor.class);
    String uriToken = "http://localhost:8090/token";

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        logger.info("request");
        System.out.println("Start");
        if (httpServletRequest.getHeader("Authozisation") != null) {
            System.out.println("no");
            String jwt=httpServletRequest.getHeader("Authozisation");
            Map<String, String> params = new HashMap<String, String>();
            params.put("jwt", jwt);
            RestTemplate restTemplate = new RestTemplate();
            try {
                String res = restTemplate.getForObject(uriToken, String.class, params);
                return true;
            } catch (HttpClientErrorException | HttpServerErrorException e) {
                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
        }else{
            System.out.println("op");
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}