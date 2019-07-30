package io.turntotech.android.navigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class AddProductFrag extends Fragment {

    EditText editTxtProductName;
    EditText editTxtProductUrl;
    EditText editTxtProductImgUrl;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_add_product, container, false);

        editTxtProductName = view.findViewById(R.id.editTxtProductName);
        editTxtProductUrl = view.findViewById(R.id.editTxtProductUrl);
        editTxtProductImgUrl = view.findViewById(R.id.editTxtProductImgUrl);

        return view;

    }
}

