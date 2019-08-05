package io.turntotech.android.navigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.turntotech.android.navigation.model.LocalDatabase;
import io.turntotech.android.navigation.model.entity.Product;

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

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.save_product_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.SaveProductItem) {
            Product product;
            String productName = editTxtProductName.getText().toString();
            String productUrl = editTxtProductUrl.getText().toString();
            String productImgUrl = editTxtProductImgUrl.getText().toString();

            product = new Product(productName, productImgUrl, productUrl);
            product.setProductName(productName);
            product.setProductUrl(productUrl);
            product.setProductImgUrl(productImgUrl);
            product.setCompanyId( LocalDatabase.selectedCompany.getId() );

            InsertProductIntoDb(product); }

            return super.onOptionsItemSelected(item);
    }

    //Insert New Product into Local Database.
    private void InsertProductIntoDb(final Product product) {

        //Create instance of database:
        final LocalDatabase localDB = LocalDatabase.getAppDatabase(getContext());

        //Use Executor for background task on different thread:
        final Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(new Runnable() {
            @Override
            public void run() {
                // Caused by: java.lang.IllegalStateException: Cannot access database on the main thread since it may potentially lock the UI for a long period of time.
                localDB.daoAccess().insertProduct(product);
            }
        });
    }
}
