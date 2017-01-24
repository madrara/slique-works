package cartbolt.qui.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cartbolt.qui.entities.OrderItem;
import cartbolt.qui.screens.R;

/**
 * Created by Job on 05-Jan-17.
 */
public class OrderCartBaseAdapter extends BaseAdapter {

    private Activity context;
    private List<OrderItem> mList;

    public OrderCartBaseAdapter(Activity ct, List<OrderItem> list) {
        context = ct;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public OrderItem getItem(int position) {
        return mList.get(position);
    }

    public OrderItem getOrderItem(int pos) {
        return mList.get(pos);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setPieceList(List<OrderItem> p){
        this.mList = p;
    }

    private class ViewHolder {

        //RelativeLayout pic;
        TextView qty;
        TextView name;
        TextView price;
        TextView cart_row_outlet;

        public ViewHolder(View base) {
            qty = (TextView) base.findViewById(R.id.cart_row_newnum);
            name= (TextView) base.findViewById(R.id.cart_row_nametag);
            price = (TextView) base.findViewById(R.id.cart_row_price_tag);
            cart_row_outlet = (TextView) base.findViewById(R.id.cart_row_outlet);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        final ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.row_carty_order, null);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }

        final OrderItem pp = mList.get(position);
        final String thename = pp.getName();
        final String theprice = pp.getPrice();
        final String theqt = pp.getQuantity();
        final int posit = position;
        viewHolder.qty.setText(String.valueOf(theqt));
        viewHolder.name.setText(thename);
        Double pricer = Double.parseDouble(theprice) * Integer.parseInt(pp.getQuantity());
        viewHolder.price.setText(String.valueOf(pricer.intValue()));
        viewHolder.cart_row_outlet.setText(pp.getOutlet());
        return v;
    }
}
