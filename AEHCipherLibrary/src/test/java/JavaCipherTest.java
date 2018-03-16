import com.rohith.aeh.crypto.AEHCipher;
import com.rohith.aeh.crypto.AEHCryptoManager;
import com.rohith.aeh.crypto.config.AEHCryptoConfig;
import com.rohith.aeh.crypto.exception.AEHCipherException;

public class JavaCipherTest {

	public static void main(String[] args) throws AEHCipherException {
		AEHCryptoConfig config = new AEHCryptoConfig();
		config.setClassName("com.rohith.aeh.crypto.impls.javacipher.AESJavaCipherImpl");
		config.setEncryptionAlgorthim("AES");
		config.setKeySize(128);
		config.setPaddingScheme("PKCS5PADDING");
		config.setTransformationMode("CBC");
		AEHCryptoManager manager = new AEHCryptoManager(config);
		AEHCipher cipher = manager.cipher();
		String crypted = cipher.cipher("asasasas");
		System.out.println(cipher.decipher(crypted));
	}

	
}
