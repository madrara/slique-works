package cartbolt.qui.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import cartbolt.qui.entities.Item;
import cartbolt.qui.screens.Details;
import cartbolt.qui.screens.R;
import cartbolt.utils.AppController;
import cartbolt.utils.Cart;
import cartbolt.utils.Globals;

/**
 * Created by Job on 29-May-16.
 */
public class ListerItemBaseAdapter extends BaseAdapter {

    private Activity mContext;
    private List<Item> mList;
    //private LayoutInflater mLayoutInflater = null;

    public ListerItemBaseAdapter(Activity context, List<Item> list) {
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
    public Item getItem(int pos) {
        return mList.get(pos);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setPieceList(List<Item> p){
        this.mList = p;
    }
    private class ListerItemViewHolder {

        public ImageView img;
        public TextView pname;
        public TextView pprice;
        public ImageButton ntstatus;

        public ListerItemViewHolder(View base) {
            img = (ImageView) base.findViewById(R.id.outpicl);
            pname = (TextView) base.findViewById(R.id.outnamel);
            pprice = (TextView) base.findViewById(R.id.ntprice);
            ntstatus = (ImageButton) base.findViewById(R.id.ntstatus);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ListerItemViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.row_lister, null);
            viewHolder = new ListerItemViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ListerItemViewHolder) v.getTag();
        }

        //int axe = position + 1;
        final Item pp = mList.get(position);
        final String name = pp.getName();
        final String theprice = String.valueOf(pp.getPrice().intValue());
        final String id = String.valueOf(pp.getId());
        final String pic = pp.getImage();
        viewHolder.pname.setText(name);
        viewHolder.pprice.setText(theprice+"/=");
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        // Loading image with placeholder and error image
        imageLoader.get(Globals.images+pic, ImageLoader.getImageListener(
                viewHolder.img, R.mipmap.carter, R.mipmap.carter));


        boolean minion = false;
        //checking if the item is already in the cart
        for(int mv = 0; mv < Cart.cartlist.size(); mv++){
            if(Cart.cartlist.get(mv).getId() == Integer.parseInt(id)){
                //Toast.makeText(mContext, "Item is already in the cart", Toast.LENGTH_LONG).show();
                minion = true;
            }
        }
        final boolean bullion = minion;
        if(bullion){
            viewHolder.ntstatus.setImageResource(R.drawable.tick);
            viewHolder.ntstatus.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                }
            });
        } else {
            viewHolder.ntstatus.setImageResource(R.drawable.buy);
            viewHolder.ntstatus.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(checkInternetConnection()){
                        Intent i = new Intent(mContext, Details.class);
                        Globals.itemid = pp.getId();
                        /*i.putExtra("id", id);
                        i.putExtra("name", name);
                        i.putExtra("price", theprice);
                        i.putExtra("pic", pic);
                        //Toast.makeText(mContext, "Forwarding id :"+ id +" Detail", Toast.LENGTH_SHORT).show();
                        //i.putExtra("id", id);*/
                        mContext.startActivity(i);
                    } else {
                        Toast.makeText(mContext, "Unable to connect to the internet", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        /*Picasso.with(mContext)
                .load(Globals.server + "/images/"+pp.getImage()/*+".jpg")*//*)
                .placeholder(R.mipmap.carter).fit().into(viewHolder.img);*/

        return v;
    }

    private boolean checkInternetConnection() {
        ConnectivityManager conMgr = (ConnectivityManager) mContext.getSystemService (Context.CONNECTIVITY_SERVICE);
        // ARE WE CONNECTED TO THE NET
        if (conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
