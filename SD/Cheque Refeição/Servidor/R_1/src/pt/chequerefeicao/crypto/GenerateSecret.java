package pt.chequerefeicao.crypto;

//provides helper methods to print byte[]
import static javax.xml.bind.DatatypeConverter.printHexBinary;
import static javax.xml.bind.DatatypeConverter.printBase64Binary;
import static javax.xml.bind.DatatypeConverter.parseBase64Binary;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/** 
* 	Generate secure random numbers.
*/
public class GenerateSecret {
	
	public static String generateRandomNumber() throws NoSuchAlgorithmException{

     SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

     final byte array[] = new byte[16];
     random.nextBytes(array);
     String segredox64 = printBase64Binary(array);
   
     return segredox64;
    
	}
	
public static String separaSegredo(String idsegredo){
		
		int tamanho = idsegredo.length();
		int tamanhoId = tamanho - 24;
		String segredo="";
		
		for(int i=tamanhoId; i<24; i++){
			segredo += idsegredo.charAt(i);
		}
		
		return segredo;
		
	}

	public static String separaId(String idsegredo){
		
		int tamanho = idsegredo.length();
		int tamanhoId = tamanho - 24;
		String id="";
		
		for(int i=0; i<tamanhoId; i++){
			id += idsegredo.charAt(i);
		}
		
		return id;
		
	}
}
