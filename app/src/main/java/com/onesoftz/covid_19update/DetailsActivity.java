package com.onesoftz.covid_19update;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.onesoftz.covid_19update.databinding.ActivityDetailsBinding;

import java.text.NumberFormat;
import java.util.Objects;

public class DetailsActivity extends AppCompatActivity {

    ActivityDetailsBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Adds the main layout.
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent intent = getIntent();
        int positionCountry = intent.getIntExtra("position", 0);

        // Adding custom toolbar.
        Toolbar mainToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mainToolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Details of " + AffectedCountries.countryModelsList.get(positionCountry).getCountry());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        NumberFormat nF = NumberFormat.getInstance();
        nF.setGroupingUsed(true);

        binding.chosenCountry.setText(AffectedCountries.countryModelsList.get(positionCountry).getCountry());
        binding.chosenCountryTotalConfirmed.setText(nF.format(Double.parseDouble(AffectedCountries.countryModelsList.get(positionCountry).getCases())));
        binding.chosenCountryTotalDeaths.setText(nF.format(Double.parseDouble(AffectedCountries.countryModelsList.get(positionCountry).getDeaths())));
        binding.chosenCountryTotalRecovered.setText(nF.format(Double.parseDouble(AffectedCountries.countryModelsList.get(positionCountry).getRecovered())));
        binding.chosenCountryTodayConfirmed.setText(nF.format(Double.parseDouble(AffectedCountries.countryModelsList.get(positionCountry).getTodayCases())));
        binding.chosenCountryTodayDeaths.setText(nF.format(Double.parseDouble(AffectedCountries.countryModelsList.get(positionCountry).getTodayDeaths())));
        binding.chosenCountryTodayRecovered.setText(nF.format(Double.parseDouble(AffectedCountries.countryModelsList.get(positionCountry).getTodayRecovered())));
        binding.chosenCountryActive.setText(nF.format(Double.parseDouble(AffectedCountries.countryModelsList.get(positionCountry).getActive())));
        binding.chosenCountryCritical.setText(nF.format(Double.parseDouble(AffectedCountries.countryModelsList.get(positionCountry).getCritical())));
        binding.chosenCountryContinent.setText(AffectedCountries.countryModelsList.get(positionCountry).getContinent());
        binding.chosenCountryPopulation.setText(nF.format(Double.parseDouble(AffectedCountries.countryModelsList.get(positionCountry).getPopulation())));
        binding.chosenCountryTests.setText(nF.format(Double.parseDouble(AffectedCountries.countryModelsList.get(positionCountry).getTests())));
        binding.chosenCountryTestsPerOneMillion.setText(nF.format(Double.parseDouble(AffectedCountries.countryModelsList.get(positionCountry).getTestsPerOneMillion())));
        binding.chosenCountryCasesPerOneMillion.setText(nF.format(Double.parseDouble(AffectedCountries.countryModelsList.get(positionCountry).getCasesPerOneMillion())));
        binding.chosenCountryDeathsPerOneMillion.setText(nF.format(Double.parseDouble(AffectedCountries.countryModelsList.get(positionCountry).getDeathsPerOneMillion())));
        binding.chosenCountryOneCasePerPeople.setText(nF.format(Double.parseDouble(AffectedCountries.countryModelsList.get(positionCountry).getOneCasePerPeople())));
        binding.chosenCountryOneDeathPerPeople.setText(nF.format(Double.parseDouble(AffectedCountries.countryModelsList.get(positionCountry).getOneDeathPerPeople())));
        binding.chosenCountryOneTestPerPeople.setText(nF.format(Double.parseDouble(AffectedCountries.countryModelsList.get(positionCountry).getOneTestPerPeople())));
        binding.chosenCountryActivePerOneMillion.setText(nF.format(Double.parseDouble(AffectedCountries.countryModelsList.get(positionCountry).getActivePerOneMillion())));
        binding.chosenCountryRecoveredPerOneMillion.setText(nF.format(Double.parseDouble(AffectedCountries.countryModelsList.get(positionCountry).getRecoveredPerOneMillion())));
        binding.chosenCountryCriticalPerOneMillion.setText(nF.format(Double.parseDouble(AffectedCountries.countryModelsList.get(positionCountry).getCriticalPerOneMillion())));

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}