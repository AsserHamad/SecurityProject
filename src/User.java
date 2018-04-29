import java.security.*;
import java.util.ArrayList;

public class User {

	public String name;
	public int id;
	private PrivateKey privateKey;
	public PublicKey publicKey;
	double GPA;
	ArrayList<User> peers;
	ArrayList<Transaction> Transactions;
	int[] peersSending;

	// Constructor
	public User(String name) {
		this.name = name;
		this.GPA = 0.2;
		this.id = Main.userIds++;
		this.Transactions = new ArrayList<Transaction>();
		KeyPairGenerator keyGen;
		SecureRandom random;
		try {
			keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
			random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			keyGen.initialize(1024, random);
			KeyPair pair = keyGen.generateKeyPair();
			this.privateKey = pair.getPrivate();
			this.publicKey = pair.getPublic();
			this.peers = new ArrayList<User>();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Transaction transact(String type, String date) {
		Transaction transaction = new Transaction(type, date);
		transaction.encrypt(this.privateKey);
		Transactions.add(transaction);
		int[] peersToSend = new int[(int) (peers.size() - 1)];
		int peerNotToSendTo = (int) (Math.random() * peers.size());
		for (int i = 0; i < peersToSend.length; i++) {
			if (i != peerNotToSendTo)
				peersToSend[i] = peers.get(i).id;
		}
		this.peersSending = peersToSend;
		return transaction;
	}

	public boolean addPeer(User peer) {
		if (peers.contains(peer) && peer.name != this.name) {
			return false;
		} else
			peers.add(peer);
		return true;
	}

	public boolean hasTransaction(Transaction transaction) {
		return Transactions.contains(transaction);
	}

	public Boolean addTransaction(Transaction transaction) {
		Transactions.add(transaction);
		System.out.println("Sending to " + (int) (peers.size() - 1) + " peers");
		int[] peersToSend = new int[(int) (peers.size() - 1)];
		int peerNotToSendTo = (int) (Math.random() * peers.size());
		for (int i = 0; i < peersToSend.length; i++) {
			if (i != peerNotToSendTo)
				peersToSend[i] = peers.get(i).id;
		}
		this.peersSending = peersToSend;

		return Transactions.size() >= 4;
	}

	public String printTransactions() {
		String tmp = "";
		for (int i = 0; i < Transactions.size(); i++) {
			Transaction transaction = Transactions.get(i);
			tmp += "[id: " + transaction.id + "  Type: " + transaction.type + "  Date: " + transaction.date + "]";
		}
		return tmp;
	}

	public String toString() {
		return this.id + "   Name:" + this.name + "  GPA:" + this.GPA + "\nTransactions:" + this.printTransactions()
				+ "\n";
	}

	public static void main(String[] args) {
		User asser = new User("Asser");
		System.out.println(asser);
	}
}
