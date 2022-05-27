import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BlockChain {
    List<Block> chain;
    String chainHash;
    int hashPerSec;

    public BlockChain() {
        chain = new ArrayList<>();
        chainHash = "";
        Block gen = new Block(0, new Timestamp(System.currentTimeMillis()),"", 2);
        gen.proofOfWork();
        addBlock(gen);
        computeHashesPerSecond();
    }

    public void addBlock(Block newBlock) {
        String testHash = newBlock.calculateHash();
        if (newBlock.previousHash.equals(chainHash) && testHash.matches("0".repeat(newBlock.difficulty)+".*")) {
            chain.add(newBlock);
            chainHash = testHash;
        } else {
            System.out.println("Illegal block!");
        }
    }

    public String getChainHash() {
        return chainHash;
    }

    public Timestamp getTime() {
        return new Timestamp(System.currentTimeMillis());
    }

    public Block getLatestBlock() {
        return chain.get(chain.size()-1);
    }

    public int getChainSize() {
        return chain.size();
    }

    public void computeHashesPerSecond() {
        String s = "00000000";
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        byte[] hs;
        long start = System.currentTimeMillis();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 2000000; i++) {
            hs = md.digest(bytes);
        }
        long end = System.currentTimeMillis();
        hashPerSec = (int) (2000000/(end-start));
    }

    public int getHashesPerSecond() {
        return hashPerSec;
    }

    @Override
    public String toString() {
        return "BlockChain: {" +
                "chain: " + chain +
                ", chainHash: '" + chainHash + '\'' +
                '}';
    }

    public Block getBlock(int i) {
        return chain.get(i);
    }

    public int getTotalDifficulty() {
        int total = 0;
        for (Block block: chain) {
            total += block.getDifficulty();
        }
        return total;
    }

    public double getTotalExpectedHashes() {
        double total = 0;
        for (Block block: chain) {
            total += Math.pow(16, block.getDifficulty());
        }

        return total;
    }

    public String isChainValid() {

        long end;
        String block1Hash = ""; // previous block hash
        String block2Hash = ""; // current block hash
        for (Block block : chain) { // each round test if current block hash match previous one
            block2Hash = block.calculateHash();
            if (!block2Hash.matches("0".repeat(block.getDifficulty()) + ".*")){
                System.out.printf("Improper hash on node %d Does not begin with " + "0".repeat(block.getDifficulty()) + "\n", block.getIndex());
            } else if (!block.getPreviousHash().equals(block1Hash)) {
                System.out.printf("Improper hash on node %d Previous Hash is incorrect\n", block.getIndex());
                return "FALSE";
            }
            block1Hash = block2Hash;
        }

        //check if the last block hash equals chain hash
        if (!block1Hash.equals(chainHash)) {
            System.out.println("The chain hash is incorrect");
            return "FALSE";
        }
        return "TRUE";
    }

    public void repairChain() {
        String block1Hash = ""; // previous block hash
        String block2Hash = ""; // current block hash
        boolean repair = false;
        int i;
        for (i = 0; i < chain.size(); i++) { // each round test if current block hash match previous one
            Block block = chain.get(i);
            block2Hash = block.calculateHash();

            if (!block2Hash.matches("0".repeat(block.getDifficulty()) + ".*")
                    || !block.getPreviousHash().equals(block1Hash)) {
                break;
            }
            block1Hash = block2Hash; //set previous hash
        }
        if (i == chain.size() && !block1Hash.equals(chainHash))
            i = chain.size() - 1; // the last block hash not equals to chain hash
        else if (i == chain.size()) return; // the chain is correct

        for (; i < chain.size(); i++) {//repair the chain from the corrupt block
            Block block = chain.get(i);
            block.setPreviousHash(i == 0 ? "" : chain.get(i - 1).calculateHash()); // previous hash is empty for the first block
            block.proofOfWork();
            chain.set(i, block);
        }

        chainHash = chain.get(chain.size() - 1).calculateHash(); // update the chain hash in the end
    }
}
