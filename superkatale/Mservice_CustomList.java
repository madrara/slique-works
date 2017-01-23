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
 
public class Mservice_CustomList extends ArrayAdapter<String> {
    private String[] mservice_id;
    private String[] mservice_name;
    private String[] mservice_slogan;
    private String[] mservice_location;
     private String[] mservice_contact;

    private Activity context;

    public Mservice_CustomList(Activity context, String[] mservice_id, String[] mservice_name, String[] mservice_slogan, String[] mservice_location, String[] mservice_contact) {
        super(context, R.layout.mechanical_service_list, mservice_id);
        this.context = context;
        this.mservice_id = mservice_id;
        this.mservice_name = mservice_name;
        this.mservice_slogan = mservice_slogan;
        this.mservice_location = mservice_location;
        this.mservice_contact = mservice_contact;

    }
 
    @SuppressLint({ "ViewHolder", "InflateParams" }) @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.mechanical_service_list, null, true);

        TextView textViewname = (TextView) listViewItem.findViewById(R.id.mservicename);
        TextView textViewslogan = (TextView) listViewItem.findViewById(R.id.mserviceslogan);
        TextView textViewid = (TextView) listViewItem.findViewById(R.id.mserviceid);
        TextView textViewslocation = (TextView) listViewItem.findViewById(R.id.mservicelocation);
        TextView textViewscontact = (TextView) listViewItem.findViewById(R.id.mservicecontact);


        textViewname.setText(mservice_name[position]);
        textViewslogan.setText(mservice_slogan[position]);
        textViewid.setText(mservice_id[position]);
        textViewslocation.setText(mservice_location[position]);
        textViewscontact.setText(mservice_contact[position]);

        return listViewItem;
    }
}