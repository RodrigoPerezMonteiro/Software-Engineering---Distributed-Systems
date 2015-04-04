
package pt.chequerefeicao.ws;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "ChequeNaoEndossavel", targetNamespace = "urn:pt:chequerefeicao:ws")
public class ChequeNaoEndossavel_Exception
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private ChequeNaoEndossavel faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public ChequeNaoEndossavel_Exception(String message, ChequeNaoEndossavel faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public ChequeNaoEndossavel_Exception(String message, ChequeNaoEndossavel faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: pt.chequerefeicao.ws.ChequeNaoEndossavel
     */
    public ChequeNaoEndossavel getFaultInfo() {
        return faultInfo;
    }

}
