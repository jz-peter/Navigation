package io.turntotech.android.navigation.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.turntotech.android.navigation.MainActivity;
import io.turntotech.android.navigation.R;
import io.turntotech.android.navigation.model.LocalDatabase;
import io.turntotech.android.navigation.model.entity.Company;

public class AddCompanyFrag extends Fragment {

    EditText editTxtCompanyName;
    EditText editTxtCompanyStock;
    EditText editTxtCompanyLogoUrl;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_add_company, container, false);

        // Toolbar configure
        TextView titleView = view.findViewById(R.id.txtBarTitle);
        titleView.setText(R.string.AddCompany);

        //Omitting unused buttons:
        view.findViewById(R.id.btnBack).setVisibility(View.GONE);
        view.findViewById(R.id.imgBtnAdd).setVisibility(View.GONE);

        Button btnCancel = view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        Button btnSave = view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddCompanyToDb();
            }

        });

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

            InsertCompanyIntoDb(company);
            getFragmentManager()
                    .beginTransaction()
                    .remove(AddCompanyFrag.this)
                    .commit();

            Activity activity = getActivity();
            hideKeyboard(activity);
        }
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

    public void AddCompanyToDb() {

        Company company;
        String companyName = editTxtCompanyName.getText().toString();
        String companyStock = editTxtCompanyStock.getText().toString();
        String companyLogoUrl = editTxtCompanyLogoUrl.getText().toString();

        company = new Company(companyLogoUrl, companyName, companyStock);
        company.setCompanyName(companyName);
        company.setCompanyStock(companyStock);
        company.setCompanyLogoUrl(companyLogoUrl);

        InsertCompanyIntoDb(company);
        getFragmentManager()
                .beginTransaction()
                .remove(AddCompanyFrag.this)
                .commit();

        hideKeyboard(getActivity());
    }

    public static void hideKeyboard(Activity activity) {

        InputMethodManager inputManager = (InputMethodManager)
                activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View currentFocusedView = activity.getCurrentFocus();
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}