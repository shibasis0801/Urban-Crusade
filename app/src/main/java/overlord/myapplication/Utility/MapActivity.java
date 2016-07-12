package overlord.myapplication.Utility;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import overlord.myapplication.R;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getSupportActionBar().setTitle("NIT Rourkela Map");
        SubsamplingScaleImageView imageView = (SubsamplingScaleImageView)findViewById(R.id.map_nitr);
        imageView.setImage(ImageSource.resource(R.drawable.smapp));
    }
}
