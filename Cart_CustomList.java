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
 
public class Cart_CustomList extends ArrayAdapter<String> {
    private String[] id;
    private String[] quantity;
    private String[] price;
    private String[] fulldescription;
    private String[] phone;
    private String[] image;

    private Activity context;

    public Cart_CustomList(Activity context, String[] id, String[] quantity, String[] price, String[] fulldescription, String[] phone, String[] image) {
        super(context, R.layout.cart_list, id);
        this.context = context;
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.fulldescription = fulldescription;
        this.phone = phone;
        this.image = image;

    }
 
    @SuppressLint({ "ViewHolder", "InflateParams" }) @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.cart_list, null, true);
        TextView textView_id = (TextView) listViewItem.findViewById(R.id.textID);
        TextView textViews_desc = (TextView) listViewItem.findViewById(R.id.textdescribe);
        TextView textView_price = (TextView)listViewItem.findViewById(R.id.textprice);
        TextView textView_qnty = (TextView)listViewItem.findViewById(R.id.textqnty);
        TextView textview_phone = (TextView)listViewItem.findViewById(R.id.textphone);
        TextView textView_image = (TextView)listViewItem.findViewById(R.id.textimage_url);

        textView_id.setText(id[position]);
        textView_qnty.setText(quantity[position]);
        textView_price.setText(price[position]);
        textViews_desc.setText(fulldescription[position]);
        textview_phone.setText(phone[position]);
        textView_image.setText(image[position]);
        return listViewItem;
    }
}