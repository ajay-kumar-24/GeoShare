package team.gis.geoshare;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Indhu on 10/15/16.
 */

public class send_request extends AsyncTask<String, Void, String> {
    public int userId;

    @Override
    protected String doInBackground(String... params) {
        InputStream is = null;
        HttpURLConnection conn = null;
        URL url = null;
        String message = null;

        try {
            url = new URL("http://207.151.56.86:8080/"+params[0]);
            System.out.println(url.toString());
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();
            is = conn.getInputStream();

            message = stream_to_string(is);
        }
        catch(IOException e){
            System.out.println(e);
        }
        return message;
    }
    @Override
    protected void onPostExecute(String message) {

    }

    public int get_user_id(){
        return userId;
    }

    public String stream_to_string(InputStream is){
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
