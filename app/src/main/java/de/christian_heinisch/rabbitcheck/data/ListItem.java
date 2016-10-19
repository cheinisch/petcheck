package de.christian_heinisch.rabbitcheck.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by chris on 09.10.2016.
 */

public class ListItem {

    public String name;
    public String beschreibung;
    public String bild;
    public String langtext;

    public ListItem(String name, String beschreibung, String bild, String langtext){
        this.name = name;
        this.beschreibung = beschreibung;
        this.bild = bild;
        this.langtext = langtext;
    }

}
