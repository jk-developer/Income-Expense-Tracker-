package com.example.jitendrakumar.incometracker.activities;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_borrow );

        getSupportActionBar().setTitle( "Borrow List" );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

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
            Toast.makeText( BorrowActivity.this, "Nothing Found in Databse!!!", Toast.LENGTH_SHORT ).show();
            return null;
        }
        else
        {
            while (res.moveToNext()){
                int bId = res.getInt( 0 );
                String bPerson =  res.getString( 1 );
                float bAmount =  res.getFloat( 2 );
                String bDesc = res.getString( 3 );
                String bDate = res.getString( 4 );
            //
                bData = new BorrowData(bId, bAmount, bDesc, bPerson, bDate);
                arrayList.add( bData);
                totalBorrow = totalBorrow +bAmount;
            }

        }
        return arrayList;
    }
}
