package com.example.irm.utils;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.slf4j.Logger;

import com.example.irm.error.IrmCryptographyException;

public class PkiUtil
{
	private static Logger LOGGER = LogUtil.getLogger();
	
	public static KeyPair generateKeyPair()
			throws IrmCryptographyException
	{
		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance(Constants.PKI_ALGORITHM);
		    generator.initialize(Constants.PKI_KEYSIZE);		    
		    KeyPair keyPair = generator.generateKeyPair();
		    return keyPair;
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("Error when generating key pair", e);
			throw new IrmCryptographyException(e);
		}
	}
	
	public static String encrypt(String plainText, PublicKey publicKey)
			throws Exception
	{
        Cipher encryptCipher = Cipher.getInstance(Constants.PKI_ALGORITHM);
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] cipherText = encryptCipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(cipherText);
    }

    public static String decrypt(String cipherText, PrivateKey privateKey)
    		throws Exception
    {
        byte[] bytes = Base64.getDecoder().decode(cipherText);

        Cipher decriptCipher = Cipher.getInstance(Constants.PKI_ALGORITHM);
        decriptCipher.init(Cipher.DECRYPT_MODE, privateKey);

        return new String(decriptCipher.doFinal(bytes), StandardCharsets.UTF_8);
    }

    public static String sign(String plainText, PrivateKey privateKey)
    		throws Exception
    {
        Signature privateSignature = Signature.getInstance(Constants.SIGNATURE_ALGORITHM);
        privateSignature.initSign(privateKey);
        privateSignature.update(plainText.getBytes(StandardCharsets.UTF_8));

        byte[] signature = privateSignature.sign();

        return Base64.getEncoder().encodeToString(signature);
    }

    public static boolean verify(String plainText, String signature, PublicKey publicKey)
    		throws Exception
    {
        Signature publicSignature = Signature.getInstance(Constants.SIGNATURE_ALGORITHM);
        publicSignature.initVerify(publicKey);
        publicSignature.update(plainText.getBytes(StandardCharsets.UTF_8));

        byte[] signatureBytes = Base64.getDecoder().decode(signature);

        return publicSignature.verify(signatureBytes);
    }
	
	public static void writePemFile(Key key, String description, String filename)
		      throws FileNotFoundException, IOException
	{
		PemFile pemFile = new PemFile(key, description);
		pemFile.write(filename);
		LOGGER.info(String.format("%s successfully writen in file %s.", description, filename));
	}
	
	public static String getStringFormat(Key key)
			throws IOException
	{
		byte[] byte_pubkey = key.getEncoded();
		String strKey = Base64.getEncoder().encodeToString(byte_pubkey);		
		LOGGER.info("String KEY::" + strKey);
		
		return strKey;
	}
	
	public static PublicKey rebuildPublicKey(String strKey)
			throws IrmCryptographyException
	{
		byte[] bKey  = Base64.getDecoder().decode(strKey);
		PublicKey pubKey = null;
		
		try {
			KeyFactory factory = KeyFactory.getInstance(Constants.PKI_ALGORITHM);
			pubKey = factory.generatePublic(new X509EncodedKeySpec(bKey));
			System.out.println("FINAL OUTPUT" + pubKey);
		} catch(NoSuchAlgorithmException | InvalidKeySpecException e) {
			LOGGER.error("Error when rebuilding public key", e);
			throw new IrmCryptographyException(e);
		}
		
		return pubKey;
	}
	
	public static PrivateKey rebuildPrivateKey(String strKey)
			throws IrmCryptographyException
	{
		byte[] bKey  = Base64.getDecoder().decode(strKey);
		PrivateKey priKey = null;
		
		try {
			KeyFactory factory = KeyFactory.getInstance(Constants.PKI_ALGORITHM);
			priKey = factory.generatePrivate(new PKCS8EncodedKeySpec(bKey));
			System.out.println("FINAL OUTPUT" + priKey);
		} catch(NoSuchAlgorithmException | InvalidKeySpecException e) {
			LOGGER.error("Error when rebuilding public key", e);
			throw new IrmCryptographyException(e);
		}
		
		return priKey;
	}
}

