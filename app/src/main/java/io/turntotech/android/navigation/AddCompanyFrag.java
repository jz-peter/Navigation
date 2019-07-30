package io.turntotech.android.navigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import io.turntotech.android.navigation.model.entity.Company;

public class AddCompanyFrag extends Fragment {

    EditText editTxtCompanyName;
    EditText editTxtCompanyStock;
    EditText editTxtCompanyLogoUrl;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_add_company, container, false);

        editTxtCompanyName = view.findViewById(R.id.editTxtCompanyName);
        editTxtCompanyStock = view.findViewById(R.id.editTxtCompanyStock);
        editTxtCompanyLogoUrl = view.findViewById(R.id.editTxtCompanyLogoUrl);

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.save_company_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.SaveCompanyItem) {
            Company company;
            String companyName = editTxtCompanyName.getText().toString();
            String companyStock = editTxtCompanyStock.getText().toString();
            String companyLogoUrl = editTxtCompanyLogoUrl.getText().toString();

            company = new Company(companyLogoUrl, companyName, companyStock);
            company.setCompanyName(companyName);
            company.setCompanyStock(companyStock);
            company.setCompanyLogoUrl(companyLogoUrl);

            InsertCompanyIntoDb(company); }

        return super.onOptionsItemSelected(item);
    }

    //Insert New Company into Local Database.
    private void InsertCompanyIntoDb(final Company company) {

        //Create instance of database:
        final LocalDatabase localDB = LocalDatabase.getAppDatabase(getContext());

        //Use Executor for background task on different thread:
        final Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(new Runnable() {
            @Override
            public void run() {
                // Caused by: java.lang.IllegalStateException: Cannot access database on the main thread since it may potentially lock the UI for a long period of time.
                localDB.daoAccess().insertCompany(company);
            }
        });
    }
}