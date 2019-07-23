package io.turntotech.android.navigation;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
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

        List<Company> companyDataList = new ArrayList<>();
        companyDataList.add(new Company("https://actdental.com/wp-content/uploads/2015/12/apple.png", "Apple", "APPL","$90.54"));
        companyDataList.add(new Company("https://gabaknow.com/english/wp-content/uploads/2016/09/revised-google-logo-200x200.gif", "Google", "GOOG","$110.83"));
        companyDataList.add(new Company("https://unitedwedream.org/wp-content/uploads/2017/07/Twitter-Thumbnail.jpg", "Twitter", "TWTR","$76.83"));
        companyDataList.add(new Company("https://www.mactrast.com/wp-content/uploads/2014/02/tesla-logo_thumb.jpg", "Tesla","TSLA","$93.73"));

        listViewCompanyName = view.findViewById(R.id.listViewCompanyName);
        CompanyListAdapter adapter = new CompanyListAdapter( getContext(),companyDataList);
        listViewCompanyName.setAdapter(adapter);
        adapter.notifyDataSetChanged();



        listViewCompanyName.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView imageView =  view.findViewById(R.id.imgViewCompanyLogo);
                Bitmap bm=((BitmapDrawable)imageView.getDrawable()).getBitmap();
                showDetails(position, bm);
            }

        });

        return view;
    }

    public void showDetails(int index, Bitmap bm) {

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        ProductListFrag productListFrag = new ProductListFrag();
        productListFrag.companyImage = bm;
        productListFrag.selectedIndex = index;
        fragmentTransaction.add(R.id.frag_container, productListFrag);
        fragmentTransaction.addToBackStack("dtl");
        fragmentTransaction.commit();
    }


}
