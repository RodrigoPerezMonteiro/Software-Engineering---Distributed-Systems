package pt.chequerefeicao.ws.handler;

import java.util.Iterator;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.*;

import static javax.xml.bind.DatatypeConverter.printBase64Binary;
import static javax.xml.bind.DatatypeConverter.parseBase64Binary;
import static javax.xml.bind.DatatypeConverter.printHexBinary;

import java.io.*;

import javax.xml.soap.*;
import javax.xml.namespace.QName;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.w3c.dom.*;

import pt.chequerefeicao.crypto.AsymCrypto;
import pt.chequerefeicao.crypto.SymCrypto;
import pt.chequerefeicao.ws.generatekeys.DigitalSignature;
import pt.chequerefeicao.ws.impl.ChequeRefeicaoMain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;

import javax.xml.bind.DatatypeConverter;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


/**
 * 	This SOAPHandler outputs the contents of 
 * 	inbound and outbound messages.
 */
public class Handler implements SOAPHandler<SOAPMessageContext> {

	private String username;



	public Set<QName> getHeaders() {
		return null;
	}

	public boolean handleMessage(SOAPMessageContext smc) {
		Boolean outbound = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (outbound) {
			System.out.println("Outbound SOAP message1:");
			encriptar(smc);
		} else {
			System.out.println("Inbound SOAP message1:");
			try {
				decifra(smc);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean handleFault(SOAPMessageContext smc) {
		return true;
	}

	// nothing to clean up
	public void close(MessageContext messageContext) {
	}


	private static byte[] SOAPMessageToByteArray(SOAPMessage msg) throws Exception {
		ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
		byte[] msgByteArray = null;

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();

		Source source = msg.getSOAPPart().getContent();
		Result result = new StreamResult(byteOutStream);
		transformer.transform(source, result);

		msgByteArray = byteOutStream.toByteArray();
		return msgByteArray;
	}

	private static SOAPMessage byteArrayToSOAPMessage(byte[] msg) throws Exception {
		ByteArrayInputStream byteInStream = new ByteArrayInputStream(msg);
		StreamSource source = new StreamSource(byteInStream);
		SOAPMessage newMsg = null;

		MessageFactory mf = MessageFactory.newInstance();
		newMsg = mf.createMessage();
		SOAPPart soapPart = newMsg.getSOAPPart();
		soapPart.setContent(source);

		return newMsg;
	}


	private void encriptar(SOAPMessageContext smc) {
		try {

			SOAPMessage soapMessage = smc.getMessage();
			SOAPPart soapPart = soapMessage.getSOAPPart();
			SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
			SOAPBody soapBody = soapEnvelope.getBody();
			SOAPHeader soapHeader = soapEnvelope.getHeader();
			SOAPElement el = null;
			
			if(soapHeader == null){
				soapHeader = soapEnvelope.addHeader();
			}

			Iterator i = soapBody.getChildElements();
			if(i.hasNext()){
				el = (SOAPElement) i.next();
			}

			Name n = soapEnvelope.createName("cabecalho","ns","urn:cabecalho");
			SOAPHeaderElement soaphe = soapHeader.addHeaderElement(n);

			soapMessage.writeTo(System.out);
			
			soaphe.addTextNode(username);
		
			byte[] messageBytes = SOAPMessageToByteArray(soapMessage);

			KeyGenerator kg = KeyGenerator.getInstance("AES");
			kg.init(128);
			SecretKey sk = kg.generateKey();
		
			byte[] mensagemCifrada = SymCrypto.cifrar(messageBytes, sk);

			String mensagemCifrada64 = printBase64Binary(mensagemCifrada);
		
			SOAPElement elem = null;

			i = soapBody.getChildElements();
			if(i.hasNext()){
				elem = (SOAPElement)i.next();
			}

			soapBody.detachNode(); // tirar body antigo.
			soapBody = soapEnvelope.addBody();
			
			SOAPBodyElement elem2 = null;
			
			if (elem.getTagName().equals("ns2:emitirResponse")) 
			{
				elem2 = soapBody.addBodyElement(soapEnvelope.createName("emitirResponse", "ns2",
						"urn:pt:chequerefeicao:ws"));
				System.out.println("ELEM ");
			} else if (elem.getTagName().equals("ns2:endossarResponse")) 
			{
				elem2 = soapBody.addBodyElement(soapEnvelope.createName("endossarResponse", "ns2",
						"urn:pt:chequerefeicao:ws"));
				System.out.println("ELEM ");
			} else if (elem.getTagName().equals("ns2:sacarResponse")) 
			{
				elem2 = soapBody.addBodyElement(soapEnvelope.createName("sacarResponse", "ns2",
						"urn:pt:chequerefeicao:ws"));
				System.out.println("ELEM ");
			} 
			
			elem2.addTextNode(mensagemCifrada64);	
			PrivateKey priv = ChequeRefeicaoMain.getCRPrivKey();
			byte[] signature = DigitalSignature.makeDigitalSignature(messageBytes, priv);
			
			String signature64 = printBase64Binary(signature);
			Name n1 = soapEnvelope.createName("digSig", "ns", "urn:digSig");
			SOAPHeaderElement elem3 = soapHeader.addHeaderElement(n1);
			elem3.addTextNode(signature64);
			
			byte[] chaveCifrada = AsymCrypto.cifrar(sk.getEncoded(), username);
			String chaveCifrada64 = printBase64Binary(chaveCifrada);
			
			n1 = soapEnvelope.createName("chave", "ns", "urn:chave");
			SOAPHeaderElement elem4 = soapHeader.addHeaderElement(n1);
			elem4.addTextNode(chaveCifrada64);
			
			System.out.println("Mensagem:");
			soapMessage = smc.getMessage();
			soapMessage.writeTo(System.out);

		} catch (Exception e){
			System.out.printf("Exception in handler: %s%n", e);
		}
	}


	public void decifra(SOAPMessageContext smc) throws Exception{
		SOAPMessage soapMessage = smc.getMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();
		SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
		SOAPBody soapBody = soapEnvelope.getBody();
		SOAPHeader soapHeader = soapEnvelope.getHeader();

		if(soapHeader == null){
			System.out.println("Erro header");
		}

		Name n = soapEnvelope.createName("cabecalho","ns","urn:chequerefeicao");
		Iterator i = soapHeader.getChildElements(n);
		if(i.hasNext()){
			SOAPElement el = (SOAPElement) i.next();
			username = el.getValue();
		}
		
		Name signat = soapEnvelope.createName("digSig", "ns", "urn:digSig");
		String signat64 = null;
		
		i = soapHeader.getChildElements(signat);
		if(i.hasNext()){
			SOAPElement el = (SOAPElement) i.next();
			signat64 = el.getValue();
		}
		
		byte[] mensagemCifrada = null;
		signat = soapEnvelope.createName("chave","ns","urn:chave");

		i = soapHeader.getChildElements(signat);
		if(i.hasNext()){
			SOAPElement el = (SOAPElement) i.next();
			String chaveSecreta = el.getValue();
			mensagemCifrada = parseBase64Binary(chaveSecreta);
		}

		byte[] signature = parseBase64Binary(signat64);
		byte[] mensagemDecifrada = AsymCrypto.decifrar(mensagemCifrada, username);
		
		SecretKey chaveSecreta1 = new SecretKeySpec(mensagemDecifrada, 0, mensagemDecifrada.length, "AES");
		String mensagem = null;

		if(soapBody.getFirstChild() != null){
			mensagem = soapBody.getFirstChild().getTextContent();
		}

		byte[] cifrado = parseBase64Binary(mensagem);
		cifrado = SymCrypto.decifrar(chaveSecreta1, cifrado);

		boolean validar = DigitalSignature.verifyDigitalSignature(signature, cifrado, username);
		if(validar){ 
			System.out.println("Valido");
		} else {
			System.out.println("Invalido");
		}
		
		soapMessage = byteArrayToSOAPMessage(cifrado);
		smc.setMessage(soapMessage);

	}
}


