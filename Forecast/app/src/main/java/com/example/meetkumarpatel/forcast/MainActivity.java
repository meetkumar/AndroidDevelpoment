package com.example.meetkumarpatel.forcast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView mWeatherTextView;
    TextView mErrorMessage;
    ProgressBar mProgressShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        mWeatherTextView = (TextView) findViewById(R.id.tv_weather_data);
        mErrorMessage = (TextView) findViewById(R.id.tv_error_message_display);
        mProgressShow = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        //loadWeatherData();
    }

    public class FetchWeatherTask extends AsyncTask<String, Void, String[]> {

        // COMPLETED (6) Override the doInBackground method to perform your network requests
        @Override
        protected String[] doInBackground(String... params) {

            /* If there's no zip code, there's nothing to look up. */
            if (params.length == 0) {
                return null;
            }

            String location = params[0];
            URL weatherRequestUrl = NetworkUtils.buildUrl(location);

            try {
                String jsonWeatherResponse = NetworkUtils
                        .getResponseFromHttpUrl(weatherRequestUrl);

                //String[] simpleJsonWeatherData = OpenWeatherJsonUtils
                       // .getSimpleWeatherStringsFromJson(MainActivity.this, jsonWeatherResponse);

                //return simpleJsonWeatherData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        private void showErrorMessage(){
            mErrorMessage.setVisibility(View.VISIBLE);
            mWeatherTextView.setVisibility(View.INVISIBLE);
        }

        private void showJSONData(){
            mErrorMessage.setVisibility(View.INVISIBLE);
            mWeatherTextView.setVisibility(View.VISIBLE);
        }

        // COMPLETED (7) Override the onPostExecute method to display the results of the network request
        @Override
        protected void onPostExecute(String[] weatherData) {
            mProgressShow.setVisibility(View.INVISIBLE);
            if (weatherData != null) {
                for (String weatherString : weatherData) {
                    showJSONData();
                    mWeatherTextView.append((weatherString) + "\n\n\n");
                }
            }
            else {
                showErrorMessage();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressShow.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.forecast,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_refresh){
            mWeatherTextView.setText("");
            //loadWeatherData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
