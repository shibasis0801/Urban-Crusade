package overlord.myapplication.Clue;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import overlord.myapplication.QuestionDatabase.Question;
import overlord.myapplication.QuestionDatabase.Singleton;
import overlord.myapplication.R;

/**
 * A fragment representing a single Clue detail screen.
 * This fragment is either contained in a {@link ClueListActivity}
 * in two-pane mode (on tablets) or a {@link ClueDetailActivity}
 * on handsets.
 */
public class ClueDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Question question;
    private int questionID;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ClueDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(Singleton.POSITION)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            questionID = getActivity().getIntent().getIntExtra(Singleton.POSITION,0);
            question = Singleton.getInstance().getQuestionArrayList().get(getActivity().getIntent().getIntExtra("CATEGORY",0)).get(questionID);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(question.getCardName());
            }
        }
    }
    private MediaPlayer mediaPlayer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;
        if (question.getClueType() == 0) {
            rootView = inflater.inflate(R.layout.fragment_clue_text, container, false);
            ((TextView) rootView.findViewById(R.id.clue_detail_text)).setText(question.getQuestion());
        } else if (question.getClueType() == 1) {
            rootView = inflater.inflate(R.layout.fragment_clue_audio, container, false);
            ((TextView) rootView.findViewById(R.id.clue_detail_text)).setText(question.getQuestion());
            ((ImageButton) rootView.findViewById(R.id.play_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Singleton.getInstance().stopPlaying();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Singleton.getInstance().setMediaPlayer(MediaPlayer.create(getContext(), question.getResourceID()));
                            Singleton.getInstance().getMediaPlayer().start();
                        }
                    }, 500);
                }
            });
        } else {
            rootView = inflater.inflate(R.layout.fragment_clue_image, container, false);
            ((TextView) rootView.findViewById(R.id.clue_detail_text)).setText(question.getQuestion());
            ((SubsamplingScaleImageView) rootView.findViewById(R.id.clue_detail_image)).setImage(ImageSource.resource(question.getResourceID()));
        }
        return rootView;
    }
}
