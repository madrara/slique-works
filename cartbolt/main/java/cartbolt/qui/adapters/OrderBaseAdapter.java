package cartbolt.qui.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cartbolt.qui.entities.Order;
import cartbolt.qui.screens.OrderDetails;
import cartbolt.qui.screens.R;

/**
 * Created by Job on 15-Jul-16.
 */
public class OrderBaseAdapter extends BaseAdapter {

    private Activity mContext;
    private List<Order> mList;
    //private LayoutInflater mLayoutInflater = null;

    public OrderBaseAdapter(Activity context, List<Order> list) {
        mContext = context;
        mList = list;
		/*mLayoutInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);*/
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Order getItem(int pos) {
        return mList.get(pos);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setPieceList(List<Order> p){
        this.mList = p;
    }

    private class OutsViewHolder {

        public TextView pname;
        public TextView loc;
        public TextView typ;
        public TextView tym;

        public TextView time;
        public OutsViewHolder(View base) {
            pname = (TextView) base.findViewById(R.id.ordername);
            loc = (TextView) base.findViewById(R.id.orderlocation);
            typ = (TextView) base.findViewById(R.id.ordertype);
            tym = (TextView) base.findViewById(R.id.ordertime);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        OutsViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.row_order, null);
            viewHolder = new OutsViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (OutsViewHolder) v.getTag();
        }

        //int axe = position + 1;
        final Order pp = mList.get(position);
        final String id = pp.getId();
        final String name = pp.getName();
        final String location = pp.getLocation();
        final String type = pp.getType();
        final String time = pp.getTime();
        //System.out.println("ADDING ***************************************************** "+id);
        viewHolder.pname.setText(name);
        viewHolder.loc.setText(location);
        viewHolder.tym.setText(time);
        switch(type){
            case "1":
                viewHolder.typ.setText("Bike 15mins - 25000");
                break;
            case "2":
                viewHolder.typ.setText("Bike 30mins - 15000");
                break;
            case "3":
                viewHolder.typ.setText("Bike 1hour  - 10000");
                break;
            case "4":
                viewHolder.typ.setText("Bike 5hours - 5000");
                break;
            case "5":
                viewHolder.typ.setText("Car  30mins - 30000");
                break;
            case "6":
                viewHolder.typ.setText("Car  1hour  - 20000");
                break;
            case "7":
                viewHolder.typ.setText("Car  5hours - 10000");
                break;
            default:
                viewHolder.typ.setText("Not selected");
                break;
        }

		/*Picasso.with(mContext)
		.load(Globals.server + "/images/"+"img"+String./*valueOf(axe)/*pp.getImg()+".jpg")*/
        //.placeholder(R.drawable.ic_launcher).fit().into(viewHolder.pic);*/

        //on click
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Globals.outletid = id;
                //Globals.sizer = sz;
                //Globals.outletname = name;
                Intent ot = new Intent(mContext, OrderDetails.class);
                ot.putExtra("id", id);
                mContext.startActivity(ot);
            }
        });

        return v;
    }

}
