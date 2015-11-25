package uk.ac.gla.bikepool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button mapButton;
    Button listButton;
    Button userButton;
    Button poolButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hookButtons();
    }

    public void hookButtons(){
        mapButton = (Button) findViewById(R.id.map_btn);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMapActivity();
            }
        });

        listButton = (Button) findViewById(R.id.list_btn);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startListActivity();
            }
        });

        userButton = (Button) findViewById(R.id.user_btn);
        userButton .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startUserActivity();
            }
        });

        poolButton = (Button) findViewById(R.id.pool_btn);
        poolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPoolActivity();
            }
        });
    }

    public void startMapActivity(){
        Intent myIntent = new Intent(MainActivity.this, BikePoolMap.class);
        //myIntent.putExtra("key", value); //Optional parameters
        startActivity(myIntent);
    }

    public void startListActivity() {
        Intent myIntent = new Intent(MainActivity.this, BikePoolList.class);
        startActivity(myIntent);
    }

    public void startUserActivity() {
        Intent myIntent = new Intent(MainActivity.this, UserActivity.class);
        startActivity(myIntent);
    }

    public void startPoolActivity() {
        Intent myIntent = new Intent(MainActivity.this, BikePoolActivity.class);
        startActivity(myIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}