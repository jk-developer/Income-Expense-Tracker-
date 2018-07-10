package com.example.jitendrakumar.incometracker.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.fragments.AboutFragment;
import com.example.jitendrakumar.incometracker.fragments.ExpenseFragment;
import com.example.jitendrakumar.incometracker.fragments.HomeFragment;
import com.example.jitendrakumar.incometracker.fragments.IncomeFragment;
import com.example.jitendrakumar.incometracker.fragments.LoginFragment;
import com.example.jitendrakumar.incometracker.fragments.IncomeReportFragment;
import com.example.jitendrakumar.incometracker.fragments.TobePaidFragment;
import com.example.jitendrakumar.incometracker.fragments.TobeTakenFragment;
import com.example.jitendrakumar.incometracker.fragments.TodoTaskFragment;
import com.example.jitendrakumar.incometracker.helper.SessionManagement;
import com.example.jitendrakumar.incometracker.helper.Task;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    public android.support.v7.widget.Toolbar toolbar;
    UserSessionManagement userSessionManagement;
    ArrayList<Task> tasks = new ArrayList<>(  );
    HomeFragment homeFragment;
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        session = new SessionManagement( MainActivity.this );
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
         String username = session.getUserName();
         switch (item.getItemId()){
            case R.id.nav_home:
                if(username!=null)
                {
                    getSupportFragmentManager().beginTransaction()
                            .replace( R.id.fragment_container, new HomeFragment())
                            .addToBackStack( null )
                            .commit();
                    toolbar.setTitle( "Income Expense Tracker" );

                    break;
                }
                else
                    {
                        Toast.makeText( MainActivity.this, "Please First login into your account!!!",Toast.LENGTH_SHORT ).show();
                        break;
                    }


            case R.id.nav_login:
                if(username!=null)
                {
                    Toast.makeText( MainActivity.this, session.getUserName()+", you are already Logged in!!!", Toast.LENGTH_SHORT ).show();
                    break;
                }else
                {
                    getSupportFragmentManager().beginTransaction()
                            .replace( R.id.fragment_container, new LoginFragment())
                            .addToBackStack( null )
                            .commit();
                    toolbar.setTitle( "Login" );
                    break;
                }


            case R.id.nav_income:
                if(username!=null){
                    getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                            new IncomeFragment())
                            .addToBackStack( null )
                            .commit();
                    toolbar.setTitle( "Income" );
                    break;

                }else
                {
                    Toast.makeText( MainActivity.this, "Please First login into your account!!!",Toast.LENGTH_SHORT ).show();
                    break;
                }

            case R.id.nav_expense:
                if(username!=null){
                    getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                            new ExpenseFragment())
                            .addToBackStack( null )
                            .commit();
                    toolbar.setTitle( "Expense" );
                    break;
                }else
                {
                    Toast.makeText( MainActivity.this, "Please First login into your account!!!",Toast.LENGTH_SHORT ).show();
                    break;
                }


            case R.id.nav_income_report:
                if(username!=null){
                    getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                            new IncomeReportFragment())
                            .addToBackStack( null )
                            .commit();
                    toolbar.setTitle( "Income Report" );
                    break;

                }else
                {
                    Toast.makeText( MainActivity.this, "Please First login into your account!!!",Toast.LENGTH_SHORT ).show();
                    break;
                }

            case R.id.nav_expense_report:
                if(username!=null){
                    getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                            new IncomeReportFragment())
                            .addToBackStack( null )
                            .commit();
                    toolbar.setTitle( "Expense Report" );
                    break;
                }else
                {
                    Toast.makeText( MainActivity.this, "Please First login into your account!!!",Toast.LENGTH_SHORT ).show();
                    break;
                }


            case R.id.nav_todo_list:
                if(username!=null){
                    getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                            new TodoTaskFragment())
                            .addToBackStack( null )
                            .commit();
                    toolbar.setTitle( "Todo Task" );
                    break;
                }else
                {
                    Toast.makeText( MainActivity.this, "Please First login into your account!!!",Toast.LENGTH_SHORT ).show();
                    break;
                }


            case R.id.nav_tobe_paid:
                if(username!=null)
                {
                    getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                            new TobePaidFragment())
                            .addToBackStack( null )
                            .commit();
                    toolbar.setTitle( "To be Paid To" );
                    break;
                }else
                {
                    Toast.makeText( MainActivity.this, "Please First login into your account!!!",Toast.LENGTH_SHORT ).show();
                    break;
                }


            case R.id.nav_tobe_taken:
                if(username!=null){
                    getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                            new TobeTakenFragment())
                            .addToBackStack( null )
                            .commit();
                    toolbar.setTitle( "To be Taken From" );
                    break;
                }else
                {
                    Toast.makeText( MainActivity.this, "Please First login into your account!!!",Toast.LENGTH_SHORT ).show();
                    break;
                }


            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                        new AboutFragment())
                        .addToBackStack( null )
                        .commit();
                toolbar.setTitle( "About" );
                break;

            case R.id.nav_logout:
                if(username!=null){
                    session.removeUser();
                    getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                            new HomeFragment())
                            .addToBackStack( null )
                            .commit();
                    toolbar.setTitle( "Income Expense Tracker" );
                    Toast.makeText( MainActivity.this, "Logged out successfully!!!" , Toast.LENGTH_SHORT).show();
                    break;
                }else
                {
                    Toast.makeText( MainActivity.this, "Please First login into your account!!!",Toast.LENGTH_SHORT ).show();
                    break;
                }


        }
        drawerLayout.closeDrawer( GravityCompat.START );
        return true;
    }


}
