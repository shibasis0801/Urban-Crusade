package overlord.myapplication.Clue;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import overlord.myapplication.Intent.IntentIntegrator;
import overlord.myapplication.Intent.IntentResult;
import overlord.myapplication.QuestionDatabase.Question;
import overlord.myapplication.QuestionDatabase.Singleton;
import overlord.myapplication.R;
import overlord.myapplication.Solved.Answer;
import overlord.myapplication.Utility.Hash;

/**
 * An activity representing a single Clue detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ClueListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link ClueDetailFragment}.
 */
public class ClueDetailActivity extends AppCompatActivity {

    int mPosition;
    int mCardType;
    ClueDetailFragment clueDetailFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clue_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        mCardType = getIntent().getIntExtra("CATEGORY",0);
        mPosition = getIntent().getIntExtra(Singleton.POSITION,0);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Singleton.getInstance().stopPlaying();
                new IntentIntegrator(ClueDetailActivity.this).initiateScan();
            }
        });

        // Show the Up button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            mPosition = getIntent().getIntExtra(Singleton.POSITION,0);
            arguments.putInt(Singleton.POSITION,mPosition);

            clueDetailFragment = new ClueDetailFragment();
            clueDetailFragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.clue_detail_container, clueDetailFragment)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Singleton.getInstance().stopPlaying();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        try {
            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            if (scanResult != null) {
                Question question = Singleton.getInstance().getQuestionArrayList().get(mCardType).get(mPosition);
                String actualValue = question.getMd5HASH();
                String scannedValue = Hash.hash(scanResult.getContents(), "SHA-256");
                Log.d(Singleton.POSITION,Integer.toString(mPosition));
                Log.d("ACTUAL VALUE",actualValue);
                Log.d("SCANNED VALUE",scannedValue);
                boolean zeroHead = actualValue.substring(1).equals(scannedValue);
                if (actualValue.equals(scannedValue) ||
                        zeroHead) {
                    question.setSolved(true);
                    Answer answer = new Answer();
                    answer.setAnswer(actualValue);
                    answer.setCard(question.getCardName());
                    answer.setPoints(question.getPoints());
                    answer.setType(question.getCardType());
                    Singleton.getInstance().getAnswerDB().writeDB(answer,question);
                    Toast.makeText(this, "Proceed Crusader", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this,"Aap Bakchod Ho",Toast.LENGTH_SHORT).show();
                }
            }
        }
        catch (Exception e){
            recreate();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            Singleton.getInstance().stopPlaying();
            Intent intent = new Intent(this,ClueListActivity.class);
            intent.putExtra("CATEGORY",getIntent().getIntExtra("CATEGORY",0));
            NavUtils.navigateUpTo(this, intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
