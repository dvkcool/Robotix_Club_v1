package org.robotixnitrr.robotix_club_v1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


/**
 * Created by My on 19/06/17.
 */

/*
  For extending from navigation bar add the activity to be open,here
  1. make the layout of activity with parent drawer. and also add NavigationView for viewing menu icon
  2. make changes in changePage function here
  3. extend activity with Base Activity and make menu icon visible in the oncreate of the activity

 */

public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private NavigationView navigator;

    private Handler handler;   //for switching between threads

    private boolean close = false;

    private static final int NAVDRAWER_ITEM_INVALID = -1;



    private void changePage(int pos) {

        Log.v("BaseActivity","before clicked on it");
        switch (pos) {
            // example Use:
            //   case R.id.nav_item_b: startActivity(new Intent(this, ActivityB.class)); break;
            //   case R.id.nav_item_c: startActivity(new Intent(this, ActivityC.class)); break;
            case R.id.nav_home: startActivity(new Intent(this, LoginScreen.class)); break;
            case R.id.nav_about_us: startActivity(new Intent(this,About_us.class)); break;

        }

        finish();
    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new Handler();                   //currently on main thread
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

        drawer = (DrawerLayout) findViewById(R.id.drawer);
        navigator = (NavigationView) findViewById(R.id.navigation);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        navigator.setCheckedItem(getItemId());
        navigator.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)  // for icon in action bar
    {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            openNavDrawer();
            return true;
        } else if (item.getItemId() == R.id.action_call) {
            call();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
    private void call() //calls from phone
    {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + getResources().getString(R.string.phonenumber)));
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (isNavDrawerOpen()) {
            closeNavDrawer();
        } else {
            manageCloseEvent();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        if (item.getItemId() == getItemId()) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            int selectedId = item.getItemId();
            navigator.setCheckedItem(selectedId);
//            drawer.closeDrawer(GravityCompat.START);
            performScreenChange(selectedId);
        }
        return true;
    }

    int getItemId() {
        return NAVDRAWER_ITEM_INVALID;
    }

    private boolean isNavDrawerOpen() {
        return drawer != null && drawer.isDrawerOpen(GravityCompat.START);
    }

    private void openNavDrawer() {
        if (drawer != null) drawer.openDrawer(GravityCompat.START);
    }

    private void closeNavDrawer() {
        if (drawer != null) drawer.closeDrawer(GravityCompat.START);
    }

    private void performScreenChange(final int item) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changePage(item);
            }
        }, 250);
    }



    private void manageCloseEvent() {
        if (close) super.onBackPressed();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                close = false;
            }
        }, 1500);

        Toast.makeText(this, "message_close_app", Toast.LENGTH_SHORT).show();
        close = true;
    }

//    @Override
//    public void setSupportActionBar(@Nullable Toolbar toolbar) {
//        super.setSupportActionBar(toolbar);
//        // TODO Auto-generated method stub
//            ActionBar actionBar = getActionBar();
//            actionBar.setHomeButtonEnabled(true);
//            actionBar.setDisplayHomeAsUpEnabled(false);
//            actionBar.setDisplayShowHomeEnabled(false);
//            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
//            actionBar.setTitle("Robotix Club App");
//            actionBar.show();
//    }
}

