package overlord.myapplication.QuestionDatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

/**
 * Created by OverlordPC on 23-Jan-16.
 */
public class QuestionDB extends SQLiteAssetHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "question.db";
    public static final String TABLE_NAME    = "questions";

    public QuestionDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public ArrayList<ArrayList<Question>> readDB(){
        ArrayList<ArrayList<Question>> arrayList = new ArrayList<ArrayList<Question>>();
        for(int i = 0; i < 5; ++i)
            arrayList.add(new ArrayList<Question>());
        String selectAll = "SELECT * FROM " + TABLE_NAME + " ORDER BY cardType asc,points desc";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectAll,null);
        cursor.moveToFirst();
        do{
            Question question = new Question();
            question.setCardType(cursor.getInt(0));
            question.setCardName(cursor.getString(1));
            question.setPoints(cursor.getInt(2));
            question.setClueType(cursor.getInt(3));
            question.setResourceID(cursor.getInt(4));
            question.setQuestion(cursor.getString(5).replace('&','\n').replace(':','"'));
            question.setMd5HASH(cursor.getString(6));
            question.setSolved(false);
            question.setId(cursor.getInt(8));
            Singleton.getInstance().getMaximumPoints()[question.getCardType()] += question.getPoints();
            arrayList.get(cursor.getInt(0)).add(question);
        }while(cursor.moveToNext());
        cursor.close();
        return arrayList;
    }
}
