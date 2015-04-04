package pt.ist.chequerefeicao;

/**
 * This class represents a simplified local version of the external service ChequeRefeicao.
 * Each check is represented by a string.
 * The simplifications made are the following ones:
 * - The data concerning the service ChequeRefeicao is not persistent
 * - It just support the operation of cashing checks.
 * - In order to be able to simulate valid and invalid checks, a valid check has an identifier
 *   that ends with a digit.
 * - The amount of a check is equal to 10 times the last digit of the identifier of the check.
 *
 * @author ES
 **/

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class ChequeRefeicaoLocal implements IChequeRefeicao {
    private Set<String> cashed;

    public ChequeRefeicaoLocal() {
	this.cashed = new HashSet<String>();
    }

    /**
     * Cash a list of checks. A check can only be cashed once. Returns
     * the amount of money represented by all checks. The checks are cashed only
     * if all of them are valid.
     *
     * @param payee the recipient of the money
     * @param checks the list of checks to register
     *
     * @return the amount of money represented by the list of checks to cash.
     *
     * @throws InvalidCheckException if there is at least one check that is not a valid one.
     * @throws CheckAlreadyUsedException if there is at least one check that was registered previously.
     * @throws InvalidPayeeException if payee is not the same that was assigned to the checks. In this
     *         simple implementation, this is not being verified.
     **/
    public int cashChecks(String payee, List<String> checks) throws InvalidCheckException, CheckAlreadyUsedException {
	int amount = 0;
	char lastChar;

	for (String check : checks) {
	    if (cashed.contains(check))
		throw new CheckAlreadyUsedException(check);

	    lastChar = check.charAt(check.length() - 1); // last character

	    if (lastChar < '0' || lastChar > '9')
		throw new InvalidCheckException(check);

	    amount += (lastChar - '0') * 10;
	}

	cashed.addAll(checks);
	return amount;
    }

    /**
     * A simple set of tests for this class.
     **/
    public static void main(String argv[]) {
	ChequeRefeicaoLocal chequeRefeicao = new ChequeRefeicaoLocal();
	List<String> checks = new ArrayList<String>();
	int amount;

	try {
	    checks.add("4");
	    checks.add("6");
	    checks.add("2");
	    amount = chequeRefeicao.cashChecks("me", checks);
	    if (amount != 120)
		System.out.println("####### Amount registered: " + amount + ". Should be 120" );
	    else
		System.out.println("Registry of valid checks made with success!" );
	} catch (InvalidCheckException ice) {
	    System.out.println("Could not make valid registry of checks! " + ice);
	} catch (CheckAlreadyUsedException cae) {
	    System.out.println("Could not make valid registry of checks!" + cae);
	}

	try {
	    checks.clear();
	    checks.add("5");
	    checks.add("2");
	    amount = chequeRefeicao.cashChecks("you", checks);
	    System.out.println("####### Error. Accepted a check registered previously: "  + amount);
	} catch (InvalidCheckException ice) {
	    System.out.println("####### Wrong exception: "  + ice);
	} catch (CheckAlreadyUsedException cae) {
	    System.out.println("Did not allow to use the same (2) check twice: " + cae);
	}

	try {
	    checks.clear();
	    checks.add("9");
	    checks.add("aa");
	    checks.add("11");
		       amount = chequeRefeicao.cashChecks("he", checks);
	    System.out.println("####### Error. Accepted an invalid: "  + amount);
	} catch (InvalidCheckException ice) {
	    System.out.println("Did not allow to use an invalid check: " + ice);
	} catch (CheckAlreadyUsedException cae) {
	    System.out.println("####### Wrong exception: "  + cae);
	}
    }
}
