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
 
public class Fuelco_CustomList extends ArrayAdapter<String> {
    private String[] fuelco_id;
    private String[] fuelco_name;
    private String[] fuelco_slogan;
    private String[] fuelco_location;

    private Activity context;

    public Fuelco_CustomList(Activity context, String[] fuelco_id, String[] fuelco_name, String[] fuelco_slogan, String[] fuelco_location) {
        super(context, R.layout.fuelcompany_list, fuelco_id);
        this.context = context;
        this.fuelco_id = fuelco_id;
        this.fuelco_name = fuelco_name;
        this.fuelco_slogan = fuelco_slogan;
        this.fuelco_location = fuelco_location;

    }
 
    @SuppressLint({ "ViewHolder", "InflateParams" }) @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.fuelcompany_list, null, true);
        TextView textViewname = (TextView) listViewItem.findViewById(R.id.fuelconame);
        TextView textViewslogan = (TextView) listViewItem.findViewById(R.id.fuelcoslogan);
        TextView textViewid = (TextView) listViewItem.findViewById(R.id.fuecoid);
        TextView textViewslocation = (TextView) listViewItem.findViewById(R.id.fuecolocation);

        textViewname.setText(fuelco_name[position]);
        textViewslogan.setText(fuelco_slogan[position]);
        textViewid.setText(fuelco_id[position]);
        textViewslocation.setText(fuelco_location[position]);

        return listViewItem;
    }
}