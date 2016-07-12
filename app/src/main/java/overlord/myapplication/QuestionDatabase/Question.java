package overlord.myapplication.QuestionDatabase;

/**
 * Created by OverlordPC on 23-Jan-16.
 */
public class Question {
    public static final int WORM        = 0;
    public static final int VIRUS       = 1;
    public static final int TROJAN      = 2;
    public static final int ANTIVIRUS   = 3;
    public static final int CONSOLE     = 4;
    public static String[] cardCategory = { "WORM", "VIRUS", "TROJAN",  "ANTIVIRUS", "CONSOLE" };
    int cardType;
    String cardName;
    int points;
    int clueType;
    int resourceID;
    String question;
    String md5HASH;
    boolean solved;
    int id;

    @Override
     public String toString() {
        return cardName + "\t:\t" + points;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getClueType() {
        return clueType;
    }

    public void setClueType(int clueType) {
        this.clueType = clueType;
    }

    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getMd5HASH() {
        return md5HASH;
    }

    public void setMd5HASH(String md5HASH) {
        this.md5HASH = md5HASH;
    }
}
