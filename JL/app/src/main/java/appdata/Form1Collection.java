package appdata;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import helpers.CheckEmpty;
import helpers.DBHelper;
import helpers.Subcounties;
import survey.data.com.jl.MainActivity;
import survey.data.com.jl.R;
import utils.Utils;


public class Form1Collection extends AppCompatActivity implements
        View.OnClickListener{
    private static final int SELECT_PICTURE = 100;
    private static final int CAMERA_PHOTO = 111;
    Button btnDatePicker, btnTimePicker, btnTimeStop;
    EditText txtDate, txtTime, txtStop;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Spinner spinarea, spinfacilty;
    String [] areas = {"HIV","AYSRH","TB"};
    String [] facilities = {"HOME","SCHOOL","HEALTH"};
    String [] counties_items = {"Kisumu","Nairobi","Nakuru","Meru","Transzoia","Narok","Kajiado","Embu","Mombasa","Nyeri","Kitui","Machakos","Kibwezi","Taita Taveta"};
    String [] subcountiesitems= {};
    ImageView image_view,image_view1;
    ArrayAdapter<String> aasubcounty;
    Spinner county;
    //declare an int to diiferentiate 2 images  uploads
    int uploadbtn=0;
    int updatecamera=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form1_collection);
        ActionBar bar = getSupportActionBar();
        //This form will be used to collect other data to SQLite or API
        //This is the next MOVE=============........

        //change title bar colr
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#006582")));
        //find views
        btnDatePicker=findViewById(R.id.btn_date);
        btnTimePicker=findViewById(R.id.btn_time);
        btnTimeStop=findViewById(R.id.btn_stoptime);
        txtDate=findViewById(R.id.eventdate);
        txtTime=findViewById(R.id.start_time);
        txtStop=findViewById(R.id.stoptime);
        //find all edittexts
        final EditText eventid = findViewById(R.id.eventID);
        final EditText eventdate = findViewById(R.id.eventdate);
        final EditText starttime = findViewById(R.id.start_time);
        final EditText stoptime = findViewById(R.id.stoptime);
        county = findViewById(R.id.county);
        final Spinner subcounty = findViewById(R.id.subcounty);
        final EditText village = findViewById(R.id.village);
        spinarea= findViewById(R.id.area);
        final EditText activity = findViewById(R.id.activitytype);
        final EditText output = findViewById(R.id.output);
        final EditText cost = findViewById(R.id.cost);
        final EditText facilityname = findViewById(R.id.facilityname);
        spinfacilty=findViewById(R.id.facilitytype);
        final EditText officername = findViewById(R.id.officername);


        //county select list
        ArrayAdapter<String> aa = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,areas);
        spinarea.setAdapter(aa);


        //area select list
        ArrayAdapter<String> aacounty = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,counties_items);
        county.setAdapter(aacounty);

        county.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Subcounties sub = new Subcounties(getApplicationContext());
               subcountiesitems = sub.getSubCounty(county.getSelectedItem().toString());
                //subcounty select list
                aasubcounty = new ArrayAdapter<>(getApplicationContext(), R.layout.design,subcountiesitems);
                subcounty.setAdapter(aasubcounty);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(Form1Collection.this, "No County selected", Toast.LENGTH_SHORT).show();
            }
        });

        //facility spinner - select list
        ArrayAdapter<String> aafacility = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,facilities);
        spinfacilty.setAdapter(aafacility);


        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        btnTimeStop.setOnClickListener(this);


        Button btn_img1 = findViewById(R.id.btn_img1);
        btn_img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Form1Collection.this);

                // Set a title for alert dialog
                builder.setTitle("Select A Choice");

                // Ask the final question
                builder.setMessage("Please select photo location");

                // Set the alert dialog yes button click listener
                builder.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when user clicked the Yes button
                        openImageChooser();
                        uploadbtn=1;

                    }
                });

                // Set the alert dialog no button click listener
                builder.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when No button clicked
                        captureCameraImage();
                        updatecamera=1;


                    }
                });

                AlertDialog dialog = builder.create();
                // Display the alert dialog on interface
                dialog.show();

                //here

            }
        });

        Button btn_img2 = findViewById(R.id.btn_img2);
        btn_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(Form1Collection.this);

                // Set a title for alert dialog
                builder.setTitle("Select your answer.");

                // Ask the final question
                builder.setMessage("Are you sure to hide?");

                // Set the alert dialog yes button click listener
                builder.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when user clicked the Yes button
                        openImageChooser();
                        uploadbtn=2;

                    }
                });

                // Set the alert dialog no button click listener
                builder.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when No button clicked
                        captureCameraImage();
                        updatecamera=2;


                    }
                });

                AlertDialog dialog = builder.create();
                // Display the alert dialog on interface
                dialog.show();

                //here

            }
        });

        Button savesurveylocal = findViewById(R.id.savesurveylocal);
        savesurveylocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String event_Id =  eventid.getText().toString();
               String event_date = eventdate.getText().toString();
               String start_time = starttime.getText().toString();
               String stop_time = stoptime.getText().toString();
               String s_county = county.getSelectedItem().toString();
               String sub_county = subcounty.getSelectedItem().toString();
               String s_village = village.getText().toString();
               String area = spinarea.getSelectedItem().toString();
               String s_activity = activity.getText().toString();
               String s_output = output.getText().toString();
               String s_cost = cost.getText().toString();
               String facility_name = facilityname.getText().toString();
               String officer_name = officername.getText().toString();
               String facility_type = spinfacilty.getSelectedItem().toString();

               CheckEmpty empty = new CheckEmpty(getApplicationContext());

               if (empty.Empty(event_Id)){
                     eventid.setError("Enter Even ID");

               }
               else if(empty.Empty(event_date)){
                   eventdate.setError("Pick A Date");
               }

               else if(empty.Empty(start_time)){
                   starttime.setError("Pick Start Time");
               }
               else if(empty.Empty(stop_time)){
                   stoptime.setError("Pick Stop Time");
               }

               else if(empty.Empty(s_county)){
                   Toast.makeText(Form1Collection.this, "Select County", Toast.LENGTH_SHORT).show();
               }

               else if(empty.Empty(sub_county)){
                   Toast.makeText(Form1Collection.this, "sub County not available", Toast.LENGTH_SHORT).show();
               }

               else if(empty.Empty(s_village)){
                   village.setError("Enter Village");
               }

               else if(empty.Empty(area)){
                   Toast.makeText(Form1Collection.this, "Select Area", Toast.LENGTH_SHORT).show();
               }

               else if(empty.Empty(s_activity)){
                   activity.setError("Enter Activity");
               }

               else if(empty.Empty(s_output)){
                   output.setError("Enter Output");
               }

               else if(empty.Empty(s_cost)){
                   cost.setError("Enter Cost");
               }

               else if(empty.Empty(facility_name)){
                   facilityname.setError("Facility Name");
               }

               else if(empty.Empty(facility_type)){
                   Toast.makeText(Form1Collection.this, "Select Facility Type", Toast.LENGTH_SHORT).show();
               }

               else if(empty.Empty(officer_name)){
                   officername.setError("Enter officer Name");
               }



               else {

                   image_view.setDrawingCacheEnabled(true);
                   image_view.buildDrawingCache();
                   Bitmap bitmap = Bitmap.createBitmap(image_view.getDrawingCache());
                   byte[] image1 = Utils.getImageBytes(bitmap);


                   image_view1.setDrawingCacheEnabled(true);
                   image_view1.buildDrawingCache();
                   Bitmap bitmap1 = Bitmap.createBitmap(image_view1.getDrawingCache());
                   byte[] image2 = Utils.getImageBytes(bitmap1);

                   DBHelper h = new DBHelper(getApplicationContext());

                   //SAVE DATA
                   h.addSurvey(new Survey(event_Id,event_date,start_time,stop_time,s_county,sub_county,s_village,area,s_activity,s_output,Integer.parseInt(s_cost),facility_name,officer_name,facility_type,image1,image2));

               }

            }
        });

    }




    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        if (v == btnTimeStop) {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtStop.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }//end pickers

    }


    // Choose an image from Gallery
    public void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {

                Uri selectedImageUri = data.getData();

                if (null != selectedImageUri) {
                    try {
                        image_view = findViewById(R.id.img);
                        image_view1 = findViewById(R.id.img1);

                        final InputStream imageStream = getContentResolver().openInputStream(selectedImageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        if (uploadbtn==1) {
                            image_view.setImageBitmap(selectedImage);
                        }
                        else {
                            image_view1.setImageBitmap(selectedImage);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(Form1Collection.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                }

            }

            if (requestCode == CAMERA_PHOTO && resultCode == Activity.RESULT_OK) {
                image_view = findViewById(R.id.img);
                image_view1 = findViewById(R.id.img1);
                if(imageToUploadUri != null){
                    Uri selectedImage = imageToUploadUri;
                    getContentResolver().notifyChange(selectedImage, null);
                    Bitmap reducedSizeBitmap = getBitmap(imageToUploadUri.getPath());
                    if(reducedSizeBitmap != null){
                        if (updatecamera==1) {
                            image_view.setImageBitmap(reducedSizeBitmap);
                        }
                        else {
                            image_view1.setImageBitmap(reducedSizeBitmap);
                        }


                    }else{
                        Toast.makeText(this,"Error while capturing Image",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(this,"Error while capturing Image",Toast.LENGTH_LONG).show();
                }
            }


        }
    }//end gallery capture


    //============camera capture

    private Uri imageToUploadUri;

    private void captureCameraImage() {
        Intent chooserIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = new File(Environment.getExternalStorageDirectory(), "POST_IMAGE.jpg");
        chooserIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        imageToUploadUri = Uri.fromFile(f);
        startActivityForResult(chooserIntent, CAMERA_PHOTO);
    }//end




    //Here is getBitmap() method used in onActivityResult(). I have done all performance improvement that can be possible while getting camera capture image bitmap.

    private Bitmap getBitmap(String path) {

        Uri uri = Uri.fromFile(new File(path));
        InputStream in = null;
        try {
            final int IMAGE_MAX_SIZE = 1200000; // 1.2MP
            in = getContentResolver().openInputStream(uri);

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, o);
            in.close();


            int scale = 1;
            while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) >
                    IMAGE_MAX_SIZE) {
                scale++;
            }
            //Log.d("", "scale = " + scale + ", orig-width: " + o.outWidth + ", orig-height: " + o.outHeight);

            Bitmap b = null;
            in = getContentResolver().openInputStream(uri);
            if (scale > 1) {
                scale--;
                // scale to max possible inSampleSize that still yields an image
                // larger than target
                o = new BitmapFactory.Options();
                o.inSampleSize = scale;
                b = BitmapFactory.decodeStream(in, null, o);

                // resize to desired dimensions
                int height = b.getHeight();
                int width = b.getWidth();
               // Log.d("", "1th scale operation dimenions - width: " + width + ", height: " + height);

                double y = Math.sqrt(IMAGE_MAX_SIZE
                        / (((double) width) / height));
                double x = (y / height) * width;

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, (int) x,
                        (int) y, true);
                b.recycle();
                b = scaledBitmap;

                System.gc();
            } else {
                b = BitmapFactory.decodeStream(in);
            }
            in.close();

           // Log.d("", "bitmap size - width: " + b.getWidth() + ", height: " +
                    b.getHeight();
            return b;
        } catch (IOException e) {
            //Log.e("", e.getMessage(), e);
            return null;
        }
    }







}//end class













//TO PROCEED WITH SAVING ----REVIEW THE INTERFACE FIRST
