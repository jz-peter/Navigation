package io.turntotech.android.navigation;

import android.arch.lifecycle.Observer;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.turntotech.android.navigation.model.LocalDatabase;
import io.turntotech.android.navigation.model.entity.Company;


public class CompanyListFrag extends Fragment {

    LocalDatabase LocalDB = null;
    ListView listViewCompanyName;
    final List<Company> companyDataList = new ArrayList<>();
    CompanyListAdapter adapter;
    int selectedPosition;


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
        menu.add(0, v.getId(), 0, "Edit");
        menu.add(0, v.getId(), 0, "Delete");

//        AdapterView.AdapterContextMenuInfo info =
//                (AdapterView.AdapterContextMenuInfo) menuInfo;
//
//        selectedPosition = info.position;
//        Company company =  companyDataList.get(selectedPosition);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle()== "Delete"){
            Company company = companyDataList.get(selectedPosition);
            deleteCompanyFromDb(company);
        }
        else if (item.getTitle()== "Edit") {
            //Edit company method
        }
        return true;
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

            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            ProductListFrag productListFrag = new ProductListFrag();
            productListFrag.companyImage = bm;
            productListFrag.selectedIndex = index;
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
}