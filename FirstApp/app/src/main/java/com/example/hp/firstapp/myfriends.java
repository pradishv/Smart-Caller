package com.example.hp.firstapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;

import java.util.ArrayList;
import java.util.List;

public class myfriends extends Activity {
    ListView list;
    String useron;
    ProgressDialog progressDialog;
   String []web;
    ArrayList<String> circles=new ArrayList<>();
    String []names;
    String []images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfriends);
        list=(ListView)findViewById(R.id.listView);
        useron = getIntent().getExtras().getString("user");
        new MyTask().execute();

    }
    private class MyTask extends AsyncTask<String, Integer, String> {

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(myfriends.this, "Message", "Loading...");
        }

        // This is run in a background thread
        @Override
        protected String doInBackground(String... params) {
            System.out.println("Inside task");
            // get the string from params, which is an array
            try {
                CloudantClient client = ClientBuilder.account("9cdbab86-7715-4131-a5da-84cfc4cffda1-bluemix")
                        .username("9cdbab86-7715-4131-a5da-84cfc4cffda1-bluemix")
                        .password("468aa757abbc6b795ae9293b3484bccd2f3fcf3f181946d4f23685e5cf1c5475")
                        .build();
                Database db = client.database("firstdb", false);
                System.out.println("in db");
                Account doc = db.find(Account.class, useron);
                System.out.println("in db2");
                Account doc2;
                int size=doc.circles.size();
                System.out.println("Size is"+size);
                for(int i=0;i<size-1;i++)
                {
                    System.out.println(doc.circles.get(i+1));
                    circles.add(doc.circles.get(i+1));
                    System.out.println(circles.get(i));
                }
                names=new String[size-1];
                images=new String[size-1];
                for(int i=0;i<circles.size();i++)
                {
                    doc2 = db.find(Account.class,circles.get(i));
                    System.out.println(i+" "+doc2.fullname);
                    names[i]=doc2.fullname;
                    System.out.println(i + "ok");
                    images[i]=doc2.image;
                    System.out.println(i + "ok1");
                }

                    return "SUCCESS";


                    //db.save(new Schedule(user5,pass5,ride5,"","","","","",name1,num1,"","","","","","","","",0.0,0.0));



            }catch(Exception e){
                    Toast.makeText(getApplicationContext(), "Network Busy!!", Toast.LENGTH_LONG).show();
                    //System.out.println("Error SMS "+e);
                    return "FAILED";
                }



        }
        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result.equals("SUCCESS")){
                System.out.println("SUCCESS");
                CustomList adapter = new
                        CustomList(myfriends.this,names,images);

                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Toast.makeText(myfriends.this, "You Clicked at " + web[+position], Toast.LENGTH_SHORT).show();

                    }
                });
            }
            else{
                Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
            }

            progressDialog.dismiss();
            // Do things like hide the progress bar or change a TextView
        }
    }

}
