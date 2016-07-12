package overlord.myapplication.Solved;

import java.util.ArrayList;

/**
 * Created by OverlordPC on 25-Jan-16.
 */
public class AnswerSingleton {
    private ArrayList<Answer> answerArrayList;
    private static AnswerSingleton ourInstance = new AnswerSingleton();

    public static AnswerSingleton getInstance() {
        if(ourInstance == null){
            ourInstance = new AnswerSingleton();
        }
        return ourInstance;
    }

    private AnswerSingleton() {
    }
}
