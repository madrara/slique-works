package com.example.flash.superkatale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class CartDetail extends AppCompatActivity {
    public static final String KEY_ID = "id";
    public static final String KEY_QNTY = "quantity";
    public static final String KEY_PRICE = "price";
    public static final String KEY_FUll = "fulldescription";
    public static final String KEY_PHONE = "phone_number";
    public static final String KEY_IMAGE = "image";

    private TextView tvphone;
    private TextView tvqnty;
    private TextView tvdescribe;
    private TextView tvid;
    private TextView tvimage;
    private TextView tvprice;
    private Button btneditcart;

    NetworkImageView imageView;
    private ImageLoader imageLoader;

    String phone;
    String qnty;
    String describe;
    String iid;
    String image;
    String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_detail);

        tvphone = (TextView)findViewById(R.id.tvphone);
        tvqnty = (TextView)findViewById(R.id.tvqnty);
        tvdescribe = (TextView)findViewById(R.id.tvdescribe);
        tvid =(TextView)findViewById(R.id.tvid);
        tvimage = (TextView)findViewById(R.id.tvimage);
        tvprice = (TextView)findViewById(R.id.tvprice);
        imageView = (NetworkImageView)findViewById(R.id.imageView);
        btneditcart = (Button)findViewById(R.id.btneditcart);



        btneditcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit = new Intent(getApplicationContext(),Edit_Cart.class);
                String image_url = ((TextView)findViewById(R.id.tvimage)).getText().toString();
                edit.putExtra(KEY_IMAGE,image_url);
                edit.putExtra(KEY_PHONE,phone);
                edit.putExtra(KEY_ID,iid);
                startActivityForResult(edit,100);
            }
        });

        Intent i = getIntent();
        phone = i.getStringExtra(KEY_PHONE);
        qnty = i.getStringExtra(KEY_QNTY);
        describe = i.getStringExtra(KEY_FUll);
        iid = i.getStringExtra(KEY_ID);
        price = i.getStringExtra(KEY_PRICE);
        image = i.getStringExtra(KEY_IMAGE);

        tvphone.setText(phone);
        tvqnty.setText(qnty);
        tvdescribe.setText(describe);
        tvid.setText(iid);
        tvimage.setText(image);
        tvprice.setText(price);

        LoadImage();
    }
    private void LoadImage(){
        String img_url = tvphone.getText().toString().trim();
        if(img_url.equals("")){
            Toast.makeText(this, "Please enter a URL", Toast.LENGTH_LONG).show();
            return;
        }
        imageLoader = CustomVolleyRequest.getInstance(this.getApplicationContext())
                .getImageLoader();
        imageLoader.get(img_url, ImageLoader.getImageListener(imageView,
                R.drawable.shop, android.R.drawable
                        .ic_dialog_alert));
        imageView.setImageUrl(img_url, imageLoader);

    }

}
