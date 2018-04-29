public class playground {
	
	final static int n = 9999999;

    public static int generatePublicKey(int privateKey) {
        return n - privateKey;
    }

    public static int encrypt(int message, int key) {
        return (message + key) % n;
    }

    public static int decrypt(int cipher, int key) {
        return (cipher + key) % n;
    }

    public static void main(String [] args) {
        int privateKey = 133;
        int publicKey = generatePublicKey(133);

        int cipher = encrypt(293920, privateKey);
        System.out.println(decrypt(cipher, publicKey));
    }
}
