package io.turntotech.android.navigation;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ListView;

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

    //Create main menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    //Selected item in main menu:
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        showAddDialog();
//        return super.onOptionsItemSelected(item);
//    }

}