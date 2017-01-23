package com.example.flash.superkatale;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**

 */
 
public class Supernkt_CustomList extends ArrayAdapter<String> {
    private String[] supermkt_id;
    private String[] supermkt_names;
    private String[] supermkt_slogan;
    private String[] supermkt_location;

    private Activity context;

    public Supernkt_CustomList(Activity context, String[] supermkt_id, String[] supermkt_names, String[] supermkt_slogan, String[] supermkt_location) {
        super(context, R.layout.supermarket_list, supermkt_id);
        this.context = context;
        this.supermkt_id = supermkt_id;
        this.supermkt_names = supermkt_names;
        this.supermkt_slogan = supermkt_slogan;
        this.supermkt_location = supermkt_location;

    }
 
    @SuppressLint({ "ViewHolder", "InflateParams" }) @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.supermarket_list, null, true);
        TextView textViewname = (TextView) listViewItem.findViewById(R.id.spmktname);
        TextView textViewslogan = (TextView) listViewItem.findViewById(R.id.spmktslogan);
        TextView textViewid = (TextView) listViewItem.findViewById(R.id.spmktid);
        TextView textViewlocation = (TextView) listViewItem.findViewById(R.id.spmktlocation);


        textViewname.setText(supermkt_names[position]);
        textViewslogan.setText(supermkt_slogan[position]);
        textViewid.setText(supermkt_id[position]);
        textViewlocation.setText(supermkt_location[position]);

        return listViewItem;
    }
}