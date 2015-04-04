package pt.ist.chequerefeicao;

public class CheckAlreadyUsedException extends Exception {
    public CheckAlreadyUsedException(String checkNumber) {
	super("Check already registered: " + checkNumber);
    }
}
