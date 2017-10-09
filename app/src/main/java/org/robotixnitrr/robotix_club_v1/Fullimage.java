package org.robotixnitrr.robotix_club_v1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebBackForwardList;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Fullimage extends AppCompatActivity {
WebView vwi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullimage);
        Intent intent= getIntent();
        vwi = (WebView) findViewById(R.id.web_img);
        vwi.setWebViewClient(new WebViewClient());
        String url = intent.getStringExtra("url");
        vwi.getSettings().setLoadWithOverviewMode(true);
        vwi.getSettings().setUseWideViewPort(true);
        vwi.loadUrl(url);
    }
}
