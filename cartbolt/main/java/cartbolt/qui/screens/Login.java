package cartbolt.qui.screens;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cartbolt.qui.entities.User;
import cartbolt.utils.AppController;
import cartbolt.utils.Globals;

public class Login extends AppCompatActivity {

    private EditText startemail, startpassword;
    private TextView startloginbtn, textViewsgn;
    private String email, password;

    private SharedPreferences prefs;
    private SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //alerter = new AlertClass(this);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        startemail = (EditText) findViewById(R.id.startemail);
        textViewsgn = (TextView) findViewById(R.id.textViewsgn);
        startpassword = (EditText) findViewById(R.id.startpassword);
        startloginbtn = (TextView) findViewById(R.id.loginsubmit);
        startloginbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                email = startemail.getText().toString();
                password = startpassword.getText().toString();
                //if(checkInternetConnection()){
                    getStarted();
                //} else {
                    //Toast.makeText(Login.this, "Unable to connect to the internet", Toast.LENGTH_LONG).show();
                //}
            }
        });

        textViewsgn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Signup.class));
                Login.this.finish();
            }
        });

    }

    private void getStarted(){
        // Tag used to cancel the request
        String tag_json_arry = "json_array_req";

        String url = Globals.server+"/login?email="+email+"&password="+password;

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Log.d(TAG, response.toString());
                        List<User> results = new ArrayList<User>();
                        try{
                            JSONArray json = new JSONArray(response.toString());
                            System.out.println("#2 JSON: " + json.toString());
                            //result.clear();
                            for(int i=0; i<json.length(); i++){
                                JSONObject chimps = json.getJSONObject(i);
                                User mme = new User(chimps.get("id").toString(), chimps.get("fname").toString(),
                                        chimps.get("sname").toString(),  chimps.get("email").toString(),
                                        chimps.get("phone").toString(), chimps.get("location").toString());
                                //mme.setImage(chimps.get("pic").toString());;
                                //mme.setSplitter(chimps.get("caselevel").toString());
                                results.add(i, mme);
                            }
                        }catch (JSONException jss){

                        }

                        if(results.isEmpty()){
                            pDialog.hide();
                            Toast.makeText(Login.this, "No results not found.", Toast.LENGTH_LONG).show();
                        } else {
                            edit = prefs.edit();
                            edit.putString("userid", results.get(0).getId());
                            edit.putString("usertype", "user");
                            edit.putString("useremail", results.get(0).getEmail());
                            edit.putString("userphone", results.get(0).getPhone());
                            edit.putString("username", results.get(0).getFname() +" " +results.get(0).getSurname());
                            edit.putString("location", results.get(0).getLocation());
                            edit.putBoolean("firstTime", false);
                            edit.commit();
                            pDialog.hide();
                            Toast.makeText(Login.this, "Login successfull", Toast.LENGTH_LONG).show();
                            System.out.println("Done with this");
                            Intent i = new Intent(Login.this, Homer.class);
                            startActivity(i);
                            Login.this.finish();
                        }
                        //end
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //VolleyLog.d(TAG, "Error: " + error.getMessage());
                pDialog.hide();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req, tag_json_arry);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Login.this.finish();
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
            startActivity(new Intent(Login.this, Start.class));
            this.finish();

        }

        return super.onOptionsItemSelected(item);
    }

}
