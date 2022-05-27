public class RequestMessage {
    int selection;
    int id;
    int difficulty;
    String message;

    public RequestMessage(){}

    public RequestMessage(int selection, int id, int difficulty, String message) {
        this.selection = selection;
        this.id = id;
        this.difficulty = difficulty;
        this.message = message;
    }

    public int getSelection() {
        return selection;
    }

    public void setSelection(int selection) {
        this.selection = selection;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
