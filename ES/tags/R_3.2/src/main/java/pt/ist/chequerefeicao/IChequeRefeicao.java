package pt.ist.chequerefeicao;

import java.util.List;

public interface IChequeRefeicao {

    public int cashChecks(String payee, List<String> checks) throws InvalidCheckException,
            CheckAlreadyUsedException;
}
