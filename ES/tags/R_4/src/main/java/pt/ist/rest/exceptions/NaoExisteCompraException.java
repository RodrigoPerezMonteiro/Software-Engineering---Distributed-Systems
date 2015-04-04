package pt.ist.rest.exceptions;

// TODO: Auto-generated Javadoc
/**
 * The Class NaoExisteCompra.
 */
public class NaoExisteCompraException extends
        restException {


    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The _id compra.
     */
    private int _idCompra;


    /**
     * Instantiates a new nao existe compra.
     */
    public NaoExisteCompraException() {

    }

    /**
     * Instantiates a new nao existe compra.
     *
     * @param msg the msg
     * @param idcompra the idcompra
     */
    public NaoExisteCompraException(String msg, int idcompra) {
        super(msg + ": Cliente: " + idcompra);
        this._idCompra = idcompra;
    }



    /**
     * Gets the id compra.
     *
     * @return the id compra
     */
    public int getIdCompra() {
        return this._idCompra;
    }

}
