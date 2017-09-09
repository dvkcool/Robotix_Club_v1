package org.robotixnitrr.robotix_club_v1;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Patterns;
import android.widget.EditText;

/**
 * Created by My on 23/06/17.
 */

public class CommonMethods {

    public static String USERNAME = "arg";

    Context context;

    public CommonMethods(){}
    public CommonMethods(Context mContext){
        context = mContext;
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public final static boolean isValidPhoneNumber(CharSequence target)  //checks for + as option also
    {
        if (target == null){
            return false;
        }
        else {
            return Patterns.PHONE.matcher(target).matches();
        }
    }
    public boolean checkIfEmpty(String string, EditText view){
        boolean check = true;
        if (string.equals("")){view.setError(context.getResources().getString(R.string.error_field_required)); check = false;}
        return check;
    }
    public  boolean isValidEmail(CharSequence target, EditText view) {
        if (target == null)
        {view.setError(context.getResources().getString(R.string.error_field_required));
            return false;
        } else {
            if( Patterns.EMAIL_ADDRESS.matcher(target).matches())
                return true;
            else
            {
                view.setError(context.getResources().getString(R.string.error_invalid_email));
                return false;
            }
        }}

}

