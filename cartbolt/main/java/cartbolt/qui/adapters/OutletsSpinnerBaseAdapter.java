package cartbolt.qui.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cartbolt.qui.entities.Homecategory;
import cartbolt.qui.screens.Outlets;
import cartbolt.qui.screens.R;

/**
 * Created by Job on 29-May-16.
 */
public class OutletsSpinnerBaseAdapter extends BaseAdapter {

    private Activity context;
    private List<String> mList;

    public OutletsSpinnerBaseAdapter(Activity ct, List<String> list) {
        context = ct;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public String getItem(int pos) {
        return mList.get(pos);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setPieceList(List<String> p){
        this.mList = p;
    }

    private class CompleteCommentViewHolder {

        TextView pname;

        public CompleteCommentViewHolder(View base) {
            pname = (TextView) base.findViewById(R.id.outlets_spinner_title);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        CompleteCommentViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.row_outlets_spinner, null);
            viewHolder = new CompleteCommentViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (CompleteCommentViewHolder) v.getTag();
        }

        final String pp = mList.get(position);
        viewHolder.pname.setText(pp);

        return v;
    }
}
