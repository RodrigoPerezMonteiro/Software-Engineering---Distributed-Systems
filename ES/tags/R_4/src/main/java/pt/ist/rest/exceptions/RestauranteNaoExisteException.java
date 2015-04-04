package pt.ist.rest.exceptions;

// TODO: Auto-generated Javadoc
/**
 * The Class RestauranteNaoExisteException.
 */
public class RestauranteNaoExisteException extends restException {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * The _nome restaurante.
     */
    private String _nomeRestaurante;
    
    /**
     * Instantiates a new restaurante nao existe exception.
     */
    public RestauranteNaoExisteException() {
        
    }

    /**
     * Instantiates a new restaurante nao existe exception.
     *
     * @param msg the msg
     * @param nomeRestaurante the nome restaurante
     */
    public RestauranteNaoExisteException(String msg, String nomeRestaurante) {
        super(msg+": "+"Restaurante: "+nomeRestaurante);
        
    }

    /**
     * Gets the nome restaurante.
     *
     * @return the nome restaurante
     */
    public String getNomeRestaurante() {
        return this._nomeRestaurante;
    }

}
