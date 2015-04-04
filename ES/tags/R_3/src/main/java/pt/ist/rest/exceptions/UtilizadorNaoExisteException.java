package pt.ist.rest.exceptions;

// TODO: Auto-generated Javadoc
/**
 * The Class UtilizadorNaoExisteException.
 */
public class UtilizadorNaoExisteException extends restException {

     /**
      * The Constant serialVersionUID.
      */
     private static final long serialVersionUID = 1L;

     
     /**
      * The _nome utilizador.
      */
     private String _nomeUtilizador;
     
   
     /**
      * Instantiates a new utilizador nao existe exception.
      */
     public UtilizadorNaoExisteException() {}

     /**
      * Instantiates a new utilizador nao existe exception.
      *
      * @param msg the msg
      * @param nomeUtilizador the nome utilizador
      */
     public UtilizadorNaoExisteException(String msg, String nomeUtilizador) {
         super(msg+": "+"Utilizador: "+nomeUtilizador);
         _nomeUtilizador = nomeUtilizador;
         
     }

     /**
      * Gets the nome cliente.
      *
      * @return the nome cliente
      */
     public String getNomeCliente() {
         return this._nomeUtilizador;
     }

 }
