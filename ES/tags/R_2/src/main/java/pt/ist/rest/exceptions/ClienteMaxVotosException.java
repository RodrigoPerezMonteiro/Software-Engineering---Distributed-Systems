package pt.ist.rest.exceptions;

// TODO: Auto-generated Javadoc
/**
 * The Class ClienteMaxVotosException.
 * 
 *  Cada cliente pode gostar ate um maximo de 15 pratos. Um cliente pode alterar os
 *  pratos de que gosta em qualquer momento (i.e. adicionando novos ou removendo
 *  antigos).
 */
public class ClienteMaxVotosException extends restException {
  
    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The _nome cliente.
     */
    private String _nomeCliente;
    

    /**
     * Instantiates a new cliente max votos exception.
     */
    public ClienteMaxVotosException() {
        
    }
  
    /**
     * Instantiates a new cliente max votos exception.
     *
     * @param msg the msg
     * @param nomeCliente the nome cliente
     */
    public ClienteMaxVotosException(String msg, String nomeCliente) {
        super(msg+": "+"Cliente: "+nomeCliente);
        _nomeCliente = nomeCliente;
    }
    
    /**
     * Gets the _nome cliente.
     *
     * @return the _nome cliente
     */
    public String get_nomeCliente() {
        return _nomeCliente;
    }

}
