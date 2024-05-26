package com.example.geowarning.methods;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.geowarning.MainActivity;
import com.example.geowarning.MyApp;
import com.example.geowarning.model.Warning;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class ApiHelper {

    private Context context;
    private RequestQueue requestQueue;
    private ObjectMapper objectMapper;

    public ApiHelper(Context context) {
        this.context = context;
        this.requestQueue = MyApp.getRequestQueue();
        this.objectMapper = new ObjectMapper();
    }

    public interface ApiCallback<T> {
        void onSuccess(T result);
        void onError(Exception e);
    }

    public void fetchWarnings(final ApiCallback<List<Warning>> callback) {
        Log.d("MyApp", "API request added to queue.");
        String url = "https://opendata-download-warnings.smhi.se/ibww/api/version/1/warning.json"; //API endpoint
        Log.d("MyApp", "About to make API call...");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("MyApp", "request successfully sent.");
                        Toast.makeText(context.getApplicationContext(), "request successfully sent API, response recieved. ", Toast.LENGTH_SHORT).show();
                        objectMapper = new ObjectMapper();
                        try {
                            List<Warning> warnings = objectMapper.readValue(response, new TypeReference<List<Warning>>() {});
                            Log.d("MyApp", "getting warning objects from API.");
                            for (Warning warning : warnings) {
                                Log.d("MyApp", "processing warning object:" + warning.getId());
                                String eventName = warning.getEvent().getSv();
                            }
                            callback.onSuccess(warnings);
                        }
                        catch (JsonProcessingException e){
                            e.printStackTrace();
                            Toast.makeText(context.getApplicationContext(), "Error parsing JSON", Toast.LENGTH_SHORT).show();
                            callback.onError(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context.getApplicationContext(), "unsuccessful api request, error:" + error, Toast.LENGTH_SHORT).show();
                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            Toast.makeText(context.getApplicationContext(), "API request failed with status code " + statusCode, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context.getApplicationContext(), "API request error: " + error, Toast.LENGTH_SHORT).show();
                        }
                        callback.onError(error);
                    }
                });
        requestQueue.add(stringRequest);
    }
}
