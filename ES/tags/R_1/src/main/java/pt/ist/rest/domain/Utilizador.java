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

}
