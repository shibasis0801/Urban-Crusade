package overlord.myapplication.Utility;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import overlord.myapplication.QuestionDatabase.QuestionDB;
import overlord.myapplication.QuestionDatabase.Singleton;
import overlord.myapplication.R;
import overlord.myapplication.Solved.AnswerDB;

public class Unlock extends AppCompatActivity {

    public int mTeamID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_unlock);
        final EditText password = (EditText)findViewById(R.id.password_uc);
        final Button button_init = (Button)findViewById(R.id.button_init);
        final EditText teamid = (EditText)findViewById(R.id.editText);
        button_init.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!teamid.getText().toString().equals(null)
                && Hash.hash(password.getText().toString(),"SHA-256").equals(getApplicationContext().getString(R.string.application_id))) {

                    SharedPreferences.Editor editor = Singleton.getInstance().getSharedPreferences().edit();
                    editor.putBoolean(Singleton.UNLOCKED, true);
                    editor.putInt(Singleton.TEAMID, Integer.parseInt(teamid.getText().toString()));
                    editor.commit();
                    Log.d("", Singleton.getInstance().getSharedPreferences().getBoolean(Singleton.UNLOCKED, false) ? "true" : "false");
                    Singleton.getInstance().setQuestionDB(new QuestionDB((getApplicationContext())));
                    Singleton.getInstance().setQuestionArrayList(Singleton.getInstance().getQuestionDB().readDB());
                    Singleton.getInstance().setAnswerDB(new AnswerDB(getApplicationContext()));
                    Singleton.getInstance().setAnswerArrayList(Singleton.getInstance().getAnswerDB().readDB());
                    Intent intent = new Intent(Unlock.this, StatActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(Unlock.this, "Incorrect/Incomplete information", Toast.LENGTH_SHORT).show();
                    password.setText("");
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
