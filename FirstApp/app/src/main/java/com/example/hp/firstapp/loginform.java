package com.example.hp.firstapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;

public class loginform extends AppCompatActivity {
    EditText username,password;
    Button submit;
    String usr,pass;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginform);

        username=(EditText)findViewById(R.id.editText);
        password=(EditText)findViewById(R.id.editText2);
        submit=(Button)findViewById(R.id.button);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usr = username.getText().toString();
                pass = password.getText().toString();

                new MyTask().execute();
                System.out.println(usr + " " + pass);
            }
        });


    }
    private class MyTask extends AsyncTask<String, Integer, String> {

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(loginform.this, "Message", "Logging In...");

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
                Account doc=db.find(Account.class,usr);
                if(doc.password.equals(pass))
                {
                    return "SUCCESS";
                }

                //db.save(new Schedule(user5,pass5,ride5,"","","","","",name1,num1,"","","","","","","","",0.0,0.0));



            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Network Busy!!", Toast.LENGTH_LONG).show();
                //System.out.println("Error SMS "+e);

            }
            return "FAILED";
        }



        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result.equals("SUCCESS")){
                System.out.println("SUCCESS");
                Intent i = new Intent(loginform.this,loginsuccess.class);
                i.putExtra("user", usr);
                startActivity(i);
            }
            else{
                Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
            }

            progressDialog.dismiss();
            // Do things like hide the progress bar or change a TextView
        }
    }
}
