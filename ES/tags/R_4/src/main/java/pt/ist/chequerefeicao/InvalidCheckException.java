package pt.ist.chequerefeicao;

public class InvalidCheckException extends Exception {
    public InvalidCheckException(String checkNumber) {
	super("Invalid check number: " + checkNumber);
    }
}
