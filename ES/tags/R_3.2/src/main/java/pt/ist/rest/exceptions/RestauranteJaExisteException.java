package pt.ist.rest.exceptions;

// TODO: Auto-generated Javadoc
/**
 * The Class RestauranteJaExisteException.
 */
public class RestauranteJaExisteException extends
        restException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The _nome restaurante. */
    private String _nomeRestaurante;

    /**
     * Instantiates a new restaurante ja existe exception.
     */
    public RestauranteJaExisteException() {
        
    }

    /**
     * Instantiates a new restaurante ja existe exception.
     *
     * @param msg the msg
     * @param nomeRestaurante the nome restaurante
     */
    public RestauranteJaExisteException(String msg, String nomeRestaurante) {
        super(msg+": "+nomeRestaurante);
        
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
