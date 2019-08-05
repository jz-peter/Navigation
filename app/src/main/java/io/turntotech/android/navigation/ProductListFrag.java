package io.turntotech.android.navigation;

import android.arch.lifecycle.Observer;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.turntotech.android.navigation.model.LocalDatabase;
import io.turntotech.android.navigation.model.entity.Company;
import io.turntotech.android.navigation.model.entity.Product;

public class ProductListFrag extends Fragment {

    LocalDatabase LocalDB = null;
    int selectedPosition;
    int selectedCompanyIndex;
    long companyId;

    public Bitmap companyImage;
    ListView listViewProductName;
    ImageView imgViewCompanyLogo;
    final List<Product> productDataList = new ArrayList<>();
    ProductListAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_product_list, container, false);

        imgViewCompanyLogo = view.findViewById(R.id.imgViewCompanyLogo);
        imgViewCompanyLogo.setImageBitmap(companyImage);
        listViewProductName = view.findViewById(R.id.listViewProductName);

        adapter = new ProductListAdapter( getContext(),productDataList);
        listViewProductName.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        //Register ListView to Context Menu:
        registerForContextMenu(listViewProductName);

        listViewProductName.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDetails(productDataList.get(position).getProductUrl());
            }

        });
        setHasOptionsMenu(true);
        getListofProduct();
        return view;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.add_product_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //Create Context Menu:
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Please Select");
        menu.add(0, 201, 0, "Edit");
        menu.add(0, 202, 1, "Delete");

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        selectedPosition = info.position;
        Product product =  productDataList.get(selectedPosition);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == 201) {
            showProductEditDialog();
        }
        else if (item.getItemId()== 202){
            Product product = productDataList.get(selectedPosition);
            deleteProductFromDb(product);
        }
        return false;
    }

    private void showProductEditDialog () {

        LayoutInflater inflater = this.getLayoutInflater();
        View customView = inflater.inflate(R.layout.frag_edit_product, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(customView);

        final AlertDialog alertDialog = builder.create();

        Button btn = customView.findViewById(R.id.onSaveProduct);
        final Product product = productDataList.get(selectedPosition);
        final EditText editProductName = customView.findViewById(R.id.editTxtProductName);
        final EditText editProductUrl = customView.findViewById(R.id.editTxtProductUrl);
        final EditText editProductImgUrl = customView.findViewById(R.id.editTxtProductImgUrl);

        editProductName.setText(product.getProductName() );
        editProductUrl.setText(product.getProductUrl() );
        editProductImgUrl.setText(product.getProductImgUrl() );

        // Adding onClick to the button programmatically
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = editProductName.getText().toString();
                String url = editProductUrl.getText().toString();
                String image = editProductImgUrl.getText().toString();

                product.setProductName( name );
                product.setProductUrl( url );
                product.setProductImgUrl( image );

                updateProductInDb(product);

                alertDialog.dismiss();
            }
        });
        // Any variable used in method inside anonymous inner class needs to be final
        alertDialog.show();
    }

    //Edit Product in database:
    private void updateProductInDb (final Product product) {

        //Use Executor for background task on different thread:
        final Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(new Runnable() {
            @Override
            public void run() {
                // Caused by: java.lang.IllegalStateException: Cannot access database on the main thread since it may potentially lock the UI for a long period of time.
                LocalDB.daoAccess().updateProduct(product);
            }
        });
    }

    //Delete Company from database:
    private void deleteProductFromDb(final Product product) {

        //Use Executor for background task on different thread:
        final Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(new Runnable() {
            @Override
            public void run() {
                // Caused by: java.lang.IllegalStateException: Cannot access database on the main thread since it may potentially lock the UI for a long period of time.
                LocalDB = LocalDatabase.getAppDatabase(getContext());
                LocalDB.daoAccess().deleteProduct(product);
            }
        });
    }

    public void showDetails(String productUrl) {

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        WebViewFrag webViewFrag = new WebViewFrag();
        webViewFrag.url = productUrl;
        fragmentTransaction.add(R.id.frag_container, webViewFrag);
        fragmentTransaction.addToBackStack("dtl");
        fragmentTransaction.commit();
    }

    public void getListofProduct() {

        LocalDB = LocalDatabase.getAppDatabase(getContext());
        LocalDB.daoAccess().fetchAllCompanyProducts(LocalDatabase.selectedCompany.getId()).observe(ProductListFrag.this, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> productDataList) {

                //Clear view before fetching all names to prevent duplicates:
                ProductListFrag.this.productDataList.clear();
                //Add all names to view:
                ProductListFrag.this.productDataList.addAll(productDataList);
                adapter.notifyDataSetChanged();
            }
        });
    }
}