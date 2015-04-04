package pt.ist.rest.exceptions;

// TODO: Auto-generated Javadoc
/**
 * The Class InvalidNifException.
 */
public class InvalidNifException extends
         restException {

     /** The Constant serialVersionUID. */
     private static final long serialVersionUID = 1L;

     
     /**
      * The _username cliente.
      */
     private String _usernameCliente;
     
     /**
      * The _nif.
      */
     private int _nif;
   
     /**
      * Instantiates a new invalid nif exception.
      */
     public InvalidNifException() {
         
     }

     /**
      * Instantiates a new invalid nif exception.
      *
      * @param msg the msg
      * @param usernameCliente the username cliente
      * @param nif the nif
      */
     public InvalidNifException(String msg, String usernameCliente,int nif) {
         super(msg);
         this._nif = nif;
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
     
     /**
      * Gets the nif.
      *
      * @return the nif
      */
     public int getNif() {
         return this._nif;
     }
 }
