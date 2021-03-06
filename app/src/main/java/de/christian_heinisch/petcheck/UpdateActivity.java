package de.christian_heinisch.petcheck;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Timestamp;

public class UpdateActivity extends AppCompatActivity {

    private ProgressBar mProgress;
    private int mProgressStatus = 0;
    private Handler mHandler = new Handler();
    private TextView percent;
    StringBuffer text = new StringBuffer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        mProgress = (ProgressBar) findViewById(R.id.progressBarUpdate_new);
        mProgress.setProgress(mProgressStatus);
        percent = (TextView) findViewById(R.id.textViewUpdateProgress);

        new update().execute();

    }


    private class update extends AsyncTask {

        @Override
        protected Object doInBackground(Object... arg0) {
            // 0%
            mHandler.post(new Runnable() {
                public void run() {
                    mProgress.setProgress(mProgressStatus);
                    percent.setText(mProgressStatus + " %");
                }
            });

            // Update Kaninchen

            getData("https://haustiercheck.christian-heinisch.de/daten/rabbit/json/rabbit-data.json", "rabbit.json");

            // 33%
            mHandler.post(new Runnable() {
                public void run() {
                    mProgressStatus = 33;
                    mProgress.setProgress(mProgressStatus);
                    percent.setText(mProgressStatus + " %");
                }
            });

            // Update Fische

            getData("https://haustiercheck.christian-heinisch.de/daten/fish/json/fish-data.json", "fish.json");

            mHandler.post(new Runnable() {
                public void run() {
                    mProgressStatus = 66;
                    mProgress.setProgress(mProgressStatus);
                    percent.setText(mProgressStatus + " %");
                }
            });

            // Update Hund

            getData("https://haustiercheck.christian-heinisch.de/daten/dog/json/dog-data.json", "dog.json");

            // 100%
            mHandler.post(new Runnable() {
                public void run() {
                    mProgressStatus = 100;
                    mProgress.setProgress(mProgressStatus);
                    percent.setText(mProgressStatus + " %");
                }
            });


            // 100%

            //Setzt einen aktuellen Timestamp als letztes Updatedatum
            setUpdateDate();

            // Neustart der App
            Intent i = getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage(getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

            return null;
        }

    }

    private void setUpdateDate() {

        long timestamp = System.currentTimeMillis();

        SharedPreferences settings = this.getSharedPreferences("UpdateInfo", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong("LastUpdate", timestamp);
        editor.commit();

    }


    public void getData(String datasource, String Filename){

        try {
            // Create a URL for the desired page
            URL url = new URL(datasource);
            StringBuffer sbuf;
            String str = "";
            System.out.println("start download");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                str = readStream(in).toString();

            } finally {
                urlConnection.disconnect();
            }
            System.out.println(str);

            write(str, Filename);
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
    }

    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }

            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }

    public void write(String text, String FILE_NAME){
        try {
            FileOutputStream fos = this.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fos.write(text.toString().getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
