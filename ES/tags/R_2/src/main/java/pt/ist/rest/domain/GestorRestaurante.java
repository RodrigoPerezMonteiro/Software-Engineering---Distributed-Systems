package pt.ist.rest.domain;

// TODO: Auto-generated Javadoc
/**
 * The Class GestorRestaurante.
 */
public class GestorRestaurante extends GestorRestaurante_Base {
    
    /**
     * Instantiates a new gestor de restaurante.
     */
    public GestorRestaurante() {
        super();
    }

    /**
     * Instantiates a new gestor de restaurante with the appropriate TipoUtilizador .
     *
     * @param nome the nome
     * @param username the username
     * @param password the password
     */
    public GestorRestaurante(String nome, String username, String password) {
        set_nome(nome);
        set_password(password);
        set_username(username);
        set_tipo(1);
    }

    /**
     * Gets the gestor restaurante.
     *
     * @return the gestor restaurante
     */
    public Restaurante getGestorRestaurante() {
        return getRestaurante();
    }

    /**
     * Gere restaurante.
     *
     * @param nomeRestaurante the restaurante name
     * @return true, if he manage the restaurant
     */
    public boolean gereRestaurante(String nomeRestaurante) {
        return getRestaurante().get_nome().equals(nomeRestaurante);
    }
    
    
    /**
     * Imprime Gestor de restaurante.
     *
     * @return The restaurant's manager information
     */
    public String print() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.print());
        return stringBuilder.toString();
    }
}
