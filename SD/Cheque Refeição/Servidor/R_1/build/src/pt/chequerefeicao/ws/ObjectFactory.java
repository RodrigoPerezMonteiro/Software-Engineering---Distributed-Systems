
package pt.chequerefeicao.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the pt.chequerefeicao.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ChequeJaUsado_QNAME = new QName("urn:pt:chequerefeicao:ws", "ChequeJaUsado");
    private final static QName _ListarResponse_QNAME = new QName("urn:pt:chequerefeicao:ws", "listarResponse");
    private final static QName _ChequeNaoEndossavel_QNAME = new QName("urn:pt:chequerefeicao:ws", "ChequeNaoEndossavel");
    private final static QName _UtilizadorInexistente_QNAME = new QName("urn:pt:chequerefeicao:ws", "UtilizadorInexistente");
    private final static QName _Emitir_QNAME = new QName("urn:pt:chequerefeicao:ws", "emitir");
    private final static QName _ChequeInexistente_QNAME = new QName("urn:pt:chequerefeicao:ws", "ChequeInexistente");
    private final static QName _ValorInvalido_QNAME = new QName("urn:pt:chequerefeicao:ws", "ValorInvalido");
    private final static QName _Endossar_QNAME = new QName("urn:pt:chequerefeicao:ws", "endossar");
    private final static QName _Sacar_QNAME = new QName("urn:pt:chequerefeicao:ws", "sacar");
    private final static QName _EndossarResponse_QNAME = new QName("urn:pt:chequerefeicao:ws", "endossarResponse");
    private final static QName _Listar_QNAME = new QName("urn:pt:chequerefeicao:ws", "listar");
    private final static QName _EmitirResponse_QNAME = new QName("urn:pt:chequerefeicao:ws", "emitirResponse");
    private final static QName _SacarResponse_QNAME = new QName("urn:pt:chequerefeicao:ws", "sacarResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: pt.chequerefeicao.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ChequeInexistente }
     * 
     */
    public ChequeInexistente createChequeInexistente() {
        return new ChequeInexistente();
    }

    /**
     * Create an instance of {@link ValorInvalido }
     * 
     */
    public ValorInvalido createValorInvalido() {
        return new ValorInvalido();
    }

    /**
     * Create an instance of {@link ChequeJaUsado }
     * 
     */
    public ChequeJaUsado createChequeJaUsado() {
        return new ChequeJaUsado();
    }

    /**
     * Create an instance of {@link ListarResponse }
     * 
     */
    public ListarResponse createListarResponse() {
        return new ListarResponse();
    }

    /**
     * Create an instance of {@link ChequeNaoEndossavel }
     * 
     */
    public ChequeNaoEndossavel createChequeNaoEndossavel() {
        return new ChequeNaoEndossavel();
    }

    /**
     * Create an instance of {@link Endossar }
     * 
     */
    public Endossar createEndossar() {
        return new Endossar();
    }

    /**
     * Create an instance of {@link Sacar }
     * 
     */
    public Sacar createSacar() {
        return new Sacar();
    }

    /**
     * Create an instance of {@link UtilizadorInexistente }
     * 
     */
    public UtilizadorInexistente createUtilizadorInexistente() {
        return new UtilizadorInexistente();
    }

    /**
     * Create an instance of {@link EndossarResponse }
     * 
     */
    public EndossarResponse createEndossarResponse() {
        return new EndossarResponse();
    }

    /**
     * Create an instance of {@link Listar }
     * 
     */
    public Listar createListar() {
        return new Listar();
    }

    /**
     * Create an instance of {@link EmitirResponse }
     * 
     */
    public EmitirResponse createEmitirResponse() {
        return new EmitirResponse();
    }

    /**
     * Create an instance of {@link SacarResponse }
     * 
     */
    public SacarResponse createSacarResponse() {
        return new SacarResponse();
    }

    /**
     * Create an instance of {@link Emitir }
     * 
     */
    public Emitir createEmitir() {
        return new Emitir();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChequeJaUsado }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:pt:chequerefeicao:ws", name = "ChequeJaUsado")
    public JAXBElement<ChequeJaUsado> createChequeJaUsado(ChequeJaUsado value) {
        return new JAXBElement<ChequeJaUsado>(_ChequeJaUsado_QNAME, ChequeJaUsado.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListarResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:pt:chequerefeicao:ws", name = "listarResponse")
    public JAXBElement<ListarResponse> createListarResponse(ListarResponse value) {
        return new JAXBElement<ListarResponse>(_ListarResponse_QNAME, ListarResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChequeNaoEndossavel }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:pt:chequerefeicao:ws", name = "ChequeNaoEndossavel")
    public JAXBElement<ChequeNaoEndossavel> createChequeNaoEndossavel(ChequeNaoEndossavel value) {
        return new JAXBElement<ChequeNaoEndossavel>(_ChequeNaoEndossavel_QNAME, ChequeNaoEndossavel.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UtilizadorInexistente }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:pt:chequerefeicao:ws", name = "UtilizadorInexistente")
    public JAXBElement<UtilizadorInexistente> createUtilizadorInexistente(UtilizadorInexistente value) {
        return new JAXBElement<UtilizadorInexistente>(_UtilizadorInexistente_QNAME, UtilizadorInexistente.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Emitir }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:pt:chequerefeicao:ws", name = "emitir")
    public JAXBElement<Emitir> createEmitir(Emitir value) {
        return new JAXBElement<Emitir>(_Emitir_QNAME, Emitir.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChequeInexistente }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:pt:chequerefeicao:ws", name = "ChequeInexistente")
    public JAXBElement<ChequeInexistente> createChequeInexistente(ChequeInexistente value) {
        return new JAXBElement<ChequeInexistente>(_ChequeInexistente_QNAME, ChequeInexistente.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValorInvalido }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:pt:chequerefeicao:ws", name = "ValorInvalido")
    public JAXBElement<ValorInvalido> createValorInvalido(ValorInvalido value) {
        return new JAXBElement<ValorInvalido>(_ValorInvalido_QNAME, ValorInvalido.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Endossar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:pt:chequerefeicao:ws", name = "endossar")
    public JAXBElement<Endossar> createEndossar(Endossar value) {
        return new JAXBElement<Endossar>(_Endossar_QNAME, Endossar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Sacar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:pt:chequerefeicao:ws", name = "sacar")
    public JAXBElement<Sacar> createSacar(Sacar value) {
        return new JAXBElement<Sacar>(_Sacar_QNAME, Sacar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EndossarResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:pt:chequerefeicao:ws", name = "endossarResponse")
    public JAXBElement<EndossarResponse> createEndossarResponse(EndossarResponse value) {
        return new JAXBElement<EndossarResponse>(_EndossarResponse_QNAME, EndossarResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Listar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:pt:chequerefeicao:ws", name = "listar")
    public JAXBElement<Listar> createListar(Listar value) {
        return new JAXBElement<Listar>(_Listar_QNAME, Listar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmitirResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:pt:chequerefeicao:ws", name = "emitirResponse")
    public JAXBElement<EmitirResponse> createEmitirResponse(EmitirResponse value) {
        return new JAXBElement<EmitirResponse>(_EmitirResponse_QNAME, EmitirResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SacarResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:pt:chequerefeicao:ws", name = "sacarResponse")
    public JAXBElement<SacarResponse> createSacarResponse(SacarResponse value) {
        return new JAXBElement<SacarResponse>(_SacarResponse_QNAME, SacarResponse.class, null, value);
    }

}
