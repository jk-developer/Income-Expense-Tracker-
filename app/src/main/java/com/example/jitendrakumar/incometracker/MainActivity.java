package com.example.jitendrakumar.incometracker;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        android.support.v7.widget.Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        drawerLayout = findViewById( R.id.drawer_layout );
        NavigationView navigationView = findViewById( R.id.nav_view );

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
                break;

            case R.id.nav_login:
                getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                        new LoginFragment()).commit();
                break;

            case R.id.nav_income:
                getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                        new IncomeFragment()).commit();
                break;

            case R.id.nav_expense:
                getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                        new ExpenseFragment()).commit();
                break;

            case R.id.nav_report:
                getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                        new ReportFragment()).commit();
                break;

            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                        new AboutFragment()).commit();
                break;

            case R.id.nav_setting:
            getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                    new SettingFragment()).commit();
            break;

            case R.id.nav_logout:
                Toast.makeText( this, "logout action", Toast.LENGTH_SHORT ).show();
        }
        drawerLayout.closeDrawer( GravityCompat.START );
        return true;
    }
}
