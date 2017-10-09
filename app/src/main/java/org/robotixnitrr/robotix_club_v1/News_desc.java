package org.robotixnitrr.robotix_club_v1;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import android.widget.TextView;
/**
 * Created by dvk on 06/10/17.
 */
public class News_desc extends AppCompatActivity {
    WebView vw;
    TextView nm;
    EditText des;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_desc);
        vw = (WebView) findViewById(R.id.news_imagevi);
        nm = (TextView) findViewById(R.id.news_name);


        des = (EditText) findViewById(R.id.news_descr);
        des.setKeyListener(null);

        Intent intent= getIntent();

        vw.setWebViewClient(new WebViewClient());
         url = intent.getStringExtra("img");
        vw.getSettings().setLoadWithOverviewMode(true);
        vw.getSettings().setUseWideViewPort(true);
        vw.loadUrl(url);
        des.setText(intent.getStringExtra("desc"));

        nm.setText(intent.getStringExtra("name"));
    }
    public void imgfull(View view){
        Intent intent = new Intent(this, Fullimage.class);
        intent.putExtra("url",url);
        startActivity(intent);
    }
}
