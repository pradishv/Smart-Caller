package com.example.hp.firstapp;


        import android.app.ProgressDialog;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.util.Base64;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.cloudant.client.api.ClientBuilder;
        import com.cloudant.client.api.CloudantClient;
        import com.cloudant.client.api.Database;

        import java.util.ArrayList;

/**
 * Created by hp1 on 21-01-2015.
 */
public class Tab1 extends Fragment {
public String useron;
    int arz;
    Button safe;
    String []favo;
    String addmem;
    ArrayList<String>favlist;
    EditText fav;
    Button favor;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_1,container,false);
        useron = getActivity().getIntent().getExtras().getString("user");
        safe=(Button)v.findViewById(R.id.button7);
        fav=(EditText)v.findViewById(R.id.editText4);
        favor=(Button)v.findViewById(R.id.button8);
        new MyTask().execute();
        favor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addmem=fav.getText().toString();
                new MyTask3().execute();

            }
        });
        safe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(safe.getText().toString());
                    if(safe.getText().toString().equals("SAFE MODE OFF"))
                    {
                        System.out.println("Now on");
                        safe.setText("SAFE MODE ON");
                        new Blknum(favo,arz,true);
                    }
                else if(safe.getText().toString().equals("SAFE MODE ON"))
                {
                    System.out.println("Now off");
                    safe.setText("SAFE MODE OFF");
                    new Blknum(favo,arz,false);
                }

            }
        });
        return v;
    }
    private class MyTask3 extends AsyncTask<String, Integer, String> {

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
                doc.favlist.add(addmem);
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
            }
            else{
                Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_LONG).show();
            }

            // Do things like hide the progress bar or change a TextView
        }
    }
    private class MyTask extends AsyncTask<String, Integer, String> {

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // progressDialog = ProgressDialog.show(getActivity(), "Message", "Loading...");
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
                favlist=new ArrayList<String>(doc.favlist);
                arz=favlist.size();
                favo=new String[arz];
                for(int i=0;i<arz;i++)
                {
                    favo[i]=favlist.get(i);
                }
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
                new Blknum(favo,arz,false);
            }
            else{
                Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_LONG).show();
            }

           // progressDialog.dismiss();
            // Do things like hide the progress bar or change a TextView
        }
    }
}
