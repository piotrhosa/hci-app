package uk.ac.gla.bikepool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.widget.Toast;

import uk.ac.gla.bikepool.BikePoolListFragment.OnPoolPass;

public class BikePoolList extends AppCompatActivity implements OnPoolPass {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BikePoolListFragment fragment = new BikePoolListFragment();
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, fragment).commit();
    }

    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pool_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_map:
                Intent myIntent = new Intent(BikePoolList.this, BikePoolMap.class);
                startActivity(myIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPoolPass(BikePool pool) {
        Intent myIntent = new Intent(BikePoolList.this, BikePoolActivity.class);
        myIntent.putExtra("pool", pool);
        startActivity(myIntent);
    }
}
