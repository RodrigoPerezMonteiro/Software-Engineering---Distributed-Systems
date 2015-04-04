package pt.ist.rest.exceptions;

// TODO: Auto-generated Javadoc
/**
 * The Class ClienteJaGostaPratoException.
 */
public class ClienteJaGostaPratoException extends restException {

  
    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;


    /**
     * The _nome cliente.
     */
    private String _nomeCliente;
    
    /**
     * The _nome prato.
     */
    private String _nomePrato;
    
  
    /**
     * Instantiates a new cliente ja gosta prato exception.
     */
    public ClienteJaGostaPratoException() {
        
    }

  
    /**
     * Instantiates a new cliente ja gosta prato exception.
     *
     * @param msg the msg
     * @param nomeCliente the nome cliente
     * @param nomePrato the nome prato
     */
    public ClienteJaGostaPratoException(String msg, String nomeCliente, String nomePrato) {
        super(msg+": "+"Cliente: " +nomeCliente+" Prato: "+nomePrato);
        _nomeCliente = nomeCliente;
        _nomePrato = nomePrato;
        
    }

    /**
     * Gets the nome cliente.
     *
     * @return the nome cliente
     */
    public String getNomeCliente() {
        return this._nomeCliente;
    }
    
    /**
     * Gets the nome prato.
     *
     * @return the nome prato
     */
    public String getNomePrato() {
        return this._nomePrato;
    }

}
