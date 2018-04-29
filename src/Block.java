import java.security.*;

public class Block {
	Transaction[] transactions;
	String hash;
	String prevHash;
	int nonce;

	public Block(Transaction[] transactions, String prevHash) {
		this.transactions = transactions;
		if(transactions.length != 4) {
			System.out.println("A block can be made from exactly 4 transactions only");
			return;
		}
		this.prevHash = prevHash;
		nonce = (int)(Math.random() * 1000);
		calculateHash();
	}
	
	public static String applySha256(String input){ //taken from https://medium.com/programmers-blockchain/create-simple-blockchain-java-tutorial-from-scratch-6eeed3cb03fa	
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");	        
			//Applies sha256 to our input, 
			byte[] hash = digest.digest(input.getBytes("UTF-8"));	        
			StringBuffer hexString = new StringBuffer(); // This will contain hash as hexidecimal
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void calculateHash() { 
		hash = applySha256(prevHash + transactions[0].id + transactions[1].id + transactions[2].id + transactions[3].id + nonce);
	}
}