package io.turntotech.android.navigation;

import android.arch.lifecycle.Observer;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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


public class CompanyListFrag extends Fragment {

    LocalDatabase LocalDB = null;
    ListView listViewCompanyName;
    final List<Company> companyDataList = new ArrayList<>();
    CompanyListAdapter adapter;
    int selectedPosition;
    long selectedId;
    long companyId;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_company_list, container, false);

        listViewCompanyName = view.findViewById(R.id.listViewCompanyName);
        adapter = new CompanyListAdapter(getContext(), companyDataList);
        listViewCompanyName.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //Register ListView to Context Menu:
        registerForContextMenu(listViewCompanyName);

        listViewCompanyName.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView imageView = view.findViewById(R.id.imgViewCompanyLogo);
                Bitmap bm = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                showProducts(position, bm);

            }
        });
        companyId = listViewCompanyName.getId();
        setHasOptionsMenu(true);
        getListOfCompany();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_company_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //Create Context Menu:
    @Override
    public void onCreateContextMenu (ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Please Select");
        menu.add(0, 101, 0, "Edit");
        menu.add(0, 102, 1, "Delete");

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        selectedPosition = info.position;
        selectedId = info.id;
        Company company =  companyDataList.get(selectedPosition);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == 101) {
            showCompanyEditDialog();
        }
        else if (item.getItemId() == 102) {
            Company company = companyDataList.get(selectedPosition);
            deleteCompanyFromDb(company);
        }
        return false;
    }

    private void showCompanyEditDialog () {

        LayoutInflater inflater = this.getLayoutInflater();
        View customView = inflater.inflate(R.layout.frag_edit_company, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(customView);

        final AlertDialog alertDialog = builder.create();

        Button btn = customView.findViewById(R.id.onSaveCompany);
        final Company company =  companyDataList.get(selectedPosition);
        final EditText editCompanyName = customView.findViewById(R.id.editTxtCompanyName);
        final EditText editCompanyStock = customView.findViewById(R.id.editTxtCompanyStock);
        final EditText editCompanyLogo = customView.findViewById(R.id.editTxtCompanyLogoUrl);

        editCompanyName.setText(company.getCompanyName() );
        editCompanyStock.setText(company.getCompanyStock() );
        editCompanyLogo.setText(company.getCompanyLogoUrl() );

        // Adding onClick on the button programmatically
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = editCompanyName.getText().toString();
                String stock = editCompanyStock.getText().toString();
                String logo = editCompanyLogo.getText().toString();

                company.setCompanyName( name );
                company.setCompanyStock( stock );
                company.setCompanyLogoUrl( logo );

                updateCompanyInDb(company);

                alertDialog.dismiss();
            }
        });
        // Any variable used in method inside anonymous inner class needs to be final
        alertDialog.show();
    }

    //Edit Company in database:
    private void updateCompanyInDb (final Company company) {

        //Use Executor for background task on different thread:
        final Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(new Runnable() {
            @Override
            public void run() {
                // Caused by: java.lang.IllegalStateException: Cannot access database on the main thread since it may potentially lock the UI for a long period of time.
                LocalDB.daoAccess().updateCompany(company);
            }
        });
    }

    //Delete Company from database:
    private void deleteCompanyFromDb(final Company company) {

        //Use Executor for background task on different thread:
        final Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(new Runnable() {
            @Override
            public void run() {
                // Caused by: java.lang.IllegalStateException: Cannot access database on the main thread since it may potentially lock the UI for a long period of time.
                LocalDB = LocalDatabase.getAppDatabase(getContext());
                LocalDB.daoAccess().deleteCompany(company);
            }
        });
    }

    public void showProducts ( int index, Bitmap bm){

            LocalDatabase.selectedCompany =  companyDataList.get(index);



            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            ProductListFrag productListFrag = new ProductListFrag();
            productListFrag.companyImage = bm;
            productListFrag.selectedCompanyIndex = index;
            fragmentTransaction.add(R.id.frag_container, productListFrag);
            fragmentTransaction.addToBackStack("dtl");
            fragmentTransaction.commit();
    }

    //Get all names from database:
    private void getListOfCompany () {

        LocalDB = LocalDatabase.getAppDatabase(getContext());
        LocalDB.daoAccess().fetchAllCompanies().observe(CompanyListFrag.this, new Observer<List<Company>>() {
            @Override
            public void onChanged(@Nullable List<Company> companyDataList) {
                // Update UI with Company
                // Example: You can update a list view with the above list.

                //Clear view before fetching all names to prevent duplicates:
                CompanyListFrag.this.companyDataList.clear();
                //Add all names to view:
                CompanyListFrag.this.companyDataList.addAll(companyDataList);
                adapter.notifyDataSetChanged();
            }
        });
    }


//    }private void getCompanyProduct () {
//
//        LocalDB = LocalDatabase.getAppDatabase(getContext());
//        LocalDB.daoAccess().fetchAllCompanyProducts(companyId).observe(CompanyListFrag.this, new Observer<List<Product>>() {
//            @Override
//            public void onChanged(@Nullable List<Product> companyDataList) {
//
//                //Clear view before fetching all names to prevent duplicates:
//                CompanyListFrag.this.companyDataList.clear();
//                //Add all names to view:
//                CompanyListFrag.this.companyDataList.addAll(companyDataList);
//                adapter.notifyDataSetChanged();
//            }
//        });


}