package de.christian_heinisch.petcheck;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SettingFragment extends Fragment {

    public SettingFragment() {

    }

    private Button button;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_setting, container, false);

        button = (Button) rootview.findViewById(R.id.buttonSettingReset);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                delete_data();
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

}
