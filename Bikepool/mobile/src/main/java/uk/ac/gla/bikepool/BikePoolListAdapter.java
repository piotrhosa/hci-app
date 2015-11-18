package uk.ac.gla.bikepool;

import android.content.Context;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

public class BikePoolListAdapter extends ArrayAdapter<BikePool> {
    private ArrayList<BikePool> mPools;

    public BikePoolListAdapter(Context context, int textViewResourceId, ArrayList<BikePool> pools) {
        super(context, textViewResourceId, pools);
        mPools = pools;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            //LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //v = vi.inflate(R.layout.list_item_pool_list, null);
        }

        BikePool pool = mPools.get(position);
        if (pool != null) {
            /*
            TextView nameTV = (TextView) v.findViewById(R.id.username);
            TextView membersNoTV = (TextView) v.findViewById(R.id.email);

            if(nameTV != null)
                nameTV.setText(pool.getName());

            if(membersNoTV != null)
                membersNoTv.setText(pool.getMembersNo());
            */
        }
        return v;
    }
}