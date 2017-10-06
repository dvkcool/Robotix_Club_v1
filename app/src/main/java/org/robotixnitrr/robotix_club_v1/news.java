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
public class news extends AppCompatActivity {
    RecyclerView recyclerview;
    ProgressDialog dialog;
    Wor_Adapter w1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        dialog = ProgressDialog.show(this, "Loading", "Please wait...", true);
        conn ce = new conn();
        ce.execute();
        w1 = new Wor_Adapter();

        recyclerview = (RecyclerView) findViewById(R.id.news_recyclerView);
        recyclerview.setAdapter(w1);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));



    }
    String news_list[]= new String[1000];
    String news_img[]= new String[1000];
    String news_des[]= new String[1000];
    int length=0;
    class conn extends AsyncTask<Void, Void, Boolean> {
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


                ResultSet r= st.executeQuery("select * from news;");
                int j=0;
                while(r.next()){
                   news_list[j]= r.getString(1);
                    news_img[j] = r.getString(2);
                    news_des[j] = r.getString(3);
                    j++;

                }
                length =j;
                cn.close();
                st.close();
            }
            catch (Exception e)
            {
                String toastMessage = e.getMessage();
                Toast.makeText(news.this, toastMessage, Toast.LENGTH_LONG).show();

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
            holder.wor_name.setText(news_list[position]);
            holder.cardView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(news.this, News_desc.class);
                    intent.putExtra("name", news_list[position]);
                    intent.putExtra("img", news_img[position]);
                    intent.putExtra("desc", news_des[position]);
                    startActivity(intent);

                }
            });
        }

        @Override
        public int getItemCount() {
            return length;
        }
        public void setData(){
            this.w_list = news_list;
            this.notifyDataSetChanged();
        }
    }
}
