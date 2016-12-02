package com.vuforia.gis.geoshare.app.TextRecognition;

import android.os.AsyncTask;

public class SendPostData extends AsyncTask<Void, Void, Void> {

    public SendPostData(){

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

//        show a progress dialog before doing the functionality
    }

    @Override
    protected Void doInBackground(Void... voids) {
//        write your functionality here
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

//        dismiss progress dialog and handle the result after getting the server response
    }
}
