package pt.ist.rest.exceptions;

public class PratoNaoExisteException extends restException {

    private static final long serialVersionUID = 1L;
    
    /**
     * The _nome prato.
     */
    private String _nomePrato;


    /**
     * Instantiates a new prato nao existe exception.
     */
    public PratoNaoExisteException() {}


    public PratoNaoExisteException(String msg, String nomePrato) {
        super(msg + ": Prato: " + nomePrato);
        _nomePrato = nomePrato;

    }

    public String getNomePrato() {
        return this._nomePrato;
    }

}
