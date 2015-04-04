package pt.ist.rest.exceptions;

public class NaoExisteTabuleiroException extends
        restException {


    private static final long serialVersionUID = 1L;

    private String _nomeCliente;


    public NaoExisteTabuleiroException() {

    }

    public NaoExisteTabuleiroException(String msg, String nomeCliente) {
        super(msg + ": Cliente: " + nomeCliente);
    }



    public String getNomeCliente() {
        return this._nomeCliente;
    }

}
