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

import java.io.IOException;
import java.io.InputStream;

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
        // Läd bestimmte Daten aus dem Objekt in ein Array
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
        try {

            InputStream is = getContext().getAssets().open(json_file);
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
