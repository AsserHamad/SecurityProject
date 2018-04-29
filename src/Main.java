import java.security.MessageDigest;
import java.util.ArrayList;

public class Main {
	
	ArrayList<User> users = new ArrayList<User>();
	public static int userIds = 0; //auto increment IDs for users to guarantee ID uniqueness
	public static int transIds = 0; //auto increment IDs for transactions to guarantee ID uniqueness
	
	public void populateUsers() {
		String []names = new String[] {"Asser","Yara","Ahmed","Joseph","Josuke","Rana","Jojo","Maged","Magamigo","Jotaro", "Omar", "Alaa", "Amr", "Mougy", "Goofy", "Scrooge", "Soosoo", "Tooto", "Balabizo", "Chico"};
		for(String name : names)
			users.add(new User(name));
		randomizePeers();
	}
	
	public String getAllUsers() {
		String tmp = "";
		for(int i=0;i<users.size();i++) {
			User user = users.get(i);
			tmp+="ID: "+user.id+"  "+user.name+"\n";
		}
		return tmp;
	}
	
	public void addPeer(User user) {
		while(!user.addPeer(users.get((int)(Math.random()*users.size()))));
	}
	
	public void randomizePeers() {
		for(int i=0;i<users.size();i++) {
			User user = users.get(i);
			int numOfPeers = 2+(int)(Math.random()*(users.size()-2));
			System.out.println("Adding "+numOfPeers+" peers to "+users.get(i).name);
			for(int j=0;j<numOfPeers;j++)
				this.addPeer(user);
		}
	}
	
	public String printPeers() {
		String tmp = "";
		for(int i = 0;i<users.size();i++) {
			String tmp0 = "";
			User user = users.get(i);
			tmp+="\n\n"+user.name+"'s peers>>";
			for(int j=0;j<user.peers.size();j++) {
				User peer = user.peers.get(j);
				tmp0 += "  Peer: "+ peer.name;
			}
			tmp+="\n"+tmp0;
		}
		return tmp;
	}
	
	private void sendTransaction(int id, Transaction transaction) {
		User user = getUserById(id);
		if(!user.hasTransaction(transaction)) {
			Boolean enoughForBlock = user.addTransaction(transaction);
			if(enoughForBlock){
				String st = "";
				for(Transaction x : user.Transactions){
					st+=x.id+",";
				}
				
			}
			else {
				for(int i=0;i<user.peersSending.length;i++){
					sendTransaction(user.peersSending[i], transaction);
				}
			}
		}
	}
	
	private void userSendTransaction(User user, Transaction transaction) {
			for(int i=0;i<user.peersSending.length;i++)
				sendTransaction(user.peersSending[i], transaction);
	}
	
	private User getUserById(int id) {
		for(int i=0;i<users.size();i++)
			if(users.get(i).id==id)
				return users.get(i);
		return null;
	}

	private void showUserTransactionLists() {
		for(int i=0;i<users.size();i++){
			System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\"+users.get(i).name+"\\\\\\\\\\\\\\\\\\\\\\\\");
			ArrayList<Transaction> transactions = users.get(i).Transactions;
			System.out.println("Transactions: ");
			for(int j=0;j<transactions.size();j++){
				System.out.println("["+transactions.get(j).type+" at "+transactions.get(j).date+"]");
			}
		}
	}

	public static void main(String[] args) {
		Main main = new Main();
		main.populateUsers();
//		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
//		messageDigest.update(stringToEncrypt.getBytes());
//		String encryptedString = new String(messageDigest.digest());
		User asser = main.users.get(0);
		
		Transaction
		asserTransaction = asser.transact("PS4", "30/3/2018");
		main.userSendTransaction(asser, asserTransaction);
		
		asserTransaction = asser.transact("Laptop", "1/4/2018");
		main.userSendTransaction(asser, asserTransaction);
		
		asserTransaction = asser.transact("Helicopter", "2/4/2018");
		main.userSendTransaction(asser, asserTransaction);
		
		asserTransaction = asser.transact("Yara's phone", "3/4/2018");
		main.userSendTransaction(asser, asserTransaction);
		
//		main.showUserTransactionLists();
		
	}

	
}
