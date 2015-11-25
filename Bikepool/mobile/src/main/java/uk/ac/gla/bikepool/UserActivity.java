package uk.ac.gla.bikepool;

import android.media.Rating;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.RatingBar;

/**
 * Created by piotr on 25/11/15.
 */
public class UserActivity extends AppCompatActivity {

    ImageView mPicIV;
    TextView mNameTV;
    RatingBar mRatingRB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPicIV = (ImageView) findViewById(R.id.image_user);
        mPicIV.setImageResource(R.drawable.leia);
        mPicIV.getLayoutParams().height = 500;

        mNameTV = (TextView) findViewById(R.id.user_name);

        mRatingRB = (RatingBar) findViewById(R.id.rating_bar);
    }
}
