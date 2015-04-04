package pt.ist.rest.exceptions;

public class GestorAdicionaItemException  extends restException {



     /** The Constant serialVersionUID. */
     private static final long serialVersionUID = 1L;

     /**
      * The _nome gestor.
      */
     private String _nomeGestor;

    
     public GestorAdicionaItemException() {
         
     }

     public GestorAdicionaItemException(String msg,String usernameGestor) {
         super(msg+": " + "Gestor: "+usernameGestor);
         this._nomeGestor = usernameGestor;
     }

     /**
      * Gets the nome gestor.
      *
      * @return the nome gestor
      */
     public String getNomeGestor() {
         return this._nomeGestor;
     }
 }
