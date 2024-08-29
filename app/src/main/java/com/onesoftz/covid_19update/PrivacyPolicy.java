package com.onesoftz.covid_19update;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebViewClient;

import com.onesoftz.covid_19update.databinding.ActivityPrivacyPolicyBinding;

import java.util.Objects;

public class PrivacyPolicy extends AppCompatActivity {
    ActivityPrivacyPolicyBinding binding;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Adds the main layout.
        binding = ActivityPrivacyPolicyBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Adding custom toolbar.
        Toolbar mainToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mainToolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Privacy Policy");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.privacyPolicyWebView.setWebViewClient(new WebViewClient());
        binding.privacyPolicyWebView.loadUrl("https://sites.google.com/view/onesoftz/privacy-policy?authuser=0");

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (binding.privacyPolicyWebView.canGoBack()) {
            binding.privacyPolicyWebView.goBack();
        } else {
            super.onBackPressed();
        }

    }
}
