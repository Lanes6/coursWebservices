package fr.miage.orleans.wsi.clientserviceauthentifie.proxyservice.exceptions;

public class BadTokenException extends Exception {
    String uri;

    public BadTokenException(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }
}
