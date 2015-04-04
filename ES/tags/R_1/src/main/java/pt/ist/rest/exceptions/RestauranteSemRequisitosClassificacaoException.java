package pt.ist.rest.exceptions;

// TODO: Auto-generated Javadoc
/**
 * The Class RestauranteSemRequisitosClassificacaoException.
 */
public class RestauranteSemRequisitosClassificacaoException extends
        restException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The _nome restaurante. */
    private String _nomeRestaurante;

    /**
     * Instantiates a new restaurante sem requisitos classificacao exception.
     */
    public RestauranteSemRequisitosClassificacaoException() {
        
    }

    /**
     * @param msg the msg
     * @param nomeRestaurante the nome restaurante
     */
    public RestauranteSemRequisitosClassificacaoException(String msg, String nomeRestaurante) {
        super(msg +": Restaurante: " + nomeRestaurante);
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
