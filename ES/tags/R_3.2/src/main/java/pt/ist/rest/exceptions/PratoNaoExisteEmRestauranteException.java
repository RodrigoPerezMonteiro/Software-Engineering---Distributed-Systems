package pt.ist.rest.exceptions;

// TODO: Auto-generated Javadoc
/**
 * The Class PratoNaoExisteException.
 */
public class PratoNaoExisteEmRestauranteException extends restException {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The _nome prato.
     */
    private String _nomePrato;
    
    /**
     * The _nome restaurante.
     */
    private String _nomeRestaurante;
    private int _idPrato;
    
  
    /**
     * Instantiates a new prato nao existe exception.
     */
    public PratoNaoExisteEmRestauranteException() {
        
    }

    /**
     * Instantiates a new prato nao existe exception.
     *
     * @param msg the msg
     * @param nomePrato the nome prato
     * @param nomeRestaurante the nome restaurante
     */
    public PratoNaoExisteEmRestauranteException(String msg, String nomePrato, String nomeRestaurante) {
        super(msg+": "+"Prato: "+nomePrato+" Restaurante: "+nomeRestaurante);
        _nomePrato = nomePrato;
        _nomeRestaurante = nomeRestaurante;
        
    }
    
    public PratoNaoExisteEmRestauranteException(String msg, int idPrato, String nomeRestaurante) {
        super(msg+": "+"Prato Id: "+idPrato+" Restaurante: "+nomeRestaurante);
        _idPrato = idPrato;
        _nomeRestaurante = nomeRestaurante;
    }

    /**
     * Gets the nome prato.
     *
     * @return the nome prato
     */
    public String getNomePrato() {
        return this._nomePrato;
    }
    
    /**
     * Gets the nome restaurante.
     *
     * @return the nome restaurante
     */
    public String getNomeRestaurante() {
        return this._nomeRestaurante;
    }

    public int getIdPrato() {
        return this._idPrato;
    }
}
