package helpers;

import android.content.Context;

public class CheckEmpty {


    Context context;
    public CheckEmpty(Context c){
        this.context = c;

    }

    public boolean Empty(String item){

        if(item.length()<1){
            return true;
        }

        else
        {
            return false;
        }
    }

}
