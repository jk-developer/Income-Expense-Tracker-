package com.example.jitendrakumar.incometracker.activities;

import android.app.ActivityOptions;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.database.BorrowDatabaseHelper;
import com.example.jitendrakumar.incometracker.database.ExpenseDatabaseHelper;
import com.example.jitendrakumar.incometracker.database.IncomeDatabaseHelper;
import com.example.jitendrakumar.incometracker.database.LendDatabaseHelper;
import com.example.jitendrakumar.incometracker.fragments.AddExpenseFragment;
import com.example.jitendrakumar.incometracker.fragments.BorrowReportFragment;
import com.example.jitendrakumar.incometracker.fragments.ExpenseReportFragment;
import com.example.jitendrakumar.incometracker.fragments.HomeFragment;
import com.example.jitendrakumar.incometracker.fragments.AddIncomeFragment;
import com.example.jitendrakumar.incometracker.fragments.LendReportFragment;
import com.example.jitendrakumar.incometracker.fragments.LoginFragment;
import com.example.jitendrakumar.incometracker.fragments.IncomeReportFragment;
import com.example.jitendrakumar.incometracker.fragments.BorrowFragment;
import com.example.jitendrakumar.incometracker.fragments.LendFragment;
import com.example.jitendrakumar.incometracker.fragments.SignupFragment;
import com.example.jitendrakumar.incometracker.helper.SessionManagement;

import java.util.Calendar;

import static android.support.design.widget.Snackbar.*;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    public android.support.v7.widget.Toolbar toolbar;;
    HomeFragment homeFragment;
    SessionManagement session;
    ExpenseDatabaseHelper expenseDatabaseHelper;
    IncomeDatabaseHelper incomeDatabaseHelper;
    BorrowDatabaseHelper borrowDatabaseHelper;
    LendDatabaseHelper lendDatabaseHelper;
    FloatingActionButton fab;

    private CharSequence charSequence[] = {"Income", "Expense", "Borrow", "Lend"};
    private  CharSequence report[] = {"Between two months, Barchart, Piechart"};
    private CharSequence notifications[] = {"Income", "Expense", "Todo Task"};
    boolean[] Checked = new boolean[charSequence.length];
    boolean[] checkedReport = new boolean[report.length];
    boolean[] checkNotif = new boolean[notifications.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById( R.id.drawer_layout );

        expenseDatabaseHelper = new ExpenseDatabaseHelper( this );
        incomeDatabaseHelper = new IncomeDatabaseHelper( this );
        borrowDatabaseHelper = new BorrowDatabaseHelper( this );
        lendDatabaseHelper = new LendDatabaseHelper( this );

        for(int i=0;i<charSequence.length;i++)
            Checked[i] = false;

        for(int j=0;j<report.length;j++)
            checkedReport[j] = false;

        for(int k=0;k<notifications.length;k++)
            checkNotif[k] = false;

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
        String user = session.getUserName();
        switch (item.getItemId()) {

                 case R.id.action_rate_us:
                        Toast.makeText( MainActivity.this, "Rate us action clicked", Toast.LENGTH_SHORT ).show();
                        return true;

                    case R.id.action_sendfeedback:
                        final AlertDialog.Builder builder = new AlertDialog.Builder( MainActivity.this );
                        builder.setMessage( "We would love to hear your feedback  or suggestions on how we can improve your experiance!" );
                        builder.setTitle( "Feedback" );
                        builder.setIcon( R.drawable.ic_mail );
                        builder.setPositiveButton( "Send", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Uri uri = Uri.parse("mailto:" );
                                Intent intent = new Intent( Intent.ACTION_SENDTO);
                                intent.setData(uri);
                                String[] to = {"jkgupta4398@gmail.com"};
                                intent.putExtra( Intent.EXTRA_EMAIL, to );
                                intent.putExtra( Intent.EXTRA_SUBJECT,"Income Expense Tracker Feedback " );
                                startActivity(intent);
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

            case R.id.nav_logout:
                String isUserValid = session.getUserName();
                if(isUserValid!=null){
                    session.removeUser();
                    getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                            new HomeFragment())
                            .addToBackStack( null )
                            .commit();
                    toolbar.setTitle( "Income Expense Tracker" );
                    Toast.makeText( MainActivity.this, "Logged out successfully!!!" , Toast.LENGTH_SHORT).show();
                    return true;
                }else
                {
                    Toast.makeText( MainActivity.this, "Please First login into your account!!!",Toast.LENGTH_SHORT ).show();
                   return true;
                }

                case R.id.action_reset:
                        for (int i = 0; i < charSequence.length; i++) {
                            Checked[i] = false;
                        }
                        final AlertDialog.Builder builderReset = new AlertDialog.Builder( MainActivity.this );
                        builderReset.setIcon( R.drawable.ic_reset );
                        builderReset.setTitle( "Choose " );
                        builderReset.setMultiChoiceItems( charSequence, new boolean[charSequence.length], new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                Checked[which] = isChecked;

                            }
                        } );

                        builderReset.setPositiveButton( "Reset", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (Checked[0] == false && Checked[1] == false && Checked[2] == false && Checked[3] == false) {
                                    Toast.makeText( MainActivity.this, "First Choose some items ", Toast.LENGTH_SHORT ).show();
                                    builderReset.setCancelable( false );
                                } else {
                                    if (Checked[0] == true) {
                                        incomeDatabaseHelper.deleteAllRecords();
                                    }
                                    if (Checked[1] == true) {
                                        expenseDatabaseHelper.deleteAllData();
                                    }
                                    if (Checked[2] == true) {
                                        borrowDatabaseHelper.deleteAllBorrowData();
                                    }
                                    if(Checked[3]==true){
                                        lendDatabaseHelper.deleteAllLendData();
                                }
                                    getSupportFragmentManager().beginTransaction()
                                            .replace( R.id.fragment_container, new HomeFragment())
                                            .addToBackStack( null )
                                            .commit();
                                    toolbar.setTitle( "Income Expense Tracker" );

                                    Toast.makeText( MainActivity.this, "Selected Items records are deleted!!!", Toast.LENGTH_SHORT ).show();
                            }
                            }
                        } );

                        builderReset.setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for (int j = 0; j < charSequence.length; j++)
                                    Checked[j] = false;

                                builderReset.setCancelable( true );
                            }
                        } );

                        AlertDialog alertDialogReset = builderReset.create();
                        alertDialogReset.show();
                        return true;

                    default:
                        for (int j = 0; j < charSequence.length; j++)
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

            case R.id.nav_signup:
                if (username != null) {
                    final AlertDialog.Builder signup_builder = new AlertDialog.Builder( MainActivity.this );
                    signup_builder.setMessage( "Sorry, You are already registered and logged in." );
                    signup_builder.setTitle( "Alert!" );
                    signup_builder.setIcon( R.drawable.signup_alert );
                    signup_builder.setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            signup_builder.setCancelable( true );
                        }
                    } );

                    AlertDialog alertDialog = signup_builder.create();
                    alertDialog.show();
                    break;
                } else {
                    getSupportFragmentManager().beginTransaction()
                            .replace( R.id.fragment_container, new SignupFragment() )
                            .addToBackStack( null )
                            .commit();
                    toolbar.setTitle( "Signup" );

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
                            new ExpenseReportFragment())
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
                    Intent addBorrow = new Intent( MainActivity.this, AddBorrowActivity.class );
                    startActivity( addBorrow );
                    break;
                }else
                {
                    Toast.makeText( MainActivity.this, "Please First login into your account!!!",Toast.LENGTH_SHORT ).show();
                    break;
                }


            case R.id.nav_tobe_taken:
                if(username!=null){
                   Intent addLend = new Intent( MainActivity.this, AddLendActivity.class );
                   startActivity( addLend );
                    break;
                }else
                {
                    Toast.makeText( MainActivity.this, "Please First login into your account!!!",Toast.LENGTH_SHORT ).show();
                    break;
                }

            case R.id.nav_borrow_report:
                if(username!=null){
                    getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                            new BorrowReportFragment())
                            .addToBackStack( null )
                            .commit();
                    toolbar.setTitle( "Borrow Report " );
                    break;
                }else
                {
                    Toast.makeText( MainActivity.this, "Please First login into your account!!!",Toast.LENGTH_SHORT ).show();
                    break;
                }

            case R.id.nav_lend_report:
                if(username!=null){
                    getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                            new LendReportFragment())
                            .addToBackStack( null )
                            .commit();
                    toolbar.setTitle( "Lend Report " );
                    break;
                }else
                {
                    Toast.makeText( MainActivity.this, "Please First login into your account!!!",Toast.LENGTH_SHORT ).show();
                    break;
                }

            case R.id.nav_about:
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation( this );
                Intent about = new Intent( MainActivity.this, AboutActivity.class );
                startActivity( about, options.toBundle() );
                break;

            case  R.id.nav_about_app:
                Intent about_app = new Intent( MainActivity.this, AboutAppActivity.class );
                startActivity( about_app );
                break;


        }
        drawerLayout.closeDrawer( GravityCompat.START );
        return true;
    }
}