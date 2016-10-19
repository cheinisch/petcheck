package de.christian_heinisch.rabbitcheck;


import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

import de.christian_heinisch.rabbitcheck.data.CustomUserAdapter;
import de.christian_heinisch.rabbitcheck.data.ListItem;



/**
 * A simple {@link Fragment} subclass.
 */
public class OverviewFragment extends Fragment {

    private InputStream input;
    private String json;
    public View rootview;
    public static InputStream is;

    public OverviewFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_overview, container, false);

        try {
            populateUserslist();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Inflate the layout for this fragment
        return rootview;
    }

    public void populateUserslist() throws JSONException {

        MainActivity activity = new MainActivity();

        ArrayList<ListItem> arrayOfUsers = null;
        arrayOfUsers = getUsers();
        CustomUserAdapter adapter = new CustomUserAdapter(getActivity(), arrayOfUsers);
        ListView listView = (ListView) rootview.findViewById(R.id.listOverview);
        listView.setAdapter(adapter);


    }
    public ArrayList<ListItem> getUsers() throws JSONException {
        ArrayList<ListItem> listitems = new ArrayList<ListItem>();

        JSONArray jsonarray = new JSONArray(loadJSONFromAsset());

        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject obj = jsonarray.getJSONObject(i);

            String titel = obj.getString("Titel");
            String beschreibung = obj.getString("Beschreibung");
            String URL = obj.getString("URL");
            String langtext = obj.getString("Langtext");
            listitems.add(new ListItem(titel, beschreibung, URL, langtext));
        }
        return listitems;
    }

    public String loadJSONFromAsset() {
        String json = null;

        String sprache = Locale.getDefault().getLanguage();
        System.out.println(sprache);
        String json_file = sprache + "-data.json";
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


}
