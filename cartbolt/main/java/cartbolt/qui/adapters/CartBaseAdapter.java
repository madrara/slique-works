package cartbolt.qui.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cartbolt.qui.entities.Item;
import cartbolt.qui.screens.R;
import cartbolt.utils.Cart;

/**
 * Created by Job on 09-Jun-16.
 */
public class CartBaseAdapter extends BaseAdapter {

    private Activity context;
    private List<Item> mList;

    public CartBaseAdapter(Activity ct, List<Item> list) {
        context = ct;
        mList = list;
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

    private class ViewHolder {

        //RelativeLayout pic;
        TextView name;
        EditText qty;
        TextView price;
        TextView cartedit;
        TextView cancel;

        public ViewHolder(View base) {
            name= (TextView) base.findViewById(R.id.cart_row_nametag);
            qty = (EditText) base.findViewById(R.id.cart_row_newnum);
            price = (TextView) base.findViewById(R.id.cart_row_price_tag);
            cartedit = (TextView) base.findViewById(R.id.cart_row_edit_tag);
            cancel = (TextView) base.findViewById(R.id.cart_row_remove);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        final ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.row_carty, null);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }

        final Item pp = mList.get(position);
        final String thename = pp.getName();
        final String theqt = String.valueOf(pp.getQuantity());
        final int posit = position;
        viewHolder.qty.setText(String.valueOf(pp.getQuantity()));
        viewHolder.name.setText(pp.getName());
        Double theprice = Double.parseDouble(pp.getPrice().toString()) * pp.getQuantity();
        viewHolder.price.setText(theprice.toString());
        viewHolder.cartedit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //viewHolder.qty.setVisibility(View.VISIBLE);
                viewHolder.qty.setText(theqt);
                viewHolder.qty.requestFocus();
                viewHolder.cartedit.setText("Save");
                viewHolder.cartedit.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        //viewHolder.qty.setVisibility(View.INVISIBLE);
                        viewHolder.cartedit.setText("Edit");
                        pp.setQuantity(Integer.parseInt(viewHolder.qty.getText().toString()));
                        CartBaseAdapter.this.notifyDataSetChanged();
                        context.finish();
                        context.startActivity(context.getIntent());
                    }
                });

            }
        });
        viewHolder.cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                razer(R.id.cart_row_remove, posit, thename);
            }
        });

        return v;
    }

    private void razer(int emt, int poster, String n){
        switch(emt){
			/*case R.id.plus:
				int qt = Cart.cartlist.get(poster).getQuantity();
				qt++;
				Cart.cartlist.get(poster).setQuantity(qt);
				break;
			case R.id.minus:
				int qtr = Cart.cartlist.get(poster).getQuantity();
				if(qtr>1){
					qtr--;
				}
				Cart.cartlist.get(poster).setQuantity(qtr);
				break;*/
            case R.id.cart_row_remove:
                Cart.cartlist.remove(poster);
                this.notifyDataSetChanged();
                Toast.makeText(context, "Removed " + n, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /*private void picture(String ft, ImageView pic) {
        Picasso.with(context)
                .load(ft)
                .placeholder(R.mipmap.ic_launcher).fit().into(pic);
        System.out.println("Failing to load ************************************************ "+ft);
    }*/
}
