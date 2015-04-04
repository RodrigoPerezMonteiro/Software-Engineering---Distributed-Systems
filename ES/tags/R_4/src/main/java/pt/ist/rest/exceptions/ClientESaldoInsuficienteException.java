package pt.ist.rest.exceptions;

// TODO: Auto-generated Javadoc
/**
 * The Class ClientESaldoInsuficienteException.
 */
public class ClientESaldoInsuficienteException extends restException  {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

     /**
      * The _nome cliente.
      */
     private String _nomeCliente;
     
     /**
      * The _saldo.
      */
     private Double _saldo;
     
     /**
      * Instantiates a new client e saldo insuficiente exception.
      */
     public ClientESaldoInsuficienteException() {}

     /**
      * Instantiates a new client e saldo insuficiente exception.
      *
      * @param msg the msg
      * @param nomeCliente the nome cliente
      * @param saldo the saldo
      */
     public ClientESaldoInsuficienteException(String msg, String nomeCliente, Double saldo) {
         super(msg+": "+"Cliente: "+nomeCliente+" Saldo: " + saldo);
         _nomeCliente = nomeCliente;
         _saldo = saldo;
     }
     
     /**
      * Gets the _nome cliente.
      *
      * @return the _nome cliente
      */
     public String get_nomeCliente() {
         return _nomeCliente;
     }
     
     /**
      * Gets the _saldo.
      *
      * @return the _saldo
      */
     public Double get_saldo() {
         return _saldo;
     }
}
