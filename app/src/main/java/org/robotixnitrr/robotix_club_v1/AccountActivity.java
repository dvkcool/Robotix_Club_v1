package org.robotixnitrr.robotix_club_v1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
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

public class AccountActivity extends BaseActivity {


    TextView infoLabel;
    TextView info;
    String s=""; int c=0, t=0;
    String n="";
    TextView name;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        ActionBar actionBar = getSupportActionBar();// for displaying menu button
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setTitle("Home");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        name = (TextView) findViewById(R.id.name);
        infoLabel = (TextView) findViewById(R.id.info_label);
        info = (TextView) findViewById(R.id.info);
        dialog = ProgressDialog.show(this, "Loading", "Please wait...", true);

        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(final Account account) {

                String accountKitId = account.getId();


                PhoneNumber phoneNumber = account.getPhoneNumber();
                if (account.getPhoneNumber() != null) {
                    String formattedPhoneNumber = formatPhoneNumber(phoneNumber.toString());
                    s= formattedPhoneNumber;
                    info.setText(formattedPhoneNumber);
                    infoLabel.setText(R.string.phone_label);
                    con cq = new con();
                    cq.execute();

                }
                else {
                    String emailString = account.getEmail();
                    s= emailString;
                    info.setText(emailString);
                    infoLabel.setText(R.string.email_label);
                    con cq = new con();
                    cq.execute();
                    t++;
                }

            }

            @Override
            public void onError(final AccountKitError error) {

                String toastMessage = error.getErrorType().getMessage();
                Toast.makeText(AccountActivity.this, toastMessage, Toast.LENGTH_LONG).show();
            }
        });
    }
    public void onLogout(View view) {

        AccountKit.logOut();
        launchLoginActivity();
    }

    private void launchLoginActivity() {
        Intent intent = new Intent(this, LoginScreen.class);
        startActivity(intent);
        finish();
    }

    public void n_wors(View view){
        Intent intent = new Intent(this, NewWorkshops.class);
        intent.putExtra("userid", s);
        startActivity(intent);
    }

    private String formatPhoneNumber(String phoneNumber) {

        try {
            PhoneNumberUtil pnu = PhoneNumberUtil.getInstance();
            Phonenumber.PhoneNumber pn = pnu.parse(phoneNumber, Locale.getDefault().getCountry());
            phoneNumber = pnu.format(pn, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
            String mob="";
            int l = phoneNumber.length();
            for(int i=0; i<l; i++){
                char c = phoneNumber.charAt(i);
                if(c!=' '){
                    mob= mob+c;
                }
            }
            phoneNumber=mob;
        }
        catch (NumberParseException e) {
            e.printStackTrace();
        }
        return phoneNumber;
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
                ResultSet r= st.executeQuery("select userid, name from user;");
                while(r.next()){
                    String s1= r.getString(1);
                    if(s.equals(s1)){
                        c++;
                        n = r.getString(2);
                    }
                }
            }
            catch (Exception e)
            {
                String toastMessage = e.getMessage();
                Toast.makeText(AccountActivity.this, toastMessage, Toast.LENGTH_LONG).show();

            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
           dialog.dismiss();
            if(c==0){
                registeru();
            }
            else{
                name.setText("Welcome "+n);
            }
        }
    }
    public void registeru(){
        Intent intent = new Intent(this, Register.class);
        intent.putExtra("userid", s);
        if(t==0){
            intent.putExtra("mobile1", true);
            intent.putExtra("mobile", s);
        }
        else{
            intent.putExtra("email1", true);
            intent.putExtra("email", s);
        }
        startActivity(intent);

    }

}
