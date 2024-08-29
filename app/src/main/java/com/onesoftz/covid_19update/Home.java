package com.onesoftz.covid_19update;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import android.content.ActivityNotFoundException;
import android.content.Intent;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.material.navigation.NavigationView;
import com.onesoftz.covid_19update.databinding.ActivityHomeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Objects;


public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActivityHomeBinding binding;
    private int worldWideTotalConfirmed, worldWideTotalConfirmedUpdated;
    private Toast mToast = null;
    private String locale;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Adds the main layout.
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Adding custom toolbar.
        Toolbar mainToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mainToolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("COVID-19 Update");

        // toggle button for drawer layout
        // Click events to nav view
        binding.navView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, mainToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        if (savedInstanceState == null) {
            binding.navView.setCheckedItem(R.id.nav_home);

        }


        worldwideDataFetcher();
        countryDataFetcher();

    }

    private void worldwideDataFetcher() {

        String url = "https://corona.lmao.ninja/v2/all/";


        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            NumberFormat nF = NumberFormat.getInstance();
                            nF.setGroupingUsed(true);


                            binding.worldwideTotalConfirmed.setText(nF.format(Double.parseDouble(jsonObject.getString("cases"))));
                            worldWideTotalConfirmed = Integer.parseInt(jsonObject.getString("cases")); // adds WorldWideTotalConfirmed data to member variable worldWideTotalConfirmed
                            binding.worldwideTotalRecovered.setText(nF.format(Double.parseDouble(jsonObject.getString("recovered"))));
                            binding.worldwideTotalDeaths.setText(nF.format(Double.parseDouble(jsonObject.getString("deaths"))));
                            binding.worldwideTodayConfirmed.setText(nF.format(Double.parseDouble(jsonObject.getString("todayCases"))));
                            binding.worldwideTodayDeaths.setText(nF.format(Double.parseDouble(jsonObject.getString("todayDeaths"))));
                            binding.worldwideTodayRecovered.setText(nF.format(Double.parseDouble(jsonObject.getString("todayRecovered"))));



                   /*         binding.tvCases.setText(nF.format(Double.parseDouble(jsonObject.getString("cases"))));
                            binding.tvRecovered.setText(nF.format(Double.parseDouble(jsonObject.getString("recovered"))));
                            binding.tvCritical.setText(nF.format(Double.parseDouble(jsonObject.getString("critical"))));
                            binding.tvActive.setText(nF.format(Double.parseDouble(jsonObject.getString("active"))));
                            binding.tvTodayCases.setText(nF.format(Double.parseDouble(jsonObject.getString("todayCases"))));
                            binding.tvTotalDeaths.setText(nF.format(Double.parseDouble(jsonObject.getString("deaths"))));
                            binding.tvTodayDeaths.setText(nF.format(Double.parseDouble(jsonObject.getString("todayDeaths"))));
                            binding.tvAffectedCountries.setText(nF.format(Double.parseDouble(jsonObject.getString("affectedCountries"))));
/*
                            binding.tvCases.setText(jsonObject.getString("cases"));
                            binding.tvRecovered.setText(jsonObject.getString("recovered"));
                            binding.tvCritical.setText(jsonObject.getString("critical"));
                            binding.tvActive.setText(jsonObject.getString("active"));
                            binding.tvTodayCases.setText(jsonObject.getString("todayCases"));
                            binding.tvTotalDeaths.setText(jsonObject.getString("deaths"));
                            binding.tvTodayDeaths.setText(jsonObject.getString("todayDeaths"));
                            binding.tvAffectedCountries.setText(jsonObject.getString("affectedCountries"));


                            binding.piechart.addPieSlice(new PieModel("Cases", Integer.parseInt(binding.tvCases.getText().toString()), Color.parseColor("#FFA726")));
                            binding.piechart.addPieSlice(new PieModel("Recovered", Integer.parseInt(binding.tvRecovered.getText().toString()), Color.parseColor("#66BB6A")));
                            binding.piechart.addPieSlice(new PieModel("Deaths", Integer.parseInt(binding.tvTotalDeaths.getText().toString()), Color.parseColor("#EF5350")));
                            binding.piechart.addPieSlice(new PieModel("Active", Integer.parseInt(binding.tvActive.getText().toString()), Color.parseColor("#29B6F6")));
                            binding.piechart.startAnimation();
*/
                /*            binding.piechart.addPieSlice(new PieModel("Cases", Integer.parseInt(jsonObject.getString("cases")), Color.parseColor("#FFA726")));
                            binding.piechart.addPieSlice(new PieModel("Recovered", Integer.parseInt(jsonObject.getString("recovered")), Color.parseColor("#66BB6A")));
                            binding.piechart.addPieSlice(new PieModel("Deaths", Integer.parseInt(jsonObject.getString("deaths")), Color.parseColor("#EF5350")));
                            binding.piechart.addPieSlice(new PieModel("Active", Integer.parseInt(jsonObject.getString("active")), Color.parseColor("#29B6F6")));
                            binding.piechart.startAnimation();
*/


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Home.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);


    }

    private void countryDataFetcher() {

        locale = this.getResources().getConfiguration().locale.getCountry();

        String url = "https://corona.lmao.ninja/v2/countries/";


        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String countryName = jsonObject.getString("country");
                                String cases = jsonObject.getString("cases");
                                String todayCases = jsonObject.getString("todayCases");
                                String deaths = jsonObject.getString("deaths");
                                String todayDeaths = jsonObject.getString("todayDeaths");
                                String recovered = jsonObject.getString("recovered");
                                String todayRecovered = jsonObject.getString("todayRecovered");

                                JSONObject object = jsonObject.getJSONObject("countryInfo");

                                String iso2 = object.getString("iso2");

                                if (iso2.equals(locale)) {
                                    NumberFormat nF = NumberFormat.getInstance();
                                    nF.setGroupingUsed(true);
                                    binding.ownCountryName.setText(countryName);
                                    binding.countryTotalConfirmed.setText(nF.format(Double.parseDouble(cases)));
                                    binding.countryTotalDeaths.setText(nF.format(Double.parseDouble(deaths)));
                                    binding.countryTotalRecovered.setText(nF.format(Double.parseDouble(recovered)));
                                    binding.countryTodayConfirmed.setText(nF.format(Double.parseDouble(todayCases)));
                                    binding.countryTodayDeaths.setText(nF.format(Double.parseDouble(todayDeaths)));
                                    binding.countryTodayRecovered.setText(nF.format(Double.parseDouble(todayRecovered)));
                                    break;
                                }


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

    private void worldWideTotalConfirmedUpdater() {

        String url = "https://corona.lmao.ninja/v2/all/";


        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            NumberFormat nF = NumberFormat.getInstance();
                            nF.setGroupingUsed(true);


                            String sWorldWideTotalConfirmedUpdated = jsonObject.getString("cases");
                            worldWideTotalConfirmedUpdated = Integer.parseInt(sWorldWideTotalConfirmedUpdated);


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Home.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);


    }

    // Adds top right overflow menu xml.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    // Listener for option item
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case R.id.action_refresh:
                refresher();
                return true;

            case R.id.action_about:
                startActivity(new Intent(this, About.class));
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                Toast.makeText(this, "Clicked Home", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_more_apps:
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://dev?id=8427095636698382674")));

                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/dev?id=8427095636698382674")));
                    e.printStackTrace();
                }

                break;
            case R.id.nav_rate_app:

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + getPackageName())));

                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                    e.printStackTrace();
                }


                break;

            case R.id.nav_share:

                Intent appSharingIntent = new Intent();
                appSharingIntent.setAction(Intent.ACTION_SEND);
                appSharingIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out this flashlight app at: https://play.google.com/store/apps/details?id=com.onesoftz.flashlight");
                appSharingIntent.setType("text/plain");
                startActivity(Intent.createChooser(appSharingIntent, "Share \"COVID-19 Update\" via"));

                break;

            case R.id.nav_checkForUpdate:

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + getPackageName())));

                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                    e.printStackTrace();
                }

                break;

            case R.id.nav_exit:
                QuitApp();
                break;
            case R.id.nav_about:
                startActivity(new Intent(this, About.class));

                break;

        }

        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    public void goTrackCountries(View view) {
        startActivity(new Intent(getApplicationContext(), AffectedCountries.class));
    }

    public void refresher() {


        worldWideTotalConfirmedUpdater();
        if (worldWideTotalConfirmed == worldWideTotalConfirmedUpdated) {
            if (mToast != null) {
                mToast.cancel();

            }
            mToast = Toast.makeText(this, "Check Sometime Later", Toast.LENGTH_SHORT);
            mToast.show();
        } else {
            worldwideDataFetcher();
            countryDataFetcher();
        }


    }

    public void QuitApp() {
        Home.this.finish();
        System.exit(0);

    }

}