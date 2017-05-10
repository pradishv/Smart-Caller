package com.example.hp.firstapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp1 on 21-01-2015.
 */
public class Tab2 extends Fragment {
    Button circles,search;
    String useron,proname,propic;
    String name;
    String []check;
    String []names;
    String []images;
    TextView tv;
    ListView list;
    String []web;
    ImageView iv;
    EditText et;
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_2,container,false);
        useron = getActivity().getIntent().getExtras().getString("user");
        System.out.println(useron);
        tv=(TextView)v.findViewById(R.id.textView3);
        iv=(ImageView)v.findViewById(R.id.imageView2);
        et=(EditText)v.findViewById(R.id.editText3);
        list=(ListView)v.findViewById(R.id.listView);
        search=(Button)v.findViewById(R.id.button6);
        circles=(Button)v.findViewById(R.id.button5);

        circles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),myfriends.class);
                i.putExtra("user",useron);
                startActivity(i);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=et.getText().toString();
                new MyTask2().execute();
            }
        });
        new MyTask().execute();
        return v;
    }
    private class MyTask extends AsyncTask<String, Integer, String> {

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getActivity(), "Message", "Loading...");
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
                proname=doc.fullname;
                propic=doc.image;
                return "SUCCESS";


                //db.save(new Schedule(user5,pass5,ride5,"","","","","",name1,num1,"","","","","","","","",0.0,0.0));



            }catch(Exception e){
                Toast.makeText(getActivity(), "Network Busy!!", Toast.LENGTH_LONG).show();
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
                tv.setText(proname);
                byte[] decodedString = Base64.decode(propic, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                iv.setImageBitmap(decodedByte);
            }
            else{
                Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_LONG).show();
            }

            progressDialog.dismiss();
            // Do things like hide the progress bar or change a TextView
        }
    }
    private class MyTask2 extends AsyncTask<String, Integer, String> {

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getActivity(), "Message", "Loading...");
        }

        // This is run in a background thread
        @Override
        protected String doInBackground(String... params) {
            System.out.println("Inside task");
            int p=0;
            // get the string from params, which is an array
            try {
                CloudantClient client = ClientBuilder.account("9cdbab86-7715-4131-a5da-84cfc4cffda1-bluemix")
                        .username("9cdbab86-7715-4131-a5da-84cfc4cffda1-bluemix")
                        .password("468aa757abbc6b795ae9293b3484bccd2f3fcf3f181946d4f23685e5cf1c5475")
                        .build();
                Database db = client.database("firstdb", false);
                Account doc;
                System.out.println("Inside task1");
             /*   List<Account> samp= db.findByIndex("{\"selector\":{\"types\":\"" + "user" + "\"}}", Account.class);
                System.out.println("Inside task2");
                int hh=samp.size();
                System.out.println("Inside task3");
                check=new String[hh];
                for(int i=0;i<hh;i++)
                {
                    if(samp.get(i).fullname.contains(name))
                    {
                        System.out.println("Insides task"+i);
                        check[p++]=samp.get(i).username;
                    }
                }
                names=new String[p-1];
                images=new String[p-1];
                for(int y=0;y<=p;y++)
                {
                    doc=db.find(Account.class,check[y]);
                    System.out.println("Insides"+y);
                    names[y]=doc.fullname;
                    images[y]=doc.image;
                }*/
                doc=db.find(Account.class,name);
                names=new String[1];
                images=new String[1];
                names[0]=doc.fullname;
                images[0]=doc.image;
                System.out.println("c2");

                return "SUCCESS";


                //db.save(new Schedule(user5,pass5,ride5,"","","","","",name1,num1,"","","","","","","","",0.0,0.0));



            }catch(Exception e){
                Toast.makeText(getActivity(), "Network Busy!!", Toast.LENGTH_LONG).show();
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
                        CustomList(getActivity(),names,images);

                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Add to circles")
                                .setMessage("Are you sure you want to add this person to circles?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                        new MyTask3().execute();
                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                        Toast.makeText(getActivity(), "You Clicked at " + names[+position], Toast.LENGTH_SHORT).show();

                    }
                });
            }
            else{
                Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_LONG).show();
            }

            progressDialog.dismiss();
            // Do things like hide the progress bar or change a TextView
        }
    }
    private class MyTask3 extends AsyncTask<String, Integer, String> {

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getActivity(), "Message", "Loading...");
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
                doc.circles.add(name);
                Account doc2 = db.find(Account.class, name);
                doc.favlist.add(doc2.phone);
                db.update(doc);

                return "SUCCESS";


                //db.save(new Schedule(user5,pass5,ride5,"","","","","",name1,num1,"","","","","","","","",0.0,0.0));



            }catch(Exception e){
                Toast.makeText(getActivity(), "Network Busy!!", Toast.LENGTH_LONG).show();
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
                tv.setText(proname);
                byte[] decodedString = Base64.decode(propic, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                iv.setImageBitmap(decodedByte);
            }
            else{
                Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_LONG).show();
            }

            progressDialog.dismiss();
            // Do things like hide the progress bar or change a TextView
        }
    }
}
