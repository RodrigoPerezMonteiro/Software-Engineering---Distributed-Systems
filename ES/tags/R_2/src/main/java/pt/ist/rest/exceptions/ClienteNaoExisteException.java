package pt.ist.rest.exceptions;

// TODO: Auto-generated Javadoc
/**
 * The Class ClienteNaoExisteException.
 */
public class ClienteNaoExisteException extends restException {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The _nome cliente.
     */
    private String _nomeCliente;
    
  
    /**
     * Instantiates a new cliente nao existe exception.
     */
    public ClienteNaoExisteException() {
        
    }

    /**
     * Instantiates a new cliente nao existe exception.
     *
     * @param msg the msg
     * @param nomeCliente the nome cliente
     */
    public ClienteNaoExisteException(String msg, String nomeCliente) {
        super(msg+": "+"Cliente: "+nomeCliente);
        _nomeCliente = nomeCliente;
        
    }

    /**
     * Gets the nome cliente.
     *
     * @return the nome cliente
     */
    public String getNomeCliente() {
        return this._nomeCliente;
    }

}
