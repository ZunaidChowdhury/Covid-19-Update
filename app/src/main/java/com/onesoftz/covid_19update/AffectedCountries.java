package com.onesoftz.covid_19update;

import androidx.annotation.NonNull;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.onesoftz.covid_19update.databinding.ActivityAffectedCountriesBinding;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AffectedCountries extends AppCompatActivity {

    ActivityAffectedCountriesBinding binding;

    public static List<CountryModel> countryModelsList = new ArrayList<>();
    CountryModel countryModel;
    MyCustomAdapter myCustomAdapter;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Adds the main layout.
        binding = ActivityAffectedCountriesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Adding custom toolbar.
        Toolbar mainToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mainToolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Affected Countries");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fetchData();

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(),DetailsActivity.class).putExtra("position",position));
            }
        });


        binding.editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                myCustomAdapter.getFilter().filter(s);
                myCustomAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void fetchData() {

        String url  = "https://corona.lmao.ninja/v2/countries/";

        binding.loader.start();

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for(int i=0;i<jsonArray.length();i++){

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String countryName = jsonObject.getString("country");
                                String cases = jsonObject.getString("cases");
                                String todayCases = jsonObject.getString("todayCases");
                                String deaths = jsonObject.getString("deaths");
                                String todayDeaths = jsonObject.getString("todayDeaths");
                                String recovered = jsonObject.getString("recovered");
                                String todayRecovered = jsonObject.getString("todayRecovered");
                                String active = jsonObject.getString("active");
                                String critical = jsonObject.getString("critical");


                                String continent = jsonObject.getString("continent");
                                String population = jsonObject.getString("population");
                                String tests = jsonObject.getString("tests");
                                String testsPerOneMillion = jsonObject.getString("testsPerOneMillion");
                                String casesPerOneMillion = jsonObject.getString("casesPerOneMillion");
                                String deathsPerOneMillion = jsonObject.getString("deathsPerOneMillion");
                                String oneCasePerPeople = jsonObject.getString("oneCasePerPeople");
                                String oneDeathPerPeople = jsonObject.getString("oneDeathPerPeople");
                                String oneTestPerPeople = jsonObject.getString("oneTestPerPeople");
                                String activePerOneMillion = jsonObject.getString("activePerOneMillion");
                                String recoveredPerOneMillion = jsonObject.getString("recoveredPerOneMillion");
                                String criticalPerOneMillion = jsonObject.getString("criticalPerOneMillion");



                                JSONObject object = jsonObject.getJSONObject("countryInfo");
                                String flagUrl = object.getString("flag");

                                countryModel = new CountryModel(flagUrl,countryName,cases,todayCases,deaths,todayDeaths,recovered,active,critical, todayRecovered, continent, population, tests,
                                        testsPerOneMillion, casesPerOneMillion, deathsPerOneMillion, oneCasePerPeople, oneDeathPerPeople, oneTestPerPeople, activePerOneMillion, recoveredPerOneMillion,
                                        criticalPerOneMillion);
                                countryModelsList.add(countryModel);


                            }

                            myCustomAdapter = new MyCustomAdapter(AffectedCountries.this,countryModelsList);
                            binding.listView.setAdapter(myCustomAdapter);
                            binding.loader.stop();
                            binding.loader.setVisibility(View.GONE);






                        } catch (JSONException e) {
                            e.printStackTrace();
                            binding.loader.stop();
                            binding.loader.setVisibility(View.GONE);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                binding.loader.stop();
                binding.loader.setVisibility(View.GONE);
                Toast.makeText(AffectedCountries.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }
}