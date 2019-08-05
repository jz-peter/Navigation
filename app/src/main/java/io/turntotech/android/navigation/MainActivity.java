package io.turntotech.android.navigation;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        CompanyListFrag companyListFrag = new CompanyListFrag();
        fragmentTransaction.add(R.id.frag_container, companyListFrag);
        fragmentTransaction.commit();
    }

    //Selected item in main menu:
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.AddCompanyItem) {
            showAddCompanyFrag();
        } else if (item.getItemId() == R.id.AddProductItem){
            showAddProductFrag();
        }
        return super.onOptionsItemSelected(item);
    }

    public void showAddCompanyFrag(){

        //check how many fragments are stacked.
        int size = getSupportFragmentManager().getFragments().size();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        AddCompanyFrag addCompanyFrag = new AddCompanyFrag();
        fragmentTransaction.add(R.id.frag_container, addCompanyFrag);
        fragmentTransaction.addToBackStack("dtl");
        fragmentTransaction.commit();
    }

    public void showAddProductFrag(){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        AddProductFrag addProductFrag = new AddProductFrag();
        fragmentTransaction.add(R.id.frag_container, addProductFrag);
        fragmentTransaction.addToBackStack("dtl");
        fragmentTransaction.commit();
    }

}