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

public class Signup extends AppCompatActivity {

    private SharedPreferences prefs;
    private SharedPreferences.Editor edit;
    //AlertClass alerter;

    private EditText regsurname, regfirstname, regemail, regpassword, regtestpassword, regphone, reglocation;
    private String name, surname, firstname, email, password, testpassword, phone, location;
    private TextView regnow;
    private String proceed = "true";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //alerter = new AlertClass(this);

        regsurname = (EditText) findViewById(R.id.surname);
        regfirstname = (EditText) findViewById(R.id.firstname);
        regemail = (EditText) findViewById(R.id.regemail);
        regpassword = (EditText) findViewById(R.id.regpassword);
        regtestpassword = (EditText) findViewById(R.id.regtestpassword);
        regphone = (EditText) findViewById(R.id.regphone);
        reglocation = (EditText) findViewById(R.id.reglocation);

        regnow = (TextView) findViewById(R.id.signupsubmit);
        regnow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                surname = regsurname.getText().toString();
                firstname = regfirstname.getText().toString();
                name = surname+firstname;
                email = regemail.getText().toString();
                password = regpassword.getText().toString();
                testpassword = regtestpassword.getText().toString();
                phone = regphone.getText().toString();
                location = reglocation.getText().toString();


                if (!(password.contentEquals(testpassword))) {
                    Toast.makeText(Signup.this, "Password didn't match", Toast.LENGTH_SHORT).show();
                    //proceed = "false";
                } else if (surname.length() < 4) {
                    Toast.makeText(Signup.this, "Surname is too short", Toast.LENGTH_SHORT).show();
                    //proceed = "false";
                } else if(firstname.length() < 4){
                    Toast.makeText(Signup.this, "First name too short", Toast.LENGTH_SHORT).show();
                }else if (password.length() < 5) {
                    Toast.makeText(Signup.this, "Password is too short", Toast.LENGTH_SHORT).show();
                    //proceed = "false";
                } else if (!(email.contains("@")) || !(email.contains("."))) {
                    Toast.makeText(Signup.this, "Email is invalid", Toast.LENGTH_SHORT).show();
                    //proceed = "false";
                } else /*if(proceed.contentEquals("true"))*/ {
                    //if(checkInternetConnection()){
                        name = surname+"%20"+firstname;
                        submit();
                    //} else {
                        //Toast.makeText(Signup.this, "Unable to connect to the internet", Toast.LENGTH_LONG).show();
                    //}
                }/* else {
					Toast.makeText(Signup.this, "Please check your input and try again!", Toast.LENGTH_SHORT).show();
				}*/

                //phone shouldn't be empty, location

            }
        });

    }

    private void submit(){
        // Tag used to cancel the request
        String tag_json_arry = "json_array_req";

        String url = Globals.server+"/signup?fname="+firstname+"&sname="+surname+"&email="+email+"&password="+password
                +"&phone="+phone+"&location="+location;

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
                            Toast.makeText(Signup.this, "No results not found.", Toast.LENGTH_LONG).show();
                        } else {
                            edit = prefs.edit();
                            edit.putString("userid", results.get(0).getId());
                            edit.putString("usertype", "user");
                            edit.putString("username", name);
                            edit.putString("useremail", results.get(0).getEmail());
                            edit.putString("userphone", results.get(0).getPhone());
                            edit.putString("location", location);
                            edit.putBoolean("firstTime", false);
                            edit.commit();
                            pDialog.hide();
                            Toast.makeText(Signup.this, "Registration successfull", Toast.LENGTH_LONG).show();
                            System.out.println("Done with this");
                            Intent i = new Intent(Signup.this, Homer.class);
                            startActivity(i);
                            Signup.this.finish();
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
        Signup.this.finish();
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
            startActivity(new Intent(Signup.this, Start.class));
            this.finish();

        }

        return super.onOptionsItemSelected(item);
    }

}
