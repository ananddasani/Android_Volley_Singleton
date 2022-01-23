# Android_Volley_Singleton
Enhancing the Volley API by singleton instance

# Dependency
```
implementation 'com.android.volley:volley:1.2.1'
```

# Code

### VolleySingleton.java
```
public class VolleySingleton {

    private RequestQueue requestQueue;
    private static VolleySingleton mInstance;

    private VolleySingleton(Context context){
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized VolleySingleton getInstance(Context context){
        if(mInstance == null)
            mInstance = new VolleySingleton(context);

        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        return requestQueue;
    }
}
```

#### MainActivity.java
```
TextView textView;
Button button;
LinearLayout linearLayout;

RequestQueue requestQueue;

        //whenever you want to fetch data from the API you create a request queue
        // requestQueue = Volley.newRequestQueue(getApplicationContext());
        
        //------------ Singleton ------------------
         requestQueue = VolleySingleton.getInstance(MainActivity.this).getRequestQueue();
        
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //url of API
                String url = "https://mocki.io/v1/db6249d4-0ee1-4bae-a948-82d92c7d1ebc";

                //if you see it is first object then array so...
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            //we want array (from the object) so...
                            JSONArray jsonArray = response.getJSONArray("student");

                            //now we have 3 items in array students[] (just we got)
                            for (int i = 0; i < jsonArray.length(); i++) {

                                //we are having objects (name, email, age) so...
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String name = jsonObject.getString("name");
                                String email = jsonObject.getString("email");
                                int age = jsonObject.getInt("age");

                                //Just Appending the data to already existing TextView
                                textView.append(name + " " + email + " " + String.valueOf(age) + "\n");

                                //Creating TextView and Adding in the Layout
                                TextView textView2 = new TextView(MainActivity.this);
                                textView2.setText(name + " " + email + " " + String.valueOf(age) + "\n");
                                linearLayout.addView(textView2);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                //add this request to request queue
                requestQueue.add(jsonObjectRequest);
            }
        });
    }
}
```

# App Highlight

<img src="app_images/Volley API Basic Code.png" /><br>

<img src="app_images/Volley API Basic App.png" width="300" />
