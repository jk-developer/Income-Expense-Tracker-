package com.example.jitendrakumar.incometracker.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.database.ExpenseDatabaseHelper;
import com.example.jitendrakumar.incometracker.fragments.AboutAppFragment;
import com.example.jitendrakumar.incometracker.fragments.AboutFragment;
import com.example.jitendrakumar.incometracker.fragments.AddExpenseFragment;
import com.example.jitendrakumar.incometracker.fragments.HomeFragment;
import com.example.jitendrakumar.incometracker.fragments.AddIncomeFragment;
import com.example.jitendrakumar.incometracker.fragments.LoginFragment;
import com.example.jitendrakumar.incometracker.fragments.IncomeReportFragment;
import com.example.jitendrakumar.incometracker.fragments.BorrowFragment;
import com.example.jitendrakumar.incometracker.fragments.LendFragment;
import com.example.jitendrakumar.incometracker.helper.SessionManagement;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    public android.support.v7.widget.Toolbar toolbar;;
    HomeFragment homeFragment;
    SessionManagement session;
    ExpenseDatabaseHelper expenseDatabaseHelper;

    private CharSequence charSequence[] = {"Income", "Expense", "Paying amount", "getting amount"};
    boolean[] Checked = new boolean[charSequence.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        expenseDatabaseHelper = new ExpenseDatabaseHelper( this );
        for(int i=0;i<charSequence.length;i++)
            Checked[i] = false;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.action_categories:
                Toast.makeText( MainActivity.this, "Categories action clicked", Toast.LENGTH_SHORT ).show();
                return true;

            case R.id.action_rate_us:
                Toast.makeText( MainActivity.this, "Categories action clicked", Toast.LENGTH_SHORT ).show();
                return true;

            case R.id.action_sendfeedback:
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage( "We would love to hear your feedback  or suggestions on how we can improve your experiance!" );
                builder.setTitle( "Feedback" );
                builder.setIcon( R.drawable.ic_mail);
                builder.setPositiveButton( "Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mailurl = "mailto:";
                        Uri uri = Uri.parse( mailurl );
                        Intent intent = new Intent( Intent.ACTION_SEND );
                        intent.setData(uri);
                        String[] to = {"jkgupta15798@gmail.com"};
                        intent.putExtra( Intent.EXTRA_EMAIL, to );
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Income Expense Tracker Feedback");
                        intent.setType( "message/rfc822" );
                        startActivity(Intent.createChooser(intent, "Send email using ..."));
                    }
                } );

                builder.setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        builder.setCancelable( true );
                    }
                } );

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;

            case R.id.action_reset:
                final AlertDialog.Builder builderReset = new AlertDialog.Builder(MainActivity.this);
                builderReset.setIcon( R.drawable.ic_reset);
                builderReset.setTitle( "Choose " );
                 builderReset.setMultiChoiceItems( charSequence, new boolean[charSequence.length], new DialogInterface.OnMultiChoiceClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        Checked[which]=isChecked;

                     }
                 } );


                builderReset.setPositiveButton( "Reset", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(Checked[0]==false && Checked[1]==false && Checked[2]==false && Checked[3]==false)
                        {
                            Toast.makeText( MainActivity.this, "First Choose some items ",Toast.LENGTH_SHORT ).show();
                            builderReset.setCancelable( false);
                        }
                        else {
                            for(int i=0;i<charSequence.length;i++)
                            {
                                Toast.makeText( MainActivity.this, Checked[i]+" ",Toast.LENGTH_SHORT ).show();

                            }

                            //    expenseDatabaseHelper.deleteAllData();
                        }

                    }
                } );

                builderReset.setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      for(int j=0;j<charSequence.length;j++)
                          Checked[j] = false;

                      builderReset.setCancelable( true );
                    }
                } );

                AlertDialog alertDialogReset = builderReset.create();
                alertDialogReset.show();
                return true;

            default:
                for(int j=0;j<charSequence.length;j++)
                    Checked[j] = false;
                return super.onOptionsItemSelected( item );
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
                            new AddIncomeFragment())
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
                            new AddExpenseFragment())
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
                    Intent in = new Intent( MainActivity.this, TodoActivity.class );
                    startActivity(in);
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
                            new BorrowFragment())
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
                            new LendFragment())
                            .addToBackStack( null )
                            .commit();
                    toolbar.setTitle( "To be Taken From" );
                    break;
                }else
                {
                    Toast.makeText( MainActivity.this, "Please First login into your account!!!",Toast.LENGTH_SHORT ).show();
                    break;
                }

            case R.id.nav_about_app:
                getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                        new AboutAppFragment())
                        .addToBackStack( null )
                        .commit();
                toolbar.setTitle( "About App" );
                break;

            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                        new AboutFragment())
                        .addToBackStack( null )
                        .commit();
                toolbar.setTitle( "About me" );
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