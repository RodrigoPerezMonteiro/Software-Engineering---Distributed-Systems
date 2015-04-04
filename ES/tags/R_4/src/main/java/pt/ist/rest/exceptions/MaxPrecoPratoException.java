package pt.ist.rest.exceptions;

// TODO: Auto-generated Javadoc
/**
 * The Class MaxPrecoPratoException.
 */
public class MaxPrecoPratoException extends restException {

/**
 * The Constant serialVersionUID.
 */
private static final long serialVersionUID = 1L;


/**
 * The _nome prato.
 */
private String _nomePrato;

/**
 * The _preco prato.
 */
private double _precoPrato;

/**
 * The _preco maximo.
 */
private double _precoMaximo;

/**
 * Instantiates a new max preco prato exception.
 */
public MaxPrecoPratoException() {
    
}

/**
 * Instantiates a new max preco prato exception.
 *
 * @param msg the msg
 * @param precoMaximo the preco maximo
 * @param nomePrato the nome prato
 * @param precoPrato the preco prato
 */
public MaxPrecoPratoException(String msg, double precoMaximo, String nomePrato, double precoPrato) {
    super(msg +": Prato: "+nomePrato+" Preco do Prato: "+precoPrato+" Preco Maximo: "+precoMaximo);
    this._precoMaximo = precoMaximo;
    this._nomePrato = nomePrato;
    this._precoPrato = precoPrato;
}

/**
 * Gets the _nome prato.
 *
 * @return the _nome prato
 */
public String get_nomePrato() {
    return _nomePrato;
}

/**
 * Gets the _preco prato.
 *
 * @return the _preco prato
 */
public double get_precoPrato() {
    return _precoPrato;
}

/**
 * Gets the _preco maximo.
 *
 * @return the _preco maximo
 */
public double get_precoMaximo() {
    return _precoMaximo;
}

}
