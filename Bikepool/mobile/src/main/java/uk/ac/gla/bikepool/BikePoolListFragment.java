package uk.ac.gla.bikepool;

import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Bundle;
import android.location.Location;

import java.util.ArrayList;

public class BikePoolListFragment extends ListFragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.onCreate(savedInstanceState);

        ArrayList<BikePool> values = new ArrayList<BikePool>();
        DbHelper db = new DbHelper(getActivity());
        Location start = new Location("noProvider");
        Location finish = new Location("noProvider");
        db.addPool(new BikePool("pool 1", start, finish, "start", "finish"));
        values = db.getAllPools();
        BikePoolListAdapter adapter = new BikePoolListAdapter(getActivity(), R.layout.frag_bike_pool_list, values);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(getActivity(), Integer.toString(position), Toast.LENGTH_SHORT).show();
    }
}
