package cartbolt.qui.screens.drawer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cartbolt.qui.screens.R;

public class ProfileFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //getSupportActionBar().setTitle(Globals.outletname);

        View view = inflater.inflate(R.layout.fragment_profile,
                container, false);

        /*adapter = new ListerItemBaseAdapter(
                FragmentTabSmall.this.getActivity(), new ArrayList<Item>());
        listview.setAdapter(adapter);*/

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}
