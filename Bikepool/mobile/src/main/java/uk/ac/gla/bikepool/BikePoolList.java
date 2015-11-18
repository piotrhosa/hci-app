package uk.ac.gla.bikepool;

import java.util.ArrayList;
import android.app.ListFragment;
import android.os.Bundle;
import android.widget.SimpleAdapter;
import android.widget.ListView;
import android.view.View;

public class BikePoolList extends ListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<BikePool> values = new ArrayList<BikePool>();
        values.add(new BikePool("pool 1"));
        BikePoolListAdapter adapter = new BikePoolListAdapter(getActivity(), R.layout.list_item_pool_list, values);
        setListAdapter(adapter);
    }

    public void onResume() {
        super.onResume();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO
    }

}
