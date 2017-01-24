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

import cartbolt.qui.entities.Subcategory;
import cartbolt.qui.screens.Lister;
import cartbolt.qui.screens.R;
import cartbolt.utils.Globals;

/**
 * Created by Job on 29-May-16.
 */
public class SubcategoryBaseAdapterLarge extends BaseAdapter {

    private Activity mContext;
    private List<Subcategory> mList;
    //private LayoutInflater mLayoutInflater = null;

    public SubcategoryBaseAdapterLarge(Activity context, List<Subcategory> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Subcategory getItem(int pos) {
        return mList.get(pos);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setPieceList(List<Subcategory> p){
        this.mList = p;
    }

    private class SubcategoryViewHolder {

        public TextView pname;

        public SubcategoryViewHolder(View base) {
            pname = (TextView) base.findViewById(R.id.sub_space);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        SubcategoryViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.row_subcategory, null);
            viewHolder = new SubcategoryViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (SubcategoryViewHolder) v.getTag();
        }

        //int axe = position + 1;
        final Subcategory pp = mList.get(position);
        final String id = String.valueOf(pp.getId());
        final String name = pp.getName();
        viewHolder.pname.setText(name);

        //on click
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globals.subcategoryid = pp.getId();
                Intent ot = new Intent(mContext, Lister.class);
                //ot.putExtra("id", id/*name*/);
                mContext.startActivity(ot);
            }
        });

        return v;
    }
}
