package com.example.training;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RandomQuoteApiFetcher {

    public static class QuoteFetcherTask extends AsyncTask<Void, Void, String> {
        private WeakReference<TextView> quoteTextViewReference;
        private static final String TAG = "RandomQuoteApiFetcher";

        //get TextView from MainActivity via WeakReference
        public QuoteFetcherTask(TextView quoteTextView) {
            this.quoteTextViewReference = new WeakReference<>(quoteTextView);
        }

        @Override
        protected String doInBackground(Void... params) {
            String apiUrl = "https://zenquotes.io/api/random";
            HttpURLConnection urlConnection = null;
            StringBuilder result = new StringBuilder();
            try{
                URL url = new URL(apiUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null){
                    urlConnection.disconnect();
                }
            }
            // Your existing network code here
            // Return the result string
            Log.d(TAG, "fetched api result string is: "+result);
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            TextView quoteTextView = quoteTextViewReference.get();
            if (quoteTextView != null) {
                try{
                    JSONArray jsonArray = new JSONArray(result);
                    JSONObject jsonObject = jsonArray.getJSONObject(0); //get first object in JSON (should only be one anyway)
                    String quote = jsonObject.getString("q"); //get line where quote is at (q)
                    String author = jsonObject.getString("a"); //get line where author is at (a)

                    quoteTextView.setText("''"+quote+"''" + " - " + author);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void fetchQuoteFromApi(TextView quoteTextView) {
        new QuoteFetcherTask(quoteTextView).execute();
    }
}
