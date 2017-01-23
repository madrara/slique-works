package com.example.flash.superkatale;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**

 */
 
public class Bread_CustomList extends ArrayAdapter<String> {
    private String[] id;
    private String[] description;
    private String[] price;
    private String[] fulldescription;
    private String[] imageurl;
    private Activity context;

    public Bread_CustomList(Activity context, String[]id, String[]description, String[]price, String[]fulldescription, String[]imageurl ) {
        super(context, R.layout.bread_list,id);
        this.context = context;
        this.id = id;
        this.description = description;
        this.price = price;
        this.fulldescription = fulldescription;
        this.imageurl = imageurl;

    }
 
    @SuppressLint({ "ViewHolder", "InflateParams" }) @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.bread_list, null, true);
        TextView textView_id = (TextView) listViewItem.findViewById(R.id.txid);
        TextView textViews_desc = (TextView) listViewItem.findViewById(R.id.txdescription);
        TextView textView_price = (TextView)listViewItem.findViewById(R.id.txprice);
        TextView textView_full = (TextView)listViewItem.findViewById(R.id.txtfulldescription);
        TextView textview_url = (TextView)listViewItem.findViewById(R.id.txturl);

        textView_id.setText(id[position]);
        textViews_desc.setText(description[position]);
        textView_price.setText(price[position]);
        textView_full.setText(fulldescription[position]);
        textview_url.setText(imageurl[position]);
        return listViewItem;
    }
}