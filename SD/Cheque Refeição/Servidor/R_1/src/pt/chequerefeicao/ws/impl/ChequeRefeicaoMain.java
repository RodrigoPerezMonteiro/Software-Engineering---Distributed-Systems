package pt.chequerefeicao.ws.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.xml.ws.Endpoint;

import pt.chequerefeicao.ws.uddi.UDDINaming;

public class ChequeRefeicaoMain {

	private static Map<String, PublicKey> publicKeys;
	private static PrivateKey chequeRefeicaoPrivKey;
	private static File folder = new File("keys");
	private static File[] folderFiles = folder.listFiles();
	private static String publicKey = null;
	private static String result;
	private static File file;
	private static byte[] pubEncoded;

	public static Map<String, PublicKey> getPublicKeys(){
		return publicKeys;
	}

	public static PrivateKey getCRPrivKey(){
		return chequeRefeicaoPrivKey;
	}
	
	private static byte[] readFile(String path)
			throws FileNotFoundException, IOException {
		FileInputStream fis = new FileInputStream(path);
        byte[] content = new byte[fis.available()];
        fis.read(content);
        fis.close();
		return content;
	}

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		if (args.length < 3) {
			System.err.println("Argument(s) missing!");
			System.err.printf("Usage: java %s uddiURL wsName wsURL%n", ChequeRefeicaoMain.class.getName());
			return;
		}
		
		publicKeys = new HashMap<String, PublicKey>();
		

		//Preencher Map com public keys de todos os users
		for(int i=0; i<folderFiles.length; i++){
			result = (folderFiles[i].getName()).substring(0, (folderFiles[i].getName()).indexOf(".")).trim();

			pubEncoded = readFile("keys/" + folderFiles[i].getName());

			if(!result.equals("chequerefeicao")){
				X509EncodedKeySpec pubSpec = new X509EncodedKeySpec(pubEncoded);
				KeyFactory keyFacPub = KeyFactory.getInstance("RSA");
				PublicKey pub = keyFacPub.generatePublic(pubSpec);					
				publicKeys.put(result, pub);

			} else{

				PKCS8EncodedKeySpec privSpec = new PKCS8EncodedKeySpec(pubEncoded);
				KeyFactory keyFacPriv = KeyFactory.getInstance("RSA");
				PrivateKey priv = keyFacPriv.generatePrivate(privSpec);
				chequeRefeicaoPrivKey = priv;
			}

		}

		String uddiURL = args[0];
		String name = args[1];
		String url = args[2];

		Endpoint endpoint = null;
		UDDINaming uddiNaming = null;


		try {
			endpoint = Endpoint.create(new ChequeImpl());

			// publish endpoint
			System.out.printf("Starting %s%n", url);
			endpoint.publish(url);


			System.out.printf("Publishing '%s' to UDDI at %s%n", name, uddiURL);
			uddiNaming = new UDDINaming(uddiURL);
			uddiNaming.rebind(name, url);

			// wait
			System.out.println("Awaiting connections");
			System.out.println("Press enter to shutdown");
			System.in.read();

		} catch(Exception e) {
			System.out.printf("Caught exception: %s%n", e);
			e.printStackTrace();

		} finally {
			try {
				if (endpoint != null) {
					// stop endpoint
					endpoint.stop();
					System.out.printf("Stopped %s%n", url);
				}
			} catch(Exception e) {
				System.out.printf("Caught exception when stopping: %s%n", e);
			}
			try {
				if (uddiNaming != null) {
					// delete from UDDI
					uddiNaming.unbind(name);
					System.out.printf("Deleted '%s' from UDDI%n", name);
				}
			} catch(Exception e) {
				System.out.printf("Caught exception when deleting: %s%n", e);
			}
		}
	}
}