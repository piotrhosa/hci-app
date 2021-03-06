package uk.ac.gla.bikepool;

import android.content.Context;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;

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
            LayoutInflater vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_bike_pool_item, null);
        }

        BikePool pool = mPools.get(position);
        if (pool != null) {
            TextView startTV = (TextView) v.findViewById(R.id.text_pool_start);
            TextView finishTV = (TextView) v.findViewById(R.id.text_pool_finish);
            TextView membersNoTV = (TextView) v.findViewById(R.id.text_pool_membersNo);

            if(startTV != null) startTV.setText(pool.getStartString());
            if(finishTV != null) finishTV.setText(pool.getFinishString());
            if(membersNoTV != null) membersNoTV.setText(Integer.toString(pool.getMembersNo()));
        }

        return v;
    }
}