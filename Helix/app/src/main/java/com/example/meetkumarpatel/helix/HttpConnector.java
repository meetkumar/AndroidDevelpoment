package com.example.meetkumarpatel.helix;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by meetkumarpatel on 11/13/17.
 */

public class HttpConnector {
    private static final String TAG = HttpConnector.class.getSimpleName();

    public HttpConnector(){

    }
    public String readUrl(String myUrl) throws IOException{
        String data = null;
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(myUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            inputStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer();

            String line ="";
            while((line = br.readLine()) != null){
                sb.append(line).append('\n');
            }
            data = sb.toString();
            br.close();
        } catch (MalformedURLException e) {
            Log.e(TAG,"MalformedURLException: "+ e.getMessage());
        } catch (IOException e) {
            Log.e(TAG,"IOException: "+ e.getMessage());
        }catch (Exception e){
            Log.e(TAG,"Exception: "+ e.getMessage());
        }
        finally {
            inputStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
}
