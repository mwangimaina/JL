package helpers;

import android.content.Context;
import android.widget.Toast;

public class Subcounties {
    Context context;
    public Subcounties(Context c){
        this.context = c;

    }

    String [] nairobisub = {"Embakasi North","Kasarani","Dagoreti"};
    String [] kisumusub = {"Awasi","Pembe Tatu","Rongo"};

    public String[] getSubCounty(String county){

        if (county=="Nairobi")
        {
            return nairobisub;
        }
        else if(county=="Kisumu")
        {
            return kisumusub;
        }
        else {
            Toast.makeText(context, "Real Sub County Not Set", Toast.LENGTH_SHORT).show();
            return kisumusub;
        }

    }
}
