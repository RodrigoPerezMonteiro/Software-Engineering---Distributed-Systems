package pt.chequerefeicao.ws.generatekeys;

//provides helper methods to print byte[]
import static javax.xml.bind.DatatypeConverter.printHexBinary;

import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

import pt.chequerefeicao.ws.impl.ChequeRefeicaoMain;

/** Generate a digital signature */
public class DigitalSignature {


    /** auxiliary method to calculate digest from text and cipher it */
    public static byte[] makeDigitalSignature(byte[] bytes,
                                              PrivateKey priv) throws Exception {

        // get a message digest object using the MD5 algorithm
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");

        // calculate the digest and print it out
        messageDigest.update(bytes);
        byte[] digest = messageDigest.digest();
        System.out.println("Digest:");
        System.out.println(printHexBinary(digest));

        // get an RSA cipher object 
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        // encrypt the plaintext using the private key
        cipher.init(Cipher.ENCRYPT_MODE, priv);
        byte[] cipherDigest = cipher.doFinal(digest);

        System.out.println("Cipher digest:");
        System.out.println(printHexBinary(cipherDigest));

        return cipherDigest;
    }


    /** auxiliary method to calculate new digest from text and compare it to the
         to deciphered digest */
    public static boolean verifyDigitalSignature(byte[] cipherDigest,
                                                 byte[] cifrado,
                                                 String username) throws Exception {

        // get a message digest object using the MD5 algorithm
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");

        // calculate the digest and print it out
        messageDigest.update(cifrado);
        byte[] digest = messageDigest.digest();
        System.out.println("New digest:");
        System.out.println(printHexBinary(digest));
        PublicKey pub = ChequeRefeicaoMain.getPublicKeys().get(username);

        System.out.println("username: " + username);
        System.out.println("chave: " + pub);
        
        // get an RSA cipher object 
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        // decrypt the ciphered digest using the public key
        cipher.init(Cipher.DECRYPT_MODE, pub);
        byte[] decipheredDigest = cipher.doFinal(cipherDigest);
        System.out.println("Deciphered digest:");
        System.out.println(printHexBinary(decipheredDigest));

        // compare digests
        if (digest.length != decipheredDigest.length)
            return false;

        for (int i=0; i < digest.length; i++)
            if (digest[i] != decipheredDigest[i])
                return false;
        return true;
    }

}
