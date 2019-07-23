package io.turntotech.android.navigation;

import android.graphics.Bitmap;
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

import io.turntotech.android.navigation.model.entity.Product;

public class ProductListFrag extends Fragment {

    public int selectedIndex;
    public Bitmap companyImage;
    ListView listViewProductName;
    ImageView imgViewCompanyLogo;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_product_list, container, false);

        imgViewCompanyLogo = view.findViewById(R.id.imgViewCompanyLogo);
        imgViewCompanyLogo.setImageBitmap(companyImage);

        final List<Product> productDataList = new ArrayList<>();
        productDataList.add (new Product("MacBook Air","https://images-na.ssl-images-amazon.com/images/I/31U4AHnZNuL.jpg","$1,729.94","https://www.apple.com/macbook-air/"));
        productDataList.add (new Product("iPad","https://images-na.ssl-images-amazon.com/images/I/614waGcr5JL._SL1500_.jpg", "$249.00","https://www.apple.com/ipad-9.7/"));
        productDataList.add (new Product("iPhone","https://images-na.ssl-images-amazon.com/images/I/414g7Ig0KZL.jpg", "$1,349.00","https://www.apple.com/iphone-xs/"));

        listViewProductName = view.findViewById(R.id.listViewProductName);
        ProductListAdapter adapter = new ProductListAdapter( getContext(),productDataList);
        listViewProductName.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        listViewProductName.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDetails(productDataList.get(position).getProductUrl());
            }

        });

        return view;
    }

    public void showDetails(String productUrl) {

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        WebViewFrag webViewFrag = new WebViewFrag();
        webViewFrag.url = productUrl;
        fragmentTransaction.add(R.id.frag_container, webViewFrag);
        fragmentTransaction.addToBackStack("dtl");
        fragmentTransaction.commit();
    }
}