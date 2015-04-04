package pt.ist.rest.exceptions;

// TODO: Auto-generated Javadoc
/**
 * The Class WrongPasswordException.
 */
public class WrongPasswordException  extends restException {


/**
 * The Constant serialVersionUID.
 */
private static final long serialVersionUID = 1L;

/**
 * The _username cliente.
 */
private String _usernameCliente;

/**
 * The _password.
 */
private String _password;


/**
 * Instantiates a new wrong password exception.
 */
public WrongPasswordException() {

}

/**
 * Instantiates a new wrong password exception.
 *
 * @param msg the msg
 * @param usernameCliente the username cliente
 * @param passwordCliente the password cliente
 */
public WrongPasswordException(String msg, String usernameCliente, String passwordCliente) {
super(msg);
this._usernameCliente = usernameCliente;
this._password = passwordCliente;
}

/**
 * Gets the username cliente.
 *
 * @return the username cliente
 */
public String getUsernameCliente() {
return this._usernameCliente;
}

/**
 * Gets the password cliente.
 *
 * @return the password cliente
 */
public String getPasswordCliente() {
return this._password;
}
}