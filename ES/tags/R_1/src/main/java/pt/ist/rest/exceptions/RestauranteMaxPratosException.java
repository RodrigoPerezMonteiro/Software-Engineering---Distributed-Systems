package pt.ist.rest.exceptions;

// TODO: Auto-generated Javadoc
/**
 * The Class RestauranteMaxPratosException.
 */
public class RestauranteMaxPratosException extends
        restException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The _nome restaurante. */
    private String _nomeRestaurante;

    /**
     * Instantiates a new restaurante max pratos exception.
     */
    public RestauranteMaxPratosException() {
        
    }

    /**
     * Instantiates a new restaurante max pratos exception.
     *
     * @param msg the msg
     * @param nomeRestaurante the nome restaurante
     */
    public RestauranteMaxPratosException(String msg, String nomeRestaurante) {
        super(msg +": "+nomeRestaurante);
        this._nomeRestaurante = nomeRestaurante;
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
