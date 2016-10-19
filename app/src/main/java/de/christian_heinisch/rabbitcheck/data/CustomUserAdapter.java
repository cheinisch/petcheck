package de.christian_heinisch.rabbitcheck.data;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import de.christian_heinisch.rabbitcheck.ItemDetailActivity;
import de.christian_heinisch.rabbitcheck.R;

/**
 * Created by chris on 09.10.2016.
 */
public class CustomUserAdapter extends ArrayAdapter<ListItem> {

    public CustomUserAdapter(Context context, ArrayList<ListItem> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ListItem user = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);

        }

        final TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvBeschreibung = (TextView) convertView.findViewById(R.id.textBeschreibung);

        tvName.setText(user.name);
        tvBeschreibung.setText(user.beschreibung);

        tvName.setTag(user);
        tvName.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                openitem(view);

            }
        });

        tvBeschreibung.setTag(user);
        tvBeschreibung.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                openitem(view);
            }
        });

        return convertView;

    }

    public void openitem(View view){

        ListItem user = (ListItem) view.getTag();
        Intent intent = new Intent(getContext(), ItemDetailActivity.class);

        //Werte an DetailActivity übergeben
        intent.putExtra("Titelleiste", user.name);
        intent.putExtra("Beschreibung", user.langtext);
        intent.putExtra("URL", user.bild);


        getContext().startActivity(intent);

    }



}
