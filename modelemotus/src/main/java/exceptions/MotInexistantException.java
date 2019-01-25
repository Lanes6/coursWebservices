package exceptions;

/**
 * Created by Fred on 02/06/2016.
 */
public class MotInexistantException extends Exception {
    public MotInexistantException(String mot) {
        super("mot inconnu "+mot);
    }
}
