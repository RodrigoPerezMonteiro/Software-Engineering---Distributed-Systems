package pt.ist.rest.exceptions;

// TODO: Auto-generated Javadoc
/**
 * The Class ClienteJaExisteException.
 */
public class ClienteJaExisteException extends
        restException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The _username cliente. */
    private String _usernameCliente;
    
    /**
     * Instantiates a new cliente ja existe exception.
     */
    public ClienteJaExisteException() {
        
    }

    /**
     * Instantiates a new cliente ja existe exception.
     *
     * @param msg the msg
     * @param usernameCliente the username cliente
     */
    public ClienteJaExisteException(String msg, String usernameCliente) {
        super(msg);
        this._usernameCliente = usernameCliente;
    }

    /**
     * Gets the username cliente.
     *
     * @return the username cliente
     */
    public String getUsernameCliente() {
        return this._usernameCliente;
    }
}
