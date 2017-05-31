package de.christian_heinisch.petcheck;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AllgemeinesFragment extends Fragment {

    View rootview;

    public AllgemeinesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment

        rootview = inflater.inflate(R.layout.fragment_allgemeines, container, false);

        try {
            loadData();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rootview;
    }


    public void loadData() throws JSONException {
        JSONObject jsondata = new JSONObject(loadJSONFromAsset());
        //JSONObject jsondata = new JSONObject(readFile());

        // Läd bestimmte Daten aus dem Objekt in ein Array

        System.out.println("DATEN: " + jsondata.getJSONArray("beschreibung"));

        JSONArray jsonarraylist = jsondata.getJSONArray("beschreibung");

        JSONObject obj = jsonarraylist.getJSONObject(0);

        String titel = obj.getString("Titel");
        String text = obj.getString("Text");
        String warntext = obj.getString("Warntext");
        String bild = obj.getString("Bild");

        TextView textTitel = (TextView) rootview.findViewById(R.id.text_allgemeines_title);
        TextView textText = (TextView) rootview.findViewById(R.id.text_allgemeines_text);
        TextView textWarntext = (TextView) rootview.findViewById(R.id.text_allgemeines_text_info);

        textTitel.setText(titel);
        textText.setText(text);
        textWarntext.setText(warntext);

        titelbild(bild);
    }

    public String loadJSONFromAsset() {
        String json = null;
        String jsonname = ((MainActivity)getActivity()).getpetdata();

        String json_file = jsonname + "-data.json";
        String FILE_NAME = jsonname + ".json";
        try {

            InputStream is;

            File file = new File("rabbit.json");
            if(file.exists()){
                System.out.println("file is already there");
                is = getActivity().openFileInput(FILE_NAME);
            }else{
                System.out.println("Not find file ");
                is = getContext().getAssets().open(json_file);
            }

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    public String readFile(){

        String newStr = "";
        System.out.println("DATA IMPORT");
        String jsonname = ((MainActivity)getActivity()).getpetdata();

        String FILE_NAME = jsonname + ".json";
        try {

            BufferedReader bReader = new BufferedReader(new InputStreamReader(getActivity().openFileInput(FILE_NAME)));
            String line;

            StringBuffer text = new StringBuffer();
            System.out.println("----");
            while ((line = bReader.readLine()) != null) {
                System.out.println(bReader.readLine());
                text.append(line);
            }
            System.out.println("----");
            newStr = text.toString();

        } catch (IOException e) {
            loadJSONFromAsset();
            e.printStackTrace();
        }

        //System.out.println("JSONDATA" + newStr.substring(newStr.indexOf("["), newStr.lastIndexOf("]") + 1));

        //return (newStr.substring(newStr.indexOf("{"), newStr.lastIndexOf("}") + 1));
        return newStr;
    }

    public void titelbild(String url_load) {

        // Titelbild in imageView speichern
        ImageView imageView = (ImageView) rootview.findViewById(R.id.imageViewAllgemeines);

        String mDrawableName = url_load;
        int resID = getResources().getIdentifier(mDrawableName , "drawable", getContext().getPackageName());


        // Titelbild der Detailseite setzten
        Picasso.with(getContext())
                .load(resID)
                .placeholder(R.drawable.ic_placeholder) // optional
                .error(R.drawable.ic_error_fallback)         // optional
                //.fit()
                .into(imageView);


    }

}
