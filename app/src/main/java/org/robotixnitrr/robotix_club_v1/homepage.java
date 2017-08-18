package org.robotixnitrr.robotix_club_v1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.facebook.accountkit.AccountKit;

public class homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
    }
    public void onLogout(View view) {
        // logout of Account Kit
        AccountKit.logOut();
        launchlogin();
    }
    public void launchlogin(){
        startActivity(new Intent(this, LoginScreen.class));
    }
}
