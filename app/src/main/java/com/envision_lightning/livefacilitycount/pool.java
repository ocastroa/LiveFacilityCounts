package com.envision_lightning.livefacilitycount;

import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

public class pool extends AppCompatActivity {

    private static TextView poolTextView;
    private static TextView updateTextView;

    LiveFacilityCountsOperations mydb;
    Cursor retrieve;
    String extract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pool);

        ConnectivityManager cManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo info = cManager.getActiveNetworkInfo();
        if(info == null){
            Toast.makeText(this, "Internet Connection is Offline ", Toast.LENGTH_SHORT).show();
        }

        mydb = new LiveFacilityCountsOperations(this);

        String date = new SimpleDateFormat("MMM dd, yyyy").format(new Date());
        TextView dateTextView = (TextView) findViewById(R.id.dateTextView);
        dateTextView.setText(date);

        chooseData();

        choseUpdate();
    }

    public void chooseData(){
        retrieve = mydb.retrieveData(2);
        Log.d("Taglines", "Retrieve Successful");
        poolTextView = (TextView) findViewById(R.id.poolTextView);

        extract = retrieve.getString(2);
        Log.d("Taglines", "Get String Successful");

        if(extract.equals("CLOSED")){
            poolTextView.setText("CLOSED");
            }
        else {
            poolTextView.setText(extract + " /150");
        }
    }

    public void choseUpdate(){
        Cursor retrieve = mydb.retrieveData(2);
        Log.d("Taglines", "Retrieve Successful");
        updateTextView = (TextView) findViewById(R.id.updateTextView);

        extract = retrieve.getString(3);
        Log.d("Taglines", "Get String  Successful");

        updateTextView.setText(extract);

        retrieve.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
////            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = NavUtils.getParentActivityIntent(this);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                NavUtils.navigateUpTo(this, intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStop() {
        mydb.close();
        super.onStop();
    }
}
