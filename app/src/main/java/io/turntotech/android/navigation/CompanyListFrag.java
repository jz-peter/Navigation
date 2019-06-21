package io.turntotech.android.navigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class CompanyListFrag extends Fragment {


    ListView listViewCompanyName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_company_list, container, false);

        List<Company> dataList = new ArrayList<>();
        dataList.add(new Company("Apple", "APPL", "img-companyLogo_Apple.png"));
        dataList.add(new Company("Google", "GOOG", "img-companyLogo_Google.png"));
        dataList.add(new Company("Twitter", "TWTR", "img-companyLogo_Twitter.png"));
        dataList.add(new Company("Tesla", "TSLA", "img-companyLogo_Tesla.png"));



        listViewCompanyName = (ListView) view.findViewById(R.id.listViewCompanyName);
        CompanyListAdapter adapter = new CompanyListAdapter( getContext() ,dataList);
        listViewCompanyName.setAdapter(adapter);

        adapter.notifyDataSetChanged();


        return view;
    }

}


