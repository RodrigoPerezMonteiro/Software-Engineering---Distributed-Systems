package pt.ist.rest.exceptions;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class restException.
 */
public class restException extends
        RuntimeException implements Serializable {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new rest exception.
     */
    public restException() {
        super();
    }

    /**
     * Instantiates a new rest exception.
     *
     * @param message the message
     */
    public restException(String message) {
        super(message);
    }

}
