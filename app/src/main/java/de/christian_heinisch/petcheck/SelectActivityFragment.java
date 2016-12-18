package de.christian_heinisch.petcheck;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import de.christian_heinisch.petcheck.data.MenuListAdapter;
import de.christian_heinisch.petcheck.data.ListItemMenu;

/**
 * A placeholder fragment containing a simple view.
 */
public class SelectActivityFragment extends Fragment {

    View rootview;

    public SelectActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_select, container, false);
        try {
            populateMenulist();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Inflate the layout for this fragment
        return rootview;
    }

    public void populateMenulist() throws JSONException {

        MainActivity activity = new MainActivity();

        ArrayList<ListItemMenu> arrayOfUsers = null;
        arrayOfUsers = getContent();
        MenuListAdapter adapter = new MenuListAdapter(getActivity(), arrayOfUsers);
        ListView listView = (ListView) rootview.findViewById(R.id.listMenu);
        listView.setAdapter(adapter);

        //TODO: handle title, description, url, fulltext of listitem to openIten(...)
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListItemMenu listitem = (ListItemMenu) parent.getAdapter().getItem(position);
                openItem(view, listitem);
            }
        });


    }

    public ArrayList<ListItemMenu> getContent() throws JSONException {
        ArrayList<ListItemMenu> listitems;
        listitems = new ArrayList<ListItemMenu>();

        // Läd JSON Daten aus der Datei in ein Objekt
        JSONArray jsonarray = new JSONArray(loadJSONFromAsset());

        System.out.println("JSON Länge: " + jsonarray.length());

        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject obj = jsonarray.getJSONObject(i);

            String titel = obj.getString("Text");
            String beschreibung = obj.getString("Json");
            System.out.println(titel);
            listitems.add(new ListItemMenu(titel, beschreibung));
        }

        return listitems;
    }

    public String loadJSONFromAsset() {
        String json = null;

        String json_file = "menu.json";
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

    public void openItem(View view, ListItemMenu listitem){

        String description = listitem.getBeschreibung();
        String titel = listitem.getName();


        SharedPreferences settings = this.getActivity().getSharedPreferences("Petload", 0);
        SharedPreferences.Editor editor = settings.edit();

        // Werte der Checkboxen speichern

        editor.putString("json", description);
        editor.putString("titel", titel);

        // Anzahl der Tiere speichern
        editor.commit();

        Intent refresh = new Intent(getContext(), MainActivity.class);
        startActivity(refresh);


    }

}
