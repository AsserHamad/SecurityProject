import java.security.*;
import java.util.Base64;

public class Transaction {

	String id;
	String encryptedId;
	String type;
	String date;

	public void encrypt(PrivateKey privateKey) {
		try {
			Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
			dsa.initSign(privateKey);
			dsa.update(this.id.getBytes());
			encryptedId = Base64.getEncoder().encodeToString(dsa.sign());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean verify(PublicKey publicKey) {
		Signature dsa;
		try {
			dsa = Signature.getInstance("SHA1withDSA", "SUN");
			dsa.initVerify(publicKey);
			dsa.update(this.id.getBytes());
			return dsa.verify(Base64.getDecoder().decode(this.encryptedId));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	public Transaction(String type, String date) {
		this.id = "" + Main.transIds++;
		this.type = type;
		this.date = date;
	}

}
