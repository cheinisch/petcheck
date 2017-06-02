package de.christian_heinisch.petcheck;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class FishCheckFragment extends Fragment {

    View rootview;
    private Button button;
    CheckBox checkBoxFishAquarium;
    CheckBox checkBoxFishPumpe;
    CheckBox checkBoxFishHeizung;
    CheckBox checkBoxFishLicht;
    CheckBox checkBoxFishSand;
    CheckBox checkBoxFishPflanzen;


    public FishCheckFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_fish_check, container, false);

        checkBoxFishAquarium = (CheckBox) rootview.findViewById(R.id.checkBoxFishAquarium);
        checkBoxFishPumpe = (CheckBox) rootview.findViewById(R.id.checkBoxFishPumpe);
        checkBoxFishHeizung = (CheckBox) rootview.findViewById(R.id.checkBoxFishHeizung);
        checkBoxFishLicht = (CheckBox) rootview.findViewById(R.id.checkBoxFishLicht);
        checkBoxFishSand = (CheckBox) rootview.findViewById(R.id.checkBoxFishSand);
        checkBoxFishPflanzen = (CheckBox) rootview.findViewById(R.id.checkBoxFishPflanzen);
        //zahl = (EditText) rootview.findViewById(R.id.text_anzahl_tiere);


        //Checkliste und Textfelder laden
        getChecklist();



        button= (Button) rootview.findViewById(R.id.CheckFishSave);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //rechnen();

                setChecklist();
            }
        });

        // Inflate the layout for this fragment
        return rootview;
    }

    public void setChecklist(){

        System.out.println("Daten gespeichert");

        SharedPreferences settings = getActivity().getSharedPreferences("PetInfo", 0);
        SharedPreferences.Editor editor = settings.edit();

        // Werte der Checkboxen speichern
        editor.putBoolean("FishAquarium", checkBoxFishAquarium.isChecked());
        editor.putBoolean("FishPumpe", checkBoxFishPumpe.isChecked());
        editor.putBoolean("FishHeizung", checkBoxFishHeizung.isChecked());
        editor.putBoolean("FishLicht", checkBoxFishLicht.isChecked());
        editor.putBoolean("FishSand", checkBoxFishSand.isChecked());
        editor.putBoolean("FishPflanzen", checkBoxFishPflanzen.isChecked());


        //editor.putString("Anzahl", zahl.getText().toString());

        // Anzahl der Tiere speichern
        editor.commit();
    }

    public void getChecklist(){

        System.out.println("Daten geladen");

        SharedPreferences settings = getActivity().getSharedPreferences("PetInfo", 0);

        // Werte der Checkboxen setzten. Wenn nix geladen werden kann ist default false
        checkBoxFishAquarium.setChecked(settings.getBoolean("FishAquarium", false));
        checkBoxFishPumpe.setChecked(settings.getBoolean("FishPumpe", false));
        checkBoxFishHeizung.setChecked(settings.getBoolean("FishHeizung", false));
        checkBoxFishLicht.setChecked(settings.getBoolean("FishLicht", false));
        checkBoxFishSand.setChecked(settings.getBoolean("FishSand", false));
        checkBoxFishPflanzen.setChecked(settings.getBoolean("FishPflanzen", false));


/*
        // Anzahl der Tiere setzten (Default ist 2)
        zahl.setText(settings.getString("Anzahl", "2").toString());

        // Werte direkt nach Start berechnen
        rechnen();*/
    }

}
