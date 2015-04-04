package pt.ist.rest.exceptions;

// TODO: Auto-generated Javadoc
/**
 * The Class RestauranteJaContemPratoException.
 */
public class RestauranteJaContemPratoException extends restException{
    
    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * The _nome restaurante.
     */
    private String _nomeRestaurante;
    
    /**
     * The _nome prato.
     */
    private String _nomePrato;
    
    
    /**
     * Instantiates a new restaurante ja contem prato exception.
     */
    public RestauranteJaContemPratoException() {
        
    }

    /**
     * Instantiates a new restaurante ja contem prato exception.
     *
     * @param msg the msg
     * @param nomeRestaurante the nome restaurante
     * @param nomePrato the nome prato
     */
    public RestauranteJaContemPratoException(String msg, String nomeRestaurante, String nomePrato) {
        super(msg+": "+"Restaurante: "+nomeRestaurante + " Prato: " + nomePrato);
        
    }

    /**
     * Gets the nome restaurante.
     *
     * @return the nome restaurante
     */
    public String getNomeRestaurante() {
        return this._nomeRestaurante;
    }

    /**
     * Gets the _nome prato.
     *
     * @return the _nome prato
     */
    public String get_nomePrato() {
        return _nomePrato;
    }
}
