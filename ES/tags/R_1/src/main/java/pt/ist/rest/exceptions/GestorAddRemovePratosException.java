package pt.ist.rest.exceptions;

// TODO: Auto-generated Javadoc
/**
* Só o gestor de um restaurante é que pode adicionar e remover pratos do restaurante que gere. 
*/

public class GestorAddRemovePratosException extends
        restException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The _nome restaurante. */
    private String _nomeRestaurante;
    
    /**
     * The _nome gestor.
     */
    private String _nomeGestor;

    /**
     * Instantiates a new gestor add remove pratos exception.
     */
    public GestorAddRemovePratosException() {
        
    }

    /**
     * Instantiates a new gestor add remove pratos exception.
     *
     * @param msg the msg
     * @param nomeGestor the nome gestor
     * @param nomeRestaurante the nome restaurante
     */
    public GestorAddRemovePratosException(String msg,String nomeGestor, String nomeRestaurante) {
        super(msg+": " + "Gestor: "+nomeGestor+" Restaurante: " + nomeRestaurante);
        this._nomeRestaurante = nomeRestaurante;
        this._nomeGestor = nomeGestor;
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
     * Gets the nome gestor.
     *
     * @return the nome gestor
     */
    public String getNomeGestor() {
        return this._nomeGestor;
    }
}
