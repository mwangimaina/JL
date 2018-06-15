package survey.data.com.jl;


import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import adapters.CustomListAdapter;
import helpers.DBHelper;

public class AllSurveyList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_survey_list);

        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#006582")));
        // Find ListView to populate
        ListView lvItems =  findViewById(R.id.surveylist);

        //get list from DBHELPER
        DBHelper helper = new DBHelper(getApplicationContext());
        // Access your CustomListadapter, use the it to access getList() methodoa

        Cursor listCursor = helper.getList();
        if (listCursor.getCount() < 1) {
            Toast.makeText(this, "No Surveys Found", Toast.LENGTH_SHORT).show();
        } else {

            CustomListAdapter listAdapter = new CustomListAdapter(this,listCursor );
// Attach cursor adapter to the ListView
            lvItems.setAdapter(listAdapter);

           // This will then trigger the CursorAdapter iterating through the result set and populating the list. We can change the cursor to update the adapter at any time with
            listAdapter.changeCursor(listCursor);
        }
    }//end if
}
