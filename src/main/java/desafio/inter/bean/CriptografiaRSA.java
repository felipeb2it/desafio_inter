package desafio.inter.bean;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.ejb.Stateless;
  
@Stateless
public class CriptografiaRSA {
  
  public static final String ALGORITHM = "RSA";
  
  public KeyPair geraChave() {
    try {
      final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
      keyGen.initialize(1024);
      final KeyPair key = keyGen.generateKeyPair();
      return key;
      
    }catch (Exception e) {
        e.printStackTrace();
    }
	return null;
  }
  
  /**
   * Criptografa o texto puro usando chave pública.
   */
  public byte[] criptografa(String texto, PublicKey chave) {
    byte[] cipherText = null;
     
    try {
      final Cipher cipher = Cipher.getInstance(ALGORITHM);
      // Criptografa o texto puro usando a chave Púlica
      cipher.init(Cipher.ENCRYPT_MODE, chave);
      cipherText = cipher.doFinal(texto.getBytes(StandardCharsets.UTF_8));
    } catch (Exception e) {
      e.printStackTrace();
    }
     
    return cipherText;
  }
  
  public byte[] criptografa(String texto, PrivateKey chave) {
	    byte[] cipherText = null;
	     
	    try {
	      final Cipher cipher = Cipher.getInstance(ALGORITHM);
	      // Criptografa o texto puro usando a chave Púlica
	      cipher.init(Cipher.ENCRYPT_MODE, chave);
	      cipherText = cipher.doFinal(texto.getBytes(StandardCharsets.UTF_8));
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	     
	    return cipherText;
  }
  
  /**
   * Decriptografa o texto puro usando chave privada.
   */
  public String decriptografa(byte[] texto, PrivateKey chave) {
    byte[] dectyptedText = null;
     
    try {
      final Cipher cipher = Cipher.getInstance(ALGORITHM);
      // Decriptografa o texto puro usando a chave Privada
      cipher.init(Cipher.DECRYPT_MODE, chave);
      dectyptedText = cipher.doFinal(texto);
  
    } catch (Exception ex) {
    	ex.printStackTrace();
    	return null;
    }
  
    return new String(dectyptedText, StandardCharsets.UTF_8);
  }
  
  public PrivateKey chavePrivadaConverter(byte[] chavePrivada) {
	try {
		KeyFactory kf = KeyFactory.getInstance(ALGORITHM);
		return kf.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(chavePrivada)));
	} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
		e.printStackTrace();
		return null;
	}
  }
  
  public PublicKey chavePublicaConverter(byte[] chavePublica) {
	try {
		KeyFactory kf = KeyFactory.getInstance(ALGORITHM);
		return kf.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(chavePublica)));
	} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
		e.printStackTrace();
		return null;
	}
  }
  

}
