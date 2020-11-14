package com.example.it17047302_mtit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

public class JokesView extends AppCompatActivity {

    private Button logout;

    RequestQueue queue;
    String url = "https://official-joke-api.appspot.com/random_joke";

    private TextView jokesView, txtID, txtType, txtSetup, txtPunchLine;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jokes_view);

        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(JokesView.this, MainActivity.class));

            }
        });

                 queue = Volley.newRequestQueue(this);


                 txtID = (TextView) findViewById(R.id.txtID);
                 txtType = (TextView) findViewById(R.id.txtType);
                 txtSetup = (TextView) findViewById(R.id.txtSetup);
                 txtPunchLine = (TextView) findViewById(R.id.txtPunchLine);
                 progressBar = (ProgressBar) findViewById(R.id.progressBar2);



    }

    public void getJokes(View view) {
   progressBar.setVisibility(View.VISIBLE);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                            int ID=0;
                            String type,setup,punchline;

                try {
                     ID=response.getInt("id");
                     type = response.getString("type");
                    setup = response.getString("setup");
                    punchline = response.getString("punchline");
                    txtID.setText(ID+"");
                    txtID.setVisibility(View.VISIBLE);

                    txtType.setText(type+"");
                    txtType.setVisibility(View.VISIBLE);

                    txtSetup.setText(setup+"");
                    txtSetup.setVisibility(View.VISIBLE);

                    txtPunchLine.setText(punchline+"");
                    txtPunchLine.setVisibility(View.VISIBLE);

                    progressBar.setVisibility(View.INVISIBLE);



                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                           String err = error.toString();
                jokesView.setText("cannot get data: " + error.toString());


            }
        });
queue.add(jsonObjectRequest);




    }
}