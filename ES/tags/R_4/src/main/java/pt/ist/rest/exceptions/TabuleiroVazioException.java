package pt.ist.rest.exceptions;


public class TabuleiroVazioException extends
        restException {


    private static final long serialVersionUID = 1L;

    private String _nomeCliente;


    public TabuleiroVazioException() {

    }

    public TabuleiroVazioException(String msg, String nomeCliente) {
        super(msg + ": Cliente: " + nomeCliente);
    }



    public String getNomeCliente() {
        return this._nomeCliente;
    }

}
