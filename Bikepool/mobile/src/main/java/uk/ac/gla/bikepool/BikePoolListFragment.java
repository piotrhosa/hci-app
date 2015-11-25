package uk.ac.gla.bikepool;

import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Bundle;
import android.location.Location;
import android.app.Activity;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class BikePoolListFragment extends ListFragment {

    OnPoolPass poolPasser;
    ArrayList<BikePool> values;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        values = new ArrayList<BikePool>();
        DbHelper db = new DbHelper(getActivity());
        Location start = new Location("noProvider");
        Location finish = new Location("noProvider");
        db.addPool(new BikePool(1,"pool 1", start, finish, "start", "finish", new ArrayList<LatLng>()));
        values = db.getAllPools();
        BikePoolListAdapter adapter = new BikePoolListAdapter(getActivity(), R.layout.frag_bike_pool_list, values);
        setListAdapter(adapter);
    }

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        poolPasser = (OnPoolPass) a;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        passPool(values.get(position));
    }

    public void passPool(BikePool pool) {
        poolPasser.onPoolPass(pool);
    }

    public interface OnPoolPass {
        public void onPoolPass(BikePool pool);
    }
}
