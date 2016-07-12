package overlord.myapplication.Utility;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import overlord.myapplication.QuestionDatabase.QuestionDB;
import overlord.myapplication.QuestionDatabase.Singleton;
import overlord.myapplication.R;
import overlord.myapplication.Solved.AnswerDB;

public class Splash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        String hashCode = getApplicationContext().getString(R.string.application_id);
        Singleton.getInstance().setSharedPreferences(getSharedPreferences(Singleton.FIRSTRUN, 0));
        Log.d("",Singleton.getInstance().getSharedPreferences().getBoolean(Singleton.UNLOCKED, false)?"true":"false");
        if(Singleton.getInstance().getSharedPreferences().getBoolean(Singleton.UNLOCKED, false)){
            Singleton.getInstance().setQuestionDB(new QuestionDB((getApplicationContext())));
            Singleton.getInstance().setQuestionArrayList(Singleton.getInstance().getQuestionDB().readDB());
            Singleton.getInstance().setAnswerDB(new AnswerDB(getApplicationContext()));
            Singleton.getInstance().setAnswerArrayList(Singleton.getInstance().getAnswerDB().readDB());

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Splash.this, StatActivity.class);
                    startActivity(intent);
                }
            }, SPLASH_TIME_OUT);
        }
        else {
            Log.d("",Singleton.getInstance().getSharedPreferences().getBoolean(Singleton.UNLOCKED, false)?"true":"false");
            startActivity(new Intent(Splash.this, Unlock.class));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
