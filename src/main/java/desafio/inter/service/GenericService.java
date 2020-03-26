package desafio.inter.service;

import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.ejb.Stateless;
import javax.inject.Inject;

import desafio.inter.bean.GenericDaoJPA;

@Stateless
public class GenericService {
	
	@Inject
	protected GenericDaoJPA dao;
	
	  /**
	   * Criptografa o texto puro usando chave pública.
	   */
	  public byte[] criptografa(String texto, PublicKey chave) {
	    byte[] cipherText = null;
	     
	    try {
	      final Cipher cipher = Cipher.getInstance("RSA");
	      // Criptografa o texto puro usando a chave Púlica
	      cipher.init(Cipher.ENCRYPT_MODE, chave);
	      cipherText = cipher.doFinal(texto.getBytes());
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
	      final Cipher cipher = Cipher.getInstance("RSA");
	      // Decriptografa o texto puro usando a chave Privada
	      cipher.init(Cipher.DECRYPT_MODE, chave);
	      dectyptedText = cipher.doFinal(texto);
	  
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	  
	    return new String(dectyptedText);
	  }
}
