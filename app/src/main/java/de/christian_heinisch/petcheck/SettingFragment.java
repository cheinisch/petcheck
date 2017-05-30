package de.christian_heinisch.petcheck;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class SettingFragment extends Fragment {

    public SettingFragment() {

    }

    private Button button;
    private Button updateButton;
    private ProgressBar mProgress;
    private int mProgressStatus = 0;
    private Handler mHandler = new Handler();
    StringBuffer text = new StringBuffer();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_setting, container, false);

        mProgress = (ProgressBar) rootview.findViewById(R.id.progressBarUpdate);
        mProgress.setProgress(mProgressStatus);

        button = (Button) rootview.findViewById(R.id.buttonSettingReset);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                delete_data();
            }
        });

        updateButton = (Button) rootview.findViewById(R.id.buttonUpdateData);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                new update().execute();
            }
        });


        return rootview;
    }

    private void delete_data() {

        SharedPreferences settings = this.getActivity().getSharedPreferences("PetInfo", 0);
        SharedPreferences.Editor editor = settings.edit();

        // Daten löschen
        editor.clear();
        // Bearbeiten schließen
        editor.commit();
    }



    private class update extends AsyncTask {

        @Override
        protected Object doInBackground(Object... arg0) {
            // 0%
            mHandler.post(new Runnable() {
                public void run() {
                    mProgress.setProgress(mProgressStatus);
                }
            });

            // Update Kaninchen

            getData("https://haustiercheck.christian-heinisch.de/daten/rabbit/json/rabbit-data.json", "rabbit.json");

            // 33%
            mHandler.post(new Runnable() {
                public void run() {
                    mProgress.setProgress(33);
                }
            });

            // Update Fische

            getData("https://haustiercheck.christian-heinisch.de/daten/rabbit/json/rabbit-data.json", "fish.json");

            mHandler.post(new Runnable() {
                public void run() {
                    mProgress.setProgress(66);
                }
            });

            // Update Hund

            getData("https://haustiercheck.christian-heinisch.de/daten/rabbit/json/rabbit-data.json", "dog.json");

            // 100%
            mHandler.post(new Runnable() {
                public void run() {
                    mProgress.setProgress(100);
                }
            });


            // 100%
            return null;
        }

    }


    public void getData(String datasource, String Filename){

        try {
            // Create a URL for the desired page
            URL url = new URL(datasource);

            // Read all the text returned by the server
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String str;
            String test2 = in.readLine();

            while ((str = in.readLine()) != null) {
                // str is one line of text; readLine() strips the newline character(s)
                text.append(str + "\n");
            }
            in.close();

            //Convert Bufferd String to String
            String newStr = text.toString();

            write(newStr, Filename);
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
    }

    public void write(String text, String FILE_NAME){
        try {
            FileOutputStream fos = getActivity().openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fos.write(text.toString().getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
