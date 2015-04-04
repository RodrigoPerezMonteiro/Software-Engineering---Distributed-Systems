package pt.ist.rest.domain;


// TODO: Auto-generated Javadoc
/**
 * The Class Utilizador.
 */
public class Utilizador extends
        Utilizador_Base {

    /**
     * Instantiates a new utilizador.
     */
    public Utilizador() {
        super();
    }

    /**
     * Gets the nome.
     * 
     * @return the nome
     */
    public String getNome() {
        return get_nome();
    }

    /**
     * Gets the username.
     * 
     * @return the username
     */
    public String getUsername() {
        return get_username();
    }

    /**
     * Gets the password.
     * 
     * @return the password
     */
    public String getPassword() {
        return get_password();
    }

    /**
     * Gets the tipo utilizador.
     * 
     * @return the tipo utilizador: 0 - Cliente || 1 - Gestor
     */
    public Integer getTipoUtilizador() {
        return get_tipo();
    }

    /**
     * Prints the.
     *
     * @return the string
     */
    public String print() {
        StringBuilder stringBuilder = new StringBuilder();
        if (getTipoUtilizador() == 0) {
            stringBuilder.append("\nCliente: \n");
        } else {
            stringBuilder.append("\nGestor: \n");
        }
        stringBuilder.append("\n\tusername = " + getUsername() + "\n\tname = " + getNome()
                + "\n\tpassword = " + getPassword());

        return stringBuilder.toString();
    }

    /* (non-Javadoc)
     * @see pt.ist.rest.domain.Utilizador_Base#setPortalRestaurante(pt.ist.rest.domain.PortalRestaurante)
     */
    @Override
    public void setPortalRestaurante(PortalRestaurante portal) {
        portal.addUtilizador(this);
    }

    /**
     * Checks if is client.
     *
     * @return true, if is client
     */
    public boolean isCliente() {
        if (getTipoUtilizador() == 1) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * Checks if is gestor.
     *
     * @return true, if is gestor
     */
    public boolean isGestor() {
        if (getTipoUtilizador() == 0) {
            return false;
        } else {
            return true;
        }
    }

}
