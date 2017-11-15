package com.example.meetkumarpatel.toylist;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText mSearchEditText;
    TextView mUrlDisplayText;
    TextView mSearchTextView;
    TextView errorMessageTextView;
    ProgressBar showProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSearchEditText = findViewById(R.id.et_search_box);
        mUrlDisplayText = (TextView) findViewById(R.id.tv_url_display);
        mSearchTextView = (TextView) findViewById(R.id.tv_github_search_result_json);
        errorMessageTextView = (TextView) findViewById(R.id.tv_error_message_display);
        showProgress = (ProgressBar) findViewById(R.id.pb_loading_indicator);
    }

    private void showErrorMessage(){
        errorMessageTextView.setVisibility(View.VISIBLE);
        mSearchTextView.setVisibility(View.INVISIBLE);
    }

    private void showJSONData(){
        errorMessageTextView.setVisibility(View.INVISIBLE);
        mSearchTextView.setVisibility(View.VISIBLE);
    }

    private void makeGithubSearchQuery(){
        String githubQuery = mSearchEditText.getText().toString();
        URL githubSearchUrl = NetworkUtils.buildUrl(githubQuery);
        mUrlDisplayText.setText(githubSearchUrl.toString());
        String gitHubSearchResults = null;
        new GithubQueryTask().execute(githubSearchUrl);
    }

    public class GithubQueryTask extends AsyncTask<URL, Void, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String githubsearchResults = null;
            try{
                githubsearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            }
            catch(IOException e){
                e.printStackTrace();
            }
            return githubsearchResults;
        }

        @Override
        protected void onPostExecute(String s) {
            showProgress.setVisibility(View.INVISIBLE);
            if(s!=null && !s.equals("")){
                showJSONData();
                mSearchTextView.setText(s);
            }
            else{
                showErrorMessage();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemThatWasSelected = item.getItemId();
        if(menuItemThatWasSelected == R.id.action_search){
            makeGithubSearchQuery();
        }
        return super.onOptionsItemSelected(item);
    }
}
