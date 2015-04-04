package pt.chequerefeicao.crypto;

// provides helper methods to print byte[]
import static javax.xml.bind.DatatypeConverter.printHexBinary;
import static javax.xml.bind.DatatypeConverter.parseBase64Binary;
import static javax.xml.bind.DatatypeConverter.printBase64Binary;







import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import pt.chequerefeicao.ws.impl.ChequeRefeicaoMain;

/**
 * Public key cryptography using the RSA algorithm.
 */
public class AsymCrypto {

	public static byte[] cifrar(byte[] plainBytes, String username) throws Exception {
		// get an RSA cipher object and print the provider
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

		cipher.init(Cipher.ENCRYPT_MODE, ChequeRefeicaoMain.getCRPrivKey());
		byte[] cipherBytes = cipher.doFinal(plainBytes);

		return cipherBytes;
	}

	public static byte[] decifrar(byte[] cipherBytes, String username) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		// decrypt the ciphertext using the private key
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		
		cipher.init(Cipher.DECRYPT_MODE, ChequeRefeicaoMain.getCRPrivKey());
		byte[] newPlainBytes = cipher.doFinal(cipherBytes);

		return newPlainBytes;
	}

	public static PrivateKey getPrivateKey(String privateKeyFile) throws IOException,
	GeneralSecurityException {
		byte[] privEncoded = readFile(privateKeyFile);
		PKCS8EncodedKeySpec privSpec = new PKCS8EncodedKeySpec(privEncoded);
		KeyFactory keyFacPriv = KeyFactory.getInstance("RSA");
		PrivateKey privKey = keyFacPriv.generatePrivate(privSpec);
		return privKey;
	}

	public static PublicKey getPublicKey(String publicKeyFile) throws IOException,
	GeneralSecurityException {
		byte[] pubEncoded = readFile(publicKeyFile);
		X509EncodedKeySpec pubSpec = new X509EncodedKeySpec(pubEncoded);
		KeyFactory keyFacPub = KeyFactory.getInstance("RSA");
		PublicKey pub = keyFacPub.generatePublic(pubSpec);
		return pub;
	}


	private static byte[] readFile(String path) throws FileNotFoundException, IOException {
		FileInputStream fis = new FileInputStream(path);
		byte[] content = new byte[fis.available()];
		fis.read(content);
		fis.close();
		return content;
	}
}
