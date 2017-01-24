package cartbolt.qui.screens;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;

import cartbolt.qui.screens.drawer.HomeFragment;
import cartbolt.qui.screens.drawer.OrdersFragment;
import cartbolt.utils.AppController;
import cartbolt.utils.Globals;

public class Homer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        LinearLayout viewGroup = (LinearLayout) findViewById(R.id.popup);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headerlayout = inflater.inflate(R.layout.nav_header_homer, viewGroup);
        ImageView headerpic = (ImageView) headerlayout.findViewById(R.id.imageViewProf);
        TextView headername = (TextView) headerlayout.findViewById(R.id.userNameProf);
        TextView headeremail= (TextView) headerlayout.findViewById(R.id.emailProf);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String check = prefs.getString("usertype", "");
        if (check.contentEquals("skip")) {
            //Toast.makeText(this, "You need to be logged in to complete this", Toast.LENGTH_LONG).show();
        } else {
            headername.setText(PreferenceManager.getDefaultSharedPreferences(this).getString("username", "Cartbolt"));
            headeremail.setText(PreferenceManager.getDefaultSharedPreferences(this).getString("useremail", "support@cartbolt.ug"));
            ImageLoader imageLoader = AppController.getInstance().getImageLoader();

            imageLoader.get(Globals.server + "/images/owners/" + PreferenceManager.getDefaultSharedPreferences(this).getString("userpic", ""), ImageLoader.getImageListener(
                    headerpic, R.drawable.cartboltlogo, R.drawable.cartboltlogo));
        }
        navView.addHeaderView(headerlayout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //initialize to Home Fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.flContent, new HomeFragment()).commit();
        getSupportActionBar().setTitle("Cartbolt");

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.homer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (item.getItemId() == android.R.id.home) {

			/*
			 * Intent carter = new Intent(this, Begin.class);
			 * startActivity(carter);
			 */
            onBackPressed();

        }

        // new - for menu actions //you can as well just use a switch...
        switch (item.getItemId()) {
            /*case R.id.action_search:
                // Intent carter = new Intent(this, Search.class);
                // startActivity(carter);
                return true;*/
            case R.id.action_cart:
                Intent cart = new Intent(this, Carty.class);
                startActivity(cart);
                return true;
            case R.id.action_settings:
                startActivity(new Intent(this, Signup.class));
                return true;
            case R.id.actions_about:
                startActivity(new Intent(this, About.class));
                return true;
            case R.id.actions_contact:
                startActivity(new Intent(this, Contactus.class));
                return true;
            case R.id.actions_help:
                startActivity(new Intent(this, Help.class));
                return true;
            case R.id.actions_logout:
                SharedPreferences prefs = PreferenceManager
                        .getDefaultSharedPreferences(this);
                SharedPreferences.Editor edit = prefs.edit();
                edit.clear().commit();
                Intent intent = new Intent(this, Start.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment thefragment = null;
        //Class fragmentClass = null;

        if(id == R.id.nav_camera) {
            thefragment =  new HomeFragment();
            //getSupportFragmentManager().beginTransaction().replace(R.id.flContent, fragment).commit();
        }else if (id == R.id.nav_gallery) {
            //fragment =  new OrdersFragment();
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            String check = prefs.getString("usertype", "");
            if (check.contentEquals("skip")) {
                Toast.makeText(this, "You need to be logged in to complete this", Toast.LENGTH_LONG).show();
            } else {
                //startActivity(new Intent(this, Help.class));
                thefragment =  new OrdersFragment();
                //getSupportFragmentManager().beginTransaction().replace(R.id.flContent, fragment).commit();
            }

        }else if(id == R.id.nav_manage) {
            //fragment =  new Results();
            startActivity(new Intent(this, Help.class));
        }else if(id == R.id.nav_share) {
            //fragment =  new CampusMap();
            startActivity(new Intent(this, About.class));
        }else if(id == R.id.nav_send) {
            //fragment =  new Finance();
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(this);
            SharedPreferences.Editor edit = prefs.edit();
            edit.clear().commit();
            Intent intent = new Intent(this, Start.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else{
            thefragment =  new HomeFragment();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (thefragment != null) {
            //FragmentManager fragmentManager = getFragmentManager();
            getSupportFragmentManager().beginTransaction().replace(R.id.flContent, thefragment).commit();

            //mDrawerList.setItemChecked(id, true);
            //mDrawerList.setSelection(id);
            //getActionBar().setTitle(mNavigationDrawerItemTitles[position]);
            getSupportActionBar().setTitle("Home");
            //mDrawerLayout.closeDrawer(mDrawerList);

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }

        drawer.closeDrawer(GravityCompat.START);

        /*try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }*

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        item.setChecked(true);
        // Set action bar title
        setTitle(item.getTitle());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);*/
        return true;
    }
}
