package de.christian_heinisch.petcheck.data;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.christian_heinisch.petcheck.ItemDetailActivity;
import de.christian_heinisch.petcheck.R;

/**
 * Created by chris on 09.10.2016.
 */
public class MenuListAdapter extends ArrayAdapter<ListItemMenu> {

    public MenuListAdapter(Context context, ArrayList<ListItemMenu> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ListItemMenu user = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_menu, parent, false);

        }

        final TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvBeschreibung = (TextView) convertView.findViewById(R.id.textBeschreibung);

        tvName.setText(user.name);

        return convertView;

    }

}
