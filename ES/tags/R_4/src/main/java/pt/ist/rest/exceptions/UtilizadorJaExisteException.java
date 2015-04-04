package pt.ist.rest.exceptions;

 // TODO: Auto-generated Javadoc
/**
  * The Class UtilizadorJaExisteException.
  */
 public class UtilizadorJaExisteException extends
         restException {

     /**
      * The Constant serialVersionUID.
      */
     private static final long serialVersionUID = 1L;

     /**
      * The _username.
      */
     private String _username;
     
     /**
      * Instantiates a new utilizador ja existe exception.
      */
     public UtilizadorJaExisteException() {
         
     }

     /**
      * Instantiates a new utilizador ja existe exception.
      *
      * @param msg the msg
      * @param usernameUser the username user
      */
     public UtilizadorJaExisteException(String msg, String usernameUser) {
         super(msg);
         this._username = usernameUser;
     }

     /**
      * Gets the username cliente.
      *
      * @return the username cliente
      */
     public String getUsernameCliente() {
         return this._username;
     }
 }

