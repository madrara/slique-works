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

import cartbolt.qui.entities.Outlet;
import cartbolt.qui.screens.Outlethome;
import cartbolt.qui.screens.R;
import cartbolt.utils.Globals;

/**
 * Created by Job on 29-May-16.
 */
public class OutletsBaseAdapter extends BaseAdapter {

    private Activity mContext;
    private List<Outlet> mList;
    //private LayoutInflater mLayoutInflater = null;

    public OutletsBaseAdapter(Activity context, List<Outlet> list) {
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
    public Outlet getItem(int pos) {
        return mList.get(pos);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setPieceList(List<Outlet> p){
        this.mList = p;
    }

    private class OutsViewHolder {

        public TextView pname;
        public TextView loc;

        public OutsViewHolder(View base) {
            pname = (TextView) base.findViewById(R.id.outlets_name);
            loc = (TextView) base.findViewById(R.id.outlets_location);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        OutsViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.row_outlets, null);
            viewHolder = new OutsViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (OutsViewHolder) v.getTag();
        }

        //int axe = position + 1;
        final Outlet pp = mList.get(position);
        final String id = pp.getId();
        final String name = pp.getName();
        final String sz = pp.getSizer();
        //System.out.println("ADDING ***************************************************** "+id);
        viewHolder.pname.setText(name);
        viewHolder.loc.setText(pp.getLocation());

		/*Picasso.with(mContext)
		.load(Globals.server + "/images/"+"img"+String./*valueOf(axe)/*pp.getImg()+".jpg")*/
        //.placeholder(R.drawable.ic_launcher).fit().into(viewHolder.pic);*/

        //on click
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globals.outletid = id;
                Globals.sizer = sz;
                Globals.outletname = name;
                Intent ot = new Intent(mContext, /*Outhomescreen*/Outlethome.class);
                mContext.startActivity(ot);
            }
        });

        return v;
    }

}
