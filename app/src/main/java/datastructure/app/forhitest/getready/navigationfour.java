package datastructure.app.forhitest.getready;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class navigationfour extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private RecyclerView mRecyclerView;
    private mAdapter myAdapter;
    private ArrayList<itemcalss> mylistofitem;
    private AdView mAdView;
    Toast toastofmine;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigationfour);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);









        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mRecyclerView = findViewById(R.id.Recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mylistofitem = new ArrayList<>();
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        toastofmine=Toast.makeText(getApplicationContext(),"Loading...",Toast.LENGTH_LONG);
        parseJSON();




    }
    public void showdailog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(new ContextThemeWrapper(this,R.style.Theme_AppCompat_Light_Dialog_Alert));
        builder.setMessage("Opps! Something went wrong are you connected to internet ")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        moveTaskToBack(true);
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();




    }
    private void parseJSON() {
        toastofmine.show();

        String murl = "https://opentdb.com/api.php?amount=49&category=15&type=multiple";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, murl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

//                            JSONArray mjsonArray = response.getJSONArray("questions");
//
//                            for ( int i=0;i<mjsonArray.length();i++) {
//                                JSONObject jsonObjectinarray=mjsonArray.getJSONObject(i);
//
//                                String mquestion=jsonObjectinarray.getString("question");
//                                String manswer=jsonObjectinarray.getString("Answer");
//
//                                //.i("mquestion","is "+mquestion);
//
//
//                                mylistofitem.add(new itemcalss(mquestion, manswer));
//                            }
//
//                            myAdapter = new mAdapter(getApplicationContext(), mylistofitem);
//                            mRecyclerView.setAdapter(myAdapter);




                            String reponsecode=response.getString("response_code");
                            if (reponsecode.equals("0"))
                            {
                                toastofmine.cancel();


                                //.i("responsecpde","is"+reponsecode);
                                JSONArray array=response.getJSONArray("results");
                                for (int i=0;i<array.length();i++)
                                {
                                    JSONObject js=array.getJSONObject(i);
                                    String question =js.getString("question");
                                    //.i("question","is"+question);
                                    String answera=js.get("correct_answer").toString();
                                    Spanned aw=Html.fromHtml(answera);
                                    Spanned qu=Html.fromHtml(question);

                                    //.i("answera","is"+answera);
                                    String a=aw.toString();
                                    String q=qu.toString();

                                    mylistofitem.add(new itemcalss(q,a));


                                }
                                myAdapter = new mAdapter(getApplicationContext(), mylistofitem);
                                mRecyclerView.setAdapter(myAdapter);

                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Something Went wrong",Toast.LENGTH_SHORT).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showdailog();
            }
        });
        mysingleton.getInstance(getApplicationContext()).addToRequestque(jsonObjectRequest);


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
        getMenuInflater().inflate(R.menu.second_nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.refresh) {
            Toast.makeText(getApplicationContext(), "Refreshed", Toast.LENGTH_SHORT).show();

            Intent i=new Intent(getApplicationContext(),navigationfour.class);
            startActivity(i);
            finish();
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_suggest) {
            try {
                Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "ashadnasim123@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Suggestions");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(intent);

            }
            catch (Exception e)
            {
                Toast.makeText(getApplicationContext(),"Please try after sometime",Toast.LENGTH_SHORT).show();
            }

        }



        else if (id == R.id.nav_contactus) {
            try{

                Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "ashadnasim123@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Contact");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(intent);
            }
            catch (Exception e)
            {
                Toast.makeText(getApplicationContext(),"Please try after sometime",Toast.LENGTH_SHORT).show();

            }



        }  else if (id == R.id.nav_share) {


            //whtsapp
            try
            {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,Uri.parse("Hey !\n" +
                        "use this computer knowledge app to check your knowledge and learn new thing \n") + "http://play.google.com/store/apps/details?id="+this.getPackageName());
                Intent ic=Intent.createChooser(intent,"Want to share with");
                startActivity(ic);
            }
            catch (Exception e)
            {


                Toast.makeText(getApplicationContext(),"Something wents wrong ",Toast.LENGTH_SHORT).show();
            }



        }

        else if (id==R.id.rate)
        {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+this.getPackageName()
                )));
            }
            catch (android.content.ActivityNotFoundException e)
            {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://play.google.com/store/apps/details?id="+this.getPackageName())));
            }
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}