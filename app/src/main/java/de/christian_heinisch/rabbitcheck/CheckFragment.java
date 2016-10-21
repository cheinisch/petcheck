package de.christian_heinisch.rabbitcheck;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class CheckFragment extends Fragment {

    public CheckFragment() {
        // Required empty public constructor
    }

    private int anzahlTiere;
    private int anzahlQMErgebnis;
    private Button button;
    CheckBox checkBoxHeu;
    CheckBox checkBoxStreu;
    CheckBox checkBoxToiletten;
    CheckBox checkBoxPellets;
    CheckBox checkBoxFutter;
    CheckBox checkBoxKruschel;
    EditText zahl;

    // Lege die Max. Anzahl an Tieren fest, mit der Gerechnet werden kann
    int maxTiere = 200;



    View rootview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_check, container, false);

        // Checkboxen und Textfelder anlegen
        checkBoxHeu = (CheckBox) rootview.findViewById(R.id.checkBoxHeu);
        checkBoxStreu = (CheckBox) rootview.findViewById(R.id.checkBoxStreu);
        checkBoxToiletten = (CheckBox) rootview.findViewById(R.id.checkBoxToiletten);
        checkBoxPellets = (CheckBox) rootview.findViewById(R.id.checkBoxPellets);
        checkBoxFutter = (CheckBox) rootview.findViewById(R.id.checkBoxFutter);
        checkBoxKruschel = (CheckBox) rootview.findViewById(R.id.checkBoxKruschel);
        zahl = (EditText) rootview.findViewById(R.id.text_anzahl_tiere);


        //Checkliste und Textfelder laden
        getChecklist();


        button= (Button) rootview.findViewById(R.id.button01);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rechnen();
                setChecklist();
            }
        });

        // Inflate the layout for this fragment
        return rootview;


    }

    public void rechnen(){


        String new_zahl = zahl.getText().toString();


        // String in einen Int konvertieren
        anzahlTiere = Integer.parseInt(new_zahl);

        // Warntext in Variable speichern
        TextView warnung = (TextView) rootview.findViewById(R.id.textWarnung);


        // Prüfe anzahl der Tiere und wenn anzahl größer als maxTiere ist, dann setzte den Wert auf maxTiere
        if(anzahlTiere > maxTiere){
            anzahlTiere = maxTiere;
            zahl.setText("" + maxTiere);
        }


        if(anzahlTiere < 2){

            warnung.setText("Du brauchst mindestens zwei Kaninchen");

            ViewGroup.LayoutParams params = warnung.getLayoutParams();
            // Changes the height and width to the specified *pixels*
            params.height = 100;
            params.width = 100;

            warnung.setLayoutParams(params);

            Context context = getActivity().getApplicationContext();
            CharSequence text = "Du brauchst mindestens zwei Kaninchen";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }else {

            warnung.setText("");
        }

            ViewGroup.LayoutParams params = warnung.getLayoutParams();
            // Changes the height and width to the specified *pixels*
            params.height = 0;
            params.width = 0;

            warnung.setLayoutParams(params);

            anzahlQMErgebnis = anzahlTiere * 2;

            String QM = anzahlQMErgebnis + " qm²";

            TextView platz = (TextView) rootview.findViewById(R.id.textPlatzangebot);
            platz.setText(QM);

            String klos = anzahlTiere + " Toiletten";
            TextView klo = (TextView) rootview.findViewById(R.id.textToiletten);
            klo.setText(klos);

            String haeuser = anzahlTiere + " Häuser";
            TextView haus = (TextView) rootview.findViewById(R.id.textHaus);
            haus.setText(haeuser);

    }

    public void setChecklist(){

        SharedPreferences settings = this.getActivity().getSharedPreferences("PetInfo", 0);
        SharedPreferences.Editor editor = settings.edit();

        // Werte der Checkboxen speichern
        editor.putBoolean("Heu", checkBoxHeu.isChecked());
        editor.putBoolean("Streu", checkBoxStreu.isChecked());
        editor.putBoolean("Toiletten", checkBoxToiletten.isChecked());
        editor.putBoolean("Pellets", checkBoxPellets.isChecked());
        editor.putBoolean("Futter", checkBoxFutter.isChecked());
        editor.putBoolean("Kruschel", checkBoxKruschel.isChecked());

        editor.putString("Anzahl", zahl.getText().toString());

        // Anzahl der Tiere speichern
        editor.commit();
    }

    public void getChecklist(){

        SharedPreferences settings = this.getActivity().getSharedPreferences("PetInfo", 0);

        // Werte der Checkboxen setzten. Wenn nix geladen werden kann ist default false
        checkBoxHeu.setChecked(settings.getBoolean("Heu", false));
        checkBoxStreu.setChecked(settings.getBoolean("Streu", false));
        checkBoxToiletten.setChecked(settings.getBoolean("Toiletten", false));
        checkBoxPellets.setChecked(settings.getBoolean("Pellets", false));
        checkBoxFutter.setChecked(settings.getBoolean("Futter", false));
        checkBoxKruschel.setChecked(settings.getBoolean("Kruschel", false));


        // Anzahl der Tiere setzten (Default ist 2)
        zahl.setText(settings.getString("Anzahl", "2").toString());

        // Werte direkt nach Start berechnen
        rechnen();
    }

}
