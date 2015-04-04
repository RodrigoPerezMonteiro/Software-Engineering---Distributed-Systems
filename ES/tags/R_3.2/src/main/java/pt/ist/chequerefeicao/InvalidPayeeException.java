package pt.ist.chequerefeicao;

public class InvalidPayeeException extends Exception {
    public InvalidPayeeException(String payee) {
	super("Invalid payee : " + payee);
    }
}
