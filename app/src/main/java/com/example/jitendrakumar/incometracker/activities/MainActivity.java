package com.example.jitendrakumar.incometracker.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.fragments.AboutFragment;
import com.example.jitendrakumar.incometracker.fragments.ExpenseFragment;
import com.example.jitendrakumar.incometracker.fragments.HomeFragment;
import com.example.jitendrakumar.incometracker.fragments.IncomeFragment;
import com.example.jitendrakumar.incometracker.fragments.LoginFragment;
import com.example.jitendrakumar.incometracker.fragments.IncomeReportFragment;
import com.example.jitendrakumar.incometracker.fragments.SettingFragment;
import com.example.jitendrakumar.incometracker.fragments.TobePaidFragment;
import com.example.jitendrakumar.incometracker.fragments.TobeTakenFragment;
import com.example.jitendrakumar.incometracker.fragments.TodoTaskFragment;
import com.example.jitendrakumar.incometracker.helper.Task;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    public android.support.v7.widget.Toolbar toolbar;
    UserSessionManagement userSessionManagement;
    ArrayList<Task> tasks = new ArrayList<>(  );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );


        NavigationView navigationView = (NavigationView) findViewById( R.id.nav_view );
         toolbar = findViewById( R.id.toolbar );
         setSupportActionBar( toolbar );

         drawerLayout = findViewById( R.id.drawer_layout );

        navigationView.setNavigationItemSelectedListener( this );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener( toggle );
        toggle.syncState();
        if(savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem( R.id.nav_home );
        }

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen( GravityCompat.START )){
            drawerLayout.closeDrawer( GravityCompat.START );
        }else{
            super.onBackPressed();
            
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                        new HomeFragment()).commit();
                toolbar.setTitle( "Income Expense Tracker" );

                break;

            case R.id.nav_login:
                getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                        new LoginFragment()).commit();
                toolbar.setTitle( "Login" );

                break;

            case R.id.nav_income:
                getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                        new IncomeFragment()).commit();
                toolbar.setTitle( "Income" );
                break;

            case R.id.nav_expense:
                getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                        new ExpenseFragment()).commit();
                toolbar.setTitle( "Expense" );
                break;

            case R.id.nav_income_report:
                getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                        new IncomeReportFragment()).commit();
                toolbar.setTitle( "Income Report" );
                break;

            case R.id.nav_expense_report:
                getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                        new IncomeReportFragment()).commit();
                toolbar.setTitle( "Expense Report" );
                break;

            case R.id.nav_todo_list:
                getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                        new TodoTaskFragment()).commit();
                toolbar.setTitle( "Todo Task" );
                break;

            case R.id.nav_tobe_paid:
                getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                        new TobePaidFragment()).commit();
                toolbar.setTitle( "To be Paid To" );
                break;

            case R.id.nav_tobe_taken:
                getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                        new TobeTakenFragment()).commit();
                toolbar.setTitle( "To be Taken From" );
                break;

            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                        new AboutFragment()).commit();
                toolbar.setTitle( "About" );
                break;

            case R.id.nav_setting:
            getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                    new SettingFragment()).commit();
                toolbar.setTitle( "Setting" );
            break;

            case R.id.nav_logout:
                Toast.makeText( this, "logout action", Toast.LENGTH_SHORT ).show();

        }
        drawerLayout.closeDrawer( GravityCompat.START );
        return true;
    }


}
