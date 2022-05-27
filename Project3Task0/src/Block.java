import java.math.BigInteger;
import java.sql.Timestamp;

public class Block {
    int index;
    Timestamp timestamp;
    String data;
    int difficulty;
    String previousHash;
    BigInteger nonce;

    public Block(int index, Timestamp timestamp, String data, int difficulty) {
        this.index = index;
        this.timestamp = timestamp;
        this.data = data;
        this.difficulty = difficulty;
        this.previousHash = "";
        this.nonce = new BigInteger("0");
    }

    public String calculateHash() {
        String hash = "";
        hash = Utils.getHashString(index+timestamp.toString()+data+previousHash+nonce+difficulty);
        return hash;
    }

    public BigInteger getNonce() {
        return nonce;
    }

    public String proofOfWork() {
        String hash = calculateHash();
        while (!hash.matches("0".repeat(difficulty)+".*")) {
            nonce = nonce.add(new BigInteger("1"));
            hash = calculateHash();
        }
        return hash;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        return "{" +
                "index: " + index +
                ", timestamp: " + timestamp +
                ", data: '" + data + '\'' +
                ", difficulty: " + difficulty +
                ", previousHash: '" + previousHash + '\'' +
                ", nonce: " + nonce +
                "}\n";
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
