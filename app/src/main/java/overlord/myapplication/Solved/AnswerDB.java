package overlord.myapplication.Solved;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;

import overlord.myapplication.QuestionDatabase.Question;
import overlord.myapplication.QuestionDatabase.Singleton;

/**
 * Created by OverlordPC on 26-Jan-16.
 */
public class AnswerDB extends SQLiteOpenHelper {
    public abstract static class HashStorage implements BaseColumns {
        public static final String TABLE_NAME = "answers";
        public static final String ANSWER = "answer";
        public static final String CARD = "card";
        public static final String POINTS = "points";
        public static final String CARDTYPE = "type";
    }
    public static final String COMMA = ",";
    public static final String TEXT  = "    text";
    public static final String INT   = "    int";

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "hashStorage.db";

    public static final String CREATE_TABLE = "CREATE TABLE " + HashStorage.TABLE_NAME + "(" +
        HashStorage._ID + "INTEGER PRIMARY KEY" + COMMA +
        HashStorage.ANSWER + TEXT + COMMA +
        HashStorage.CARD   + TEXT + COMMA +
        HashStorage.POINTS + INT  + COMMA +
        HashStorage.CARDTYPE+INT  +  ")";
    public static final String DROP_TABLE = "DROP TABLE " + HashStorage.TABLE_NAME ;

    public AnswerDB(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }
    public ArrayList<Answer> readDB(){
        ArrayList<Answer> answerArrayList = new ArrayList<>();
        String select = "SELECT * FROM " + HashStorage.TABLE_NAME ;//+ " ORDER BY " + HashStorage.POINTS;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(select,null);
        Singleton.getInstance().resetScores();
        if(cursor.moveToFirst()) {
            do{
                Answer answer = new Answer();
                answer.setAnswer(cursor.getString(1));
                answer.setCard(cursor.getString(2));
                answer.setPoints(cursor.getInt(3));
                answer.setType(cursor.getInt(4));
                Singleton.getInstance().getPoints()[answer.getType()] += answer.getPoints();
                Singleton.getInstance().setScore(Singleton.getInstance().getScore() + answer.getPoints());
                answerArrayList.add(answer);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return answerArrayList;
    }
    public void writeDB(Answer answer,Question question){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM answers WHERE card = ?",
                new String[]{answer.getCard()});
        if(!cursor.moveToFirst()) {
            Singleton.getInstance().getAnswerArrayList().add(answer);
            Singleton.getInstance().setScore(Singleton.getInstance().getScore() + question.getPoints());
            Singleton.getInstance().getPoints()[question.getCardType()] += question.getPoints();
            ContentValues values = new ContentValues();
            values.put(HashStorage.ANSWER, answer.getAnswer());
            values.put(HashStorage.CARD, answer.getCard());
            values.put(HashStorage.POINTS, answer.getPoints());
            values.put(HashStorage.CARDTYPE,answer.getType());
            database.insert(
                    HashStorage.TABLE_NAME,
                    null,
                    values);
            database.close();
        }
        cursor.close();
    }
}
