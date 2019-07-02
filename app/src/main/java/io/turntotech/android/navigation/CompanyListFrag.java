package io.turntotech.android.navigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import io.turntotech.android.navigation.model.entity.Company;


public class CompanyListFrag extends Fragment {


    ListView listViewCompanyName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_company_list, container, false);

        List<Company> dataList = new ArrayList<>();
        dataList.add(new Company("https://actdental.com/wp-content/uploads/2015/12/apple.png", "Apple", "APPL","$90.54"));
        dataList.add(new Company("https://gabaknow.com/english/wp-content/uploads/2016/09/revised-google-logo-200x200.gif", "Google", "GOOG","$110.83"));
        dataList.add(new Company("https://unitedwedream.org/wp-content/uploads/2017/07/Twitter-Thumbnail.jpg", "Twitter", "TWTR","$76.83"));
        dataList.add(new Company("https://www.mactrast.com/wp-content/uploads/2014/02/tesla-logo_thumb.jpg", "Tesla","TSLA","$93.73"));

        listViewCompanyName = view.findViewById(R.id.listViewCompanyName);
        CompanyListAdapter adapter = new CompanyListAdapter( getContext(),dataList);
        listViewCompanyName.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return view;
    }
}


