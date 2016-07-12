package overlord.myapplication.QuestionDatabase;

import android.content.SharedPreferences;
import android.media.MediaPlayer;

import java.util.ArrayList;

import overlord.myapplication.Solved.Answer;
import overlord.myapplication.Solved.AnswerDB;

/**
 * Created by OverlordPC on 24-Jan-16.
 */
public class Singleton {

    public final static String POSITION  = "CLUE_POSITION";
    public static final String FIRSTRUN  = "firstRun";
    public static final String UNLOCKED  = "UNLOCKED";
    public static final String TEAMID    = "TEAMID";
    public static final String CATEGORY  = "CATEGORY";

    private ArrayList<ArrayList<Question>> questionArrayList;
    private ArrayList<Answer> answerArrayList;
    private QuestionDB questionDB;
    private AnswerDB answerDB;
    private MediaPlayer mediaPlayer;

    private int maximumPoints[] = new int[5];
    private int points[] = new int[5];
    private int score;
    private SharedPreferences sharedPreferences;

    public void resetScores(){
        points        = new int[5];
        score         = 0;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public int[] getPoints() {
        return points;
    }

    public void setPoints(int[] points) {
        this.points = points;
    }

    public int[] getMaximumPoints() {
        return maximumPoints;
    }

    public void setMaximumPoints(int[] maximumPoints) {
        this.maximumPoints = maximumPoints;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public void stopPlaying(){
        if(getMediaPlayer() != null){
            getMediaPlayer().stop();
            getMediaPlayer().reset();
            setMediaPlayer(null);
        }
    }

    public AnswerDB getAnswerDB() {
        return answerDB;
    }

    public void setAnswerDB(AnswerDB answerDB) {
        this.answerDB = answerDB;
    }

    public QuestionDB getQuestionDB() {
        return questionDB;
    }

    public void setQuestionDB(QuestionDB questionDB) {
        this.questionDB = questionDB;
    }

    private static Singleton ourInstance = null;

    public static Singleton getInstance() {
        if(ourInstance == null)
            ourInstance = new Singleton();
        return ourInstance;
    }
    private Singleton() {
    }

    public ArrayList<ArrayList<Question>> getQuestionArrayList() {
        return questionArrayList;
    }

    public void setQuestionArrayList(ArrayList<ArrayList<Question>> questionArrayList) {
        this.questionArrayList = questionArrayList;
    }

    public ArrayList<Answer> getAnswerArrayList() {
        return answerArrayList;
    }

    public void setAnswerArrayList(ArrayList<Answer> answerArrayList) {
        this.answerArrayList = answerArrayList;
    }
}
