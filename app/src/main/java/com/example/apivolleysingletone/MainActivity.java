package com.example.apivolleysingletone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView textView;
    RequestQueue requestQueue;
    String uri = "https://mocki.io/v1/52d67292-a36d-466e-8f23-3e18b9c5a147";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create a request queue
//                requestQueue = Volley.newRequestQueue(MainActivity.this);

                requestQueue = VolleySingleton.getInstance(MainActivity.this).getRequestQueue();

                //request a object
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, uri, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            //we are accessing the array
                            JSONArray jsonArray = response.getJSONArray("student");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                //now access the 3 objects in the array
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String name = jsonObject.getString("name");
                                String email = jsonObject.getString("email");
                                int age = jsonObject.getInt("age");

                                textView.append(name + " " + email + " " + age + "\n");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                //add this request to the queue
                requestQueue.add(jsonObjectRequest);
            }
        });
    }
}