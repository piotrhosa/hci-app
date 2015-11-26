package uk.ac.gla.bikepool;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class BikePoolActivity extends AppCompatActivity {
    TextView nameTV;
    TextView startTV;
    TextView finishTV;
    TextView descriptionTV;
    TextView membersTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pool);

        BikePool pool = (BikePool)getIntent().getSerializableExtra("pool");

        hookText();
    }

    private void hookText() {
        nameTV = (TextView) findViewById(R.id.text_name);
        startTV = (TextView) findViewById(R.id.text_start);
        finishTV = (TextView) findViewById(R.id.text_finish);
        descriptionTV = (TextView) findViewById(R.id.text_desc);
        membersTV = (TextView) findViewById(R.id.text_members);
    }
}