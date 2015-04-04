package pt.ist.rest.exceptions;

// TODO: Auto-generated Javadoc
/**
 * The Class GestorJaGereRestauranteException.
 */
public class GestorJaGereRestauranteException extends restException {
    
    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * The _nome gestor.
     */
    private String _nomeGestor;
    
    /**
     * The _nome restaurante.
     */
    private String _nomeRestaurante;

    
    /**
     * Instantiates a new gestor ja gere restaurante exception.
     */
    public GestorJaGereRestauranteException() {
        
    }

    /**
     * Instantiates a new gestor ja gere restaurante exception.
     *
     * @param msg the msg
     * @param nomeGestor the nome gestor
     * @param nomeRestaurante the nome restaurante
     */
    public GestorJaGereRestauranteException(String msg,String nomeGestor, String nomeRestaurante) {
        super(msg+": "+"Gestor: "+nomeGestor+" Restaurante: "+nomeRestaurante);
        _nomeGestor = nomeGestor;
        _nomeRestaurante = nomeRestaurante;
    }

    /**
     * Gets the nome gestor.
     *
     * @return the nome gestor
     */
    public String getNomeGestor() {
        return this._nomeGestor;
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
