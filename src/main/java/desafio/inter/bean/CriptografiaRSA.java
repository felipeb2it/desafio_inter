package desafio.inter.bean;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.enterprise.context.ApplicationScoped;
  

@ApplicationScoped
public class CriptografiaRSA {

	public static final String ALGORITHM = "RSA";

	private SecretKey originalKey;

	@PostConstruct
	private void init() {
		geraChaveAES();
	}

	public KeyPair geraChave() {
		try {
			final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
			keyGen.initialize(2048, new SecureRandom());
			final KeyPair key = keyGen.generateKeyPair();
			return key;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void geraChaveAES() {
		KeyGenerator generator;
		try {
			generator = KeyGenerator.getInstance("AES");
			generator.init(128); // The AES key size in number of bits
			originalKey = generator.generateKey();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	private byte[] criptografaAES(String texto) {
		Cipher aesCipher;
		try {
			aesCipher = Cipher.getInstance("AES");
			aesCipher.init(Cipher.ENCRYPT_MODE, originalKey);
			return aesCipher.doFinal(texto.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String descriptografaAES(byte[] texto) {
		Cipher aesCipher;
		try {
			aesCipher = Cipher.getInstance("AES");
			aesCipher.init(Cipher.DECRYPT_MODE, originalKey);
			byte[] bytePlainText = aesCipher.doFinal(texto);
			return new String(bytePlainText);
		} catch (Exception e) {
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
			cipher.init(Cipher.ENCRYPT_MODE, chave);
			cipherText = cipher.doFinal(criptografaAES(texto));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cipherText;
	}

	public byte[] criptografa(String texto, PrivateKey chave) {
		byte[] cipherText = null;

		try {
			final Cipher cipher = Cipher.getInstance(ALGORITHM);
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
		String decriptedText = null;
		try {
			final Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, chave);
			decriptedText = descriptografaAES(cipher.doFinal(Base64.getDecoder().decode(texto)));

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

		return decriptedText;
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
