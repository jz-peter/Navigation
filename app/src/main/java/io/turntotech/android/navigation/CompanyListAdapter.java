package io.turntotech.android.navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class CompanyListAdapter extends ArrayAdapter<Company> {

    public CompanyListAdapter(Context context, List<Company> values) {
        super(context, R.layout.row_layout, values);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Company company = getItem(position);

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.row_layout, parent, false);
            convertView = rowView;
        }

        TextView textViewCompanyName = (TextView) convertView.findViewById(R.id.textViewCompanyName);
        TextView textViewCompanyStock = (TextView) convertView.findViewById(R.id.textViewCompanyStock);
        TextView textViewCompanyLogo = (TextView) convertView.findViewById(R.id.textViewCompanyLogo);

        textViewCompanyName.setText(company.getCompanyName());
        textViewCompanyStock.setText(company.getCompanyStock());
        textViewCompanyLogo.setText(company.getCompanyLogoUrl());


        return convertView;
    }






}
