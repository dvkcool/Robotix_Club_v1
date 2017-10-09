package org.robotixnitrr.robotix_club_v1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.PhoneNumber;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Locale;

public class Works_Detailer extends AppCompatActivity {
WebView vw;
    TextView nm;
  EditText des;
    ProgressDialog dialog;
String url;
    String user;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_works__detailer);
        vw = (WebView) findViewById(R.id.imagevi);
        nm = (TextView) findViewById(R.id.name);
        des = (EditText) findViewById(R.id.descr);
        des.setKeyListener(null);
        Intent intent= getIntent();

        vw.setWebViewClient(new WebViewClient());
          url = intent.getStringExtra("img");
        vw.getSettings().setLoadWithOverviewMode(true);
        vw.getSettings().setUseWideViewPort(true);
        vw.loadUrl(url);
        des.setText(intent.getStringExtra("desc"));

        nm.setText(intent.getStringExtra("name"));
        user = intent.getStringExtra("id");
        dialog = ProgressDialog.show(this, "Loading", "Please wait...", true);
        dialog.dismiss();
    }
   public String cr_st(){
        String s1="insert into regis values(\""+ nm.getText()+"\", \"";

        s1 = s1+ user +"\");";
        return s1;
    }
    class con extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();

        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try
            {
                Class.forName("com.mysql.jdbc.Driver");
                Connection cn = DriverManager.getConnection("jdbc:mysql://sg2plcpnl0175.prod.sin2.secureserver.net:3306/postnitrr", "robotix2017","Robotixrocks@2017");
                Statement st= cn.createStatement();
                String s =cr_st();

                st.executeUpdate(s);

            }
            catch (Exception e)
            {
                String toastMessage = e.getMessage();
                Toast.makeText(Works_Detailer.this, toastMessage, Toast.LENGTH_LONG).show();

            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            dialog.dismiss();
            Toast.makeText(Works_Detailer.this, "You have succesfully registered", Toast.LENGTH_LONG).show();
            Intent in = new Intent(Works_Detailer.this, AccountActivity.class);
            startActivity(in);
        }
    }

    public void onreg(View view){
        con ce = new con();
        ce.execute();

    }
    public void imgfull(View view){
        Intent intent = new Intent(this, Fullimage.class);
        intent.putExtra("url",url);
        startActivity(intent);
    }
}
