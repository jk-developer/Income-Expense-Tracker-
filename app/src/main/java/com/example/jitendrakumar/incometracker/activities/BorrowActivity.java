package com.example.jitendrakumar.incometracker.activities;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.adapters.MyBorrowAdapter;
import com.example.jitendrakumar.incometracker.database.BorrowDatabaseHelper;
import com.example.jitendrakumar.incometracker.models.BorrowData;

import java.util.ArrayList;

public class BorrowActivity extends AppCompatActivity {

    RecyclerView rvBorrowData;
    BorrowDatabaseHelper borrowDatabaseHelper;
    ArrayList<BorrowData> arrayList = new ArrayList<>( );
    BorrowData bData;
    MyBorrowAdapter myBorrowAdapter;
    public static final String TAG = "date";
    private float totalBorrow = (float) 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature( Window.FEATURE_CONTENT_TRANSITIONS );
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_borrow );

        getSupportActionBar().setTitle( "Borrow List" );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        initAnimation();

        borrowDatabaseHelper = new BorrowDatabaseHelper( BorrowActivity.this );
        rvBorrowData= (RecyclerView) findViewById( R.id.rvBorrowData );

        ArrayList<BorrowData> myborrowlist = new ArrayList<>();
        myborrowlist = getArrayList();

        myBorrowAdapter = new MyBorrowAdapter( myborrowlist, this );

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager( getApplicationContext() );
        rvBorrowData.setLayoutManager( mLayoutManager );
        rvBorrowData.setItemAnimator( new DefaultItemAnimator() );
        rvBorrowData.setHasFixedSize( true );
        rvBorrowData.setAdapter( myBorrowAdapter );
    }

    public ArrayList<BorrowData> getArrayList(){
        Cursor res = borrowDatabaseHelper.getAllPayingData();
        if(res.getCount() == 0)
        {
            Toast.makeText( BorrowActivity.this, "No Borrow record is found!!!", Toast.LENGTH_SHORT ).show();
            return null;
        }
        else
        {
            while (res.moveToNext()){
                int bId = res.getInt( 0 );
                String bPerson =  res.getString( 1 );
                float bAmount =  res.getFloat( 2 );
                String bDesc = res.getString( 3 );
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

                bData = new BorrowData(bId, bAmount, bDesc, bPerson, Date);
                arrayList.add( bData);
                totalBorrow = totalBorrow +bAmount;
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
