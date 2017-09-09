package org.robotixnitrr.robotix_club_v1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Region;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Register extends AppCompatActivity {
String mob="", email="", userid;
    String q="";
    EditText name, college, branch, sem, mobi, em;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = (EditText) findViewById(R.id.nm);
        college = (EditText) findViewById(R.id.clg);
        branch = (EditText) findViewById(R.id.branch);
        sem = (EditText) findViewById(R.id.sem);
        mobi = (EditText) findViewById(R.id.mobile);
        em = (EditText) findViewById(R.id.email);

        Intent intent= getIntent();
        if(intent.getBooleanExtra("mobile1", false)){
            mob = intent.getStringExtra("mobile");
            mobi.setText(mob);
        }
        if(intent.getBooleanExtra("email1", false)){
            email= intent.getStringExtra("email");
            em.setText(email);
        }
 userid = intent.getStringExtra("userid");


    }
    public void submitclc(View view) {
        do {
            String n = name.getText().toString();

            String c = college.getText().toString();
            String b = branch.getText().toString();
            String se = sem.getText().toString();
            String m = mobi.getText().toString();
            String e = em.getText().toString();
            if (n.length() == 0) {
                String toastMessage = "Please enter your name";
                Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
                break;
            }
            if (c.length() == 0) {
                String toastMessage = "Please enter your college";
                Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
                break;
            }
            if (b.length() == 0) {
                String toastMessage = "Please enter your branch";
                Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
                break;
            }
            if (se.length() == 0) {
                String toastMessage = "Please enter your semester";
                Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
                break;
            }
            if (se.length() > 2) {
                String toastMessage = "Please enter your valid semester e.g. 1, 2, 3,.., 8";
                Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
                break;
            }

            if (m.length() == 0) {
                String toastMessage = "Please enter your mobile number";
                Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
                break;
            }
            if (m.length() > 10) {
                String toastMessage = "Please enter valid mobile number";
                Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
                break;
            }
            if (e.length() == 0) {
                String toastMessage = "Please enter your email id";
                Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
                break;
            }
            q = "Insert into user values('" + userid + "', '" + n + "', '" + c + "', '" + b + "', '" + se + "', '" + m + "', '" + e + "');";
            con cq = new con();
            cq.execute();
        }while(false);
    }
    class con extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(Register.this, "Loading", "Please wait...", true);
            dialog.show();

        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try
            {
                Class.forName("com.mysql.jdbc.Driver");
                Connection cn = DriverManager.getConnection("jdbc:mysql://sg2plcpnl0175.prod.sin2.secureserver.net:3306/postnitrr", "robotix2017","Robotixrocks@2017");
                Statement st= cn.createStatement();
                st.executeUpdate(q);

            }
            catch (Exception e)
            {
                String toastMessage = e.getMessage();
                Toast.makeText(Register.this, toastMessage, Toast.LENGTH_LONG).show();

            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            dialog.dismiss();
            Toast.makeText(Register.this, "Succesfully registered", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Register.this, AccountActivity.class);
         startActivity(intent);

        }
    }

}
