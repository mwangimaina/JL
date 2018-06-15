package helpers;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import appdata.Survey;

import static android.content.Context.MODE_PRIVATE;

public class DBHelper {

    public static final String IMAGE_ID = "id";
    public static final String IMAGE = "image";
    private final Context mContext;

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "survey4.db";
    private static final int DATABASE_VERSION = 1;


    private static final String CREATE_SURVEY_TABLE = "CREATE TABLE IF NOT EXISTS  SurveyTable (" +
            "_id INTEGER PRIMARY KEY ," +
            "event_date varchar," +
            "start_time varchar," +
            "stop_time varchar," +
            "s_county text," +
            "sub_county text," +
            "s_village text," +
            "area varchar," +
            "s_activity varchar," +
            "s_output varchar," +
            "s_cost int," +
            "facility_name varchar," +
            "officer_name text," +
            "facility_type varchar," +
            "image1 blob," +
            "image2 blob, lat varchar, lon varchar, flag varchar); ";


    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_SURVEY_TABLE);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS ImagesTable");
            onCreate(db);
        }
    }

    public void Reset() {
        mDbHelper.onUpgrade(this.mDb, 1, 1);
    }

    public DBHelper(Context ctx) {
        mContext = ctx;
        mDbHelper = new DatabaseHelper(mContext);
    }

    public DBHelper open() throws SQLException {
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    // Insert the image to the Sqlite DB

    public void addSurvey(Survey survey) {
        SharedPreferences pref = mContext.getSharedPreferences("mypref", MODE_PRIVATE);
        open();
        ContentValues cv = new ContentValues();
        cv.put("_id", survey.getEvent_Id());
        cv.put("event_date", survey.getEvent_date());
        cv.put("start_time", survey.getStart_time());
        cv.put("stop_time", survey.getStop_time());
        cv.put("s_county", survey.getS_county());
        cv.put("sub_county", survey.getSub_county());
        cv.put("s_village", survey.getS_village());
        cv.put("area", survey.getArea());
        cv.put("s_activity", survey.getS_activity());
        cv.put("s_output", survey.getS_output());
        cv.put("s_cost", survey.getS_cost());
        cv.put("facility_name", survey.getFacility_name());
        cv.put("officer_name", survey.getOfficer_name());
        cv.put("facility_type", survey.getFacility_type());
        cv.put("image1", survey.getImageBytes1());
        cv.put("image2", survey.getImageBytes2());
        cv.put("lat", pref.getString("lat",""));
        cv.put("lon", pref.getString("lon",""));
        cv.put("flag", "false"); //record is not final


        try {
            long rowInserted = mDb.insert("SurveyTable", null, cv);
            if(rowInserted != -1)
                Toast.makeText(mContext, "New row added, Event Id: " + rowInserted, Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        catch (Exception w){
            Toast.makeText(mContext, "Something went wrong. Check Event ID", Toast.LENGTH_SHORT).show();
        }

    }



    public Cursor getList(){
        mDb = mDbHelper.getReadableDatabase();
// Query for items from the database and get a cursor back
        Cursor surveyCursor = mDb.rawQuery("SELECT  * FROM SurveyTable", null);
        return surveyCursor;
    }


    public Cursor getSingleList(String event_id){
        mDb = mDbHelper.getReadableDatabase();
// Query for items from the database and get a cursor back
        Cursor singleCursor = mDb.rawQuery("SELECT  * FROM SurveyTable WHERE _id = '"+event_id+"'", null);
        return singleCursor;
    }

    // Get the image from SQLite DB
    // We will just get the last image we just saved for convenience...
    public byte[] retreiveImageFromDB() {
        Cursor cur = mDb.query(true, "SurveyTable", new String[]{IMAGE,},
                               null, null, null, null,
                               IMAGE_ID + " DESC", "1");
        if (cur.moveToFirst()) {
            byte[] blob = cur.getBlob(cur.getColumnIndex(IMAGE));
            cur.close();
            return blob;
        }
        cur.close();
        return null;
    }



    // Insert the image to the Sqlite DB

    public void updateSurvey(Survey survey) {
        SharedPreferences pref = mContext.getSharedPreferences("mypref", MODE_PRIVATE);
        open();
        ContentValues cv = new ContentValues();
        cv.put("_id", survey.getEvent_Id());
        cv.put("event_date", survey.getEvent_date());
        cv.put("start_time", survey.getStart_time());
        cv.put("stop_time", survey.getStop_time());
        cv.put("s_county", survey.getS_county());
        cv.put("sub_county", survey.getSub_county());
        cv.put("s_village", survey.getS_village());
        cv.put("area", survey.getArea());
        cv.put("s_activity", survey.getS_activity());
        cv.put("s_output", survey.getS_output());
        cv.put("s_cost", survey.getS_cost());
        cv.put("facility_name", survey.getFacility_name());
        cv.put("officer_name", survey.getOfficer_name());
        cv.put("facility_type", survey.getFacility_type());
        cv.put("image1", survey.getImageBytes1());
        cv.put("image2", survey.getImageBytes2());
        cv.put("lat", pref.getString("lat",""));
        cv.put("lon", pref.getString("lon",""));

        try {
            int i = mDb.update("SurveyTable", //table
                    cv, // column/value
                    "_id"+" = ?", // selections
                    new String[] { String.valueOf(survey.getEvent_Id()) }); //selection args


            if(i != -1)
                Toast.makeText(mContext, i+" Record "+survey.getEvent_Id()+" has been updated!: ", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        catch (Exception w){
            Toast.makeText(mContext, "Something went wrong. Check Event ID", Toast.LENGTH_SHORT).show();
        }

    }







}//end helper