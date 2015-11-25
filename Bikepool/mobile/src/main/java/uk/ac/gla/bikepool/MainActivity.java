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
    Button notifyButton;

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

        notifyButton = (Button) findViewById(R.id.notification_btn);
        notifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotificationToWatch();
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

    public void sendNotificationToWatch() {
        BikePoolController controller = (BikePoolController) getApplicationContext();
        controller.buildAndSendNotification("Twoja stara", "Robi Placki");
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