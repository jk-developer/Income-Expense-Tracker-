package com.example.jitendrakumar.incometracker.activities;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Window;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.adapters.MyLendAdapter;
import com.example.jitendrakumar.incometracker.database.LendDatabaseHelper;
import com.example.jitendrakumar.incometracker.models.LendData;

import java.util.ArrayList;

public class LendActivity extends AppCompatActivity {

    RecyclerView rvLendData;
    LendDatabaseHelper lendDatabaseHelper;
    ArrayList<LendData> arrayList = new ArrayList<>( );
    LendData lData;
    MyLendAdapter myLendAdapter;
    public static final String TAG = "date";
    private float totalLend = (float) 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature( Window.FEATURE_CONTENT_TRANSITIONS );
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_lend );

            getSupportActionBar().setTitle( "Lend List" );
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        initAnimation();

            lendDatabaseHelper = new LendDatabaseHelper( LendActivity.this );
            rvLendData= (RecyclerView) findViewById( R.id.rvLendData );

            ArrayList<LendData> mylendlist = new ArrayList<>();
            mylendlist = getArrayList();

            myLendAdapter = new MyLendAdapter( mylendlist, this );

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager( getApplicationContext() );
            rvLendData.setLayoutManager( mLayoutManager );
            rvLendData.setItemAnimator( new DefaultItemAnimator() );
            rvLendData.setHasFixedSize( true );
            rvLendData.setAdapter( myLendAdapter );
        }

        public ArrayList<LendData> getArrayList(){
            Cursor res = lendDatabaseHelper.getAllTakenData();
            if(res.getCount() == 0)
            {
                Toast.makeText( LendActivity.this, "No Lend record is found!!!", Toast.LENGTH_SHORT ).show();
                return null;
            }
            else
            {
                while (res.moveToNext()){
                    int lId = res.getInt( 0 );
                    String lPerson =  res.getString( 1 );
                    float lAmount =  res.getFloat( 2 );
                    String lDesc = res.getString( 3 );
                    int byear = res.getInt( 4 );
                    int bmonth = res.getInt( 5 );
                    int bday = res.getInt( 6 );

                    String Date = "";
                    if(bmonth<=9 && bday<=9)
                    {
                        Date = "0"+bday +"/0"+bmonth +"/"+byear ;
                    }
                    if(bmonth<=9 && bday>9){
                        Date = bday +"/0"+bmonth +"/"+byear ;
                    }
                    if(bmonth>9 && bday<=9){
                        Date = "0"+bday +"/"+bmonth +"/"+byear ;
                    }
                    else {
                        Date = bday +"/"+bmonth +"/"+byear ;
                    }

                    //
                    lData = new LendData(lId, lAmount, lDesc, lPerson, Date);
                    arrayList.add( lData);
                    totalLend = totalLend +lAmount;
                }

            }
            return arrayList;
        }

    @Override
    public boolean onSupportNavigateUp() {
        finishAfterTransition();
        return true;    }

    public void initAnimation(){
        Slide enterTransition = new Slide( );
        enterTransition.setSlideEdge( Gravity.BOTTOM);
        enterTransition.setInterpolator( new AnticipateOvershootInterpolator(  ));
        enterTransition.setDuration( 1000 );
        getWindow().setEnterTransition( enterTransition );

    }

}

