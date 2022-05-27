import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

//source: https://github.com/CMU-Heinz-95702/Project-2-Client-Server
public class RSA {
    private BigInteger n; // n is the modulus for both the private and public keys
    private BigInteger e; // e is the exponent of the public key
    private BigInteger d; // d is the exponent of the private key
    private MessageDigest md;
    public BigInteger getN() {
        return n;
    }

    public BigInteger getE() {
        return e;
    }

    public BigInteger getD() {
        return d;
    }

    public String publicKey() {
        return e+","+n;
    }

    public BigInteger encryptWithPrivate(String message) {
        //use the private key to encrypt the message, add leading 0
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        byte[] signBytes = new byte[messageBytes.length+1];
        signBytes[0] = 0;
        System.arraycopy(messageBytes, 0, signBytes, 1, signBytes.length - 1);
        System.out.println();
        BigInteger m = new BigInteger(signBytes);
        return m.modPow(d, n);
    }

    public BigInteger sign(String message) {
        //encrypt the message with private key to sign
        byte[] m = Utils.getHashBytes(message);
        return encryptWithPrivate(Utils.bytesToHex(m));
    }

    public String decryptMessage(BigInteger message, BigInteger e, BigInteger n) {
        //decrypt the message with key pari e + n
        byte[] mb = message.modPow(e, n).toByteArray();
        return new String(mb);
    }

    public RSA() {
        Random rnd = new Random();

        // Generate two large random primes.
        BigInteger p = new BigInteger(400, 100, rnd);
        BigInteger q = new BigInteger(400, 100, rnd);

        // Compute n by the equation n = p * q.
        n = p.multiply(q);

        // Compute phi(n) = (p-1) * (q-1)
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        // Select a small odd integer e that is relatively prime to phi(n).
        e = new BigInteger("65537");

        // Compute d as the multiplicative inverse of e modulo phi(n).
        d = e.modInverse(phi);

        try { // for hash
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            noSuchAlgorithmException.printStackTrace();
        }

        System.out.println(" e = " + e);
        System.out.println(" d = " + d);
        System.out.println(" n = " + n);
    }
}
