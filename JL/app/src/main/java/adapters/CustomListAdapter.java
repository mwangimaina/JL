package adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import appdata.Form1Collection;
import appdata.SiteVisitUpdateForm;
import survey.data.com.jl.R;

public class CustomListAdapter extends CursorAdapter {
    public CustomListAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.customlist, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tvid = view.findViewById(R.id.id);

        TextView tvsubcounty =  view.findViewById(R.id.sucounty);

        TextView tvsynced =  view.findViewById(R.id.txtsynced);
        // Extract properties from cursor

        Button btnedit = (Button) view.findViewById(R.id.btnedit);

        final String event_id = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
        String priority = cursor.getString(cursor.getColumnIndexOrThrow("s_county"));
        String syncstatus = cursor.getString(cursor.getColumnIndexOrThrow("flag"));
        //int priority = cursor.getInt(cursor.getColumnIndexOrThrow("s_county"));

        // Populate fields with extracted properties
        tvid.setText("Event ID: "+event_id);
        tvsubcounty.setText(priority);
        //check if record is synced or not
        if (syncstatus=="true"){
            tvsynced.setText("Synced");
        }
        else {
            tvsynced.setText("Not Synced");
        }

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent x  =   new Intent(context, SiteVisitUpdateForm.class);
              //we bundle the even id
                Bundle b = new Bundle();
                b.putString("event_id", event_id);
                x.putExtras(b);
                context.startActivity(x);
            }
        });


    }
}








