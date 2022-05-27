import java.math.BigInteger;

public class ResponseMessage {
    int selection;
    int size;
    String chainHash;
    double totalHashes;
    int totalDiff;
    BigInteger recentNonce;
    int hps;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    String response;
    public ResponseMessage(){}

    public int getSelection() {
        return selection;
    }

    public void setSelection(int selection) {
        this.selection = selection;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getChainHash() {
        return chainHash;
    }

    public void setChainHash(String chainHash) {
        this.chainHash = chainHash;
    }

    public double getTotalHashes() {
        return totalHashes;
    }

    public void setTotalHashes(double totalHashes) {
        this.totalHashes = totalHashes;
    }

    public int getTotalDiff() {
        return totalDiff;
    }

    public void setTotalDiff(int totalDiff) {
        this.totalDiff = totalDiff;
    }

    public BigInteger getRecentNonce() {
        return recentNonce;
    }

    public void setRecentNonce(BigInteger recentNonce) {
        this.recentNonce = recentNonce;
    }

    public int getHps() {
        return hps;
    }

    public void setHps(int hps) {
        this.hps = hps;
    }

    @Override
    public String toString() {
        return "{" +
                "selection:" + selection +
                ", size:" + size +
                ", chainHash:'" + chainHash + '\'' +
                ", totalHashes:" + totalHashes +
                ", totalDiff:" + totalDiff +
                ", recentNonce:" + recentNonce +
                ", hps:" + hps +
                ", \nresponse:'" + response + '\'' +
                '}';
    }
}
