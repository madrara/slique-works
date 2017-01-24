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

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import cartbolt.qui.entities.Homecategory;
import cartbolt.qui.screens.Outlets;
import cartbolt.qui.screens.R;
import cartbolt.utils.AppController;
import cartbolt.utils.Globals;

/**
 * Created by Job on 29-May-16.
 */
public class HomeCategoryBaseAdapter extends BaseAdapter {

    private Activity context;
    private List<Homecategory> mList;

    public HomeCategoryBaseAdapter(Activity ct, List<Homecategory> list) {
        context = ct;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Homecategory getItem(int pos) {
        return mList.get(pos);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setPieceList(List<Homecategory> p){
        this.mList = p;
    }

    private class CompleteCommentViewHolder {

        ImageView pic;
        TextView pname;
        //TextView pintro;

        public CompleteCommentViewHolder(View base) {
            pic = (ImageView) base.findViewById(R.id.home_cat_image);
            pname = (TextView) base.findViewById(R.id.home_cat_title);
            //pintro = (TextView) base.findViewById(R.id.home_cat_note);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        CompleteCommentViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.row_home_category, null);
            viewHolder = new CompleteCommentViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (CompleteCommentViewHolder) v.getTag();
        }

        final Homecategory pp = mList.get(position);
        final String outpic = pp.getImageurl();
        final String thename = pp.getName();
        viewHolder.pname.setText(pp.getName());
        //viewHolder.pintro.setText(pp.getIntro());
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        // Loading image with placeholder and error image
        imageLoader.get(Globals.images+pp.getImageurl(), ImageLoader.getImageListener(
                viewHolder.pic, R.mipmap.carter, R.mipmap.carter));
        System.out.print(Globals.images+pp.getImageurl());

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for now
                Intent det = new Intent(context, Outlets.class);
                Globals.typeofoutlet = thename;
                //det.putExtra("homeoutcat", thename);
                context.startActivity(det);
            }
        });

        return v;
    }
}
