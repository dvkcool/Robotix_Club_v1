package org.robotixnitrr.robotix_club_v1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by dvk on 26/09/17.
 */
public class NewWorkshops extends AppCompatActivity {
RecyclerView recyclerview;
    ProgressDialog dialog;
    Wor_Adapter w1;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_workshops);
         Intent intent = getIntent();
        id = intent.getStringExtra("userid");

        dialog = ProgressDialog.show(this, "Loading", "Please wait...", true);
        con cq = new con();
        cq.execute();
        w1 = new Wor_Adapter();

        recyclerview = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerview.setAdapter(w1);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
    }
ResultSet r2;
    String wor_list[]= new String[1000];
    String wor_img[]= new String[1000];
    String wor_des[]= new String[1000];
    int length;
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


                ResultSet r= st.executeQuery("select * from workshop;");
                r2 =r;
                int j=0;
                while(r.next()){
                    wor_list[j]= r.getString(1);
                    wor_img[j] = r.getString(2);
                    wor_des[j] = r.getString(3);
                    j++;

                }
                length =j;
            }
            catch (Exception e)
            {
                String toastMessage = e.getMessage();
                Toast.makeText(NewWorkshops.this, toastMessage, Toast.LENGTH_LONG).show();

            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            dialog.dismiss();
            w1.setData();
        }
    }
    public class Wor_Adapter extends RecyclerView.Adapter<Workshop_view_holder>{
String[] w_list;

        @Override
        public Workshop_view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
           View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workshoplist2, parent, false);

            return new Workshop_view_holder(view);
        }

        @Override
        public void onBindViewHolder(Workshop_view_holder holder, final int position) {
           holder.wor_name.setText(wor_list[position]);
            holder.cardView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {

                              Intent intent = new Intent(NewWorkshops.this, Works_Detailer.class);
                              intent.putExtra("name", wor_list[position]);
                              intent.putExtra("img", wor_img[position]);
                              intent.putExtra("desc", wor_des[position]);
                                intent.putExtra("id", id);
                              startActivity(intent);


                }
            });
        }

        @Override
        public int getItemCount() {
            return length;
        }
        public void setData(){
            this.w_list = wor_list;
            this.notifyDataSetChanged();
        }
    }
}
