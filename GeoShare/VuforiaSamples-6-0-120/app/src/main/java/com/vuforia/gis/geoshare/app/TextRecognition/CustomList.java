package com.vuforia.gis.geoshare.app.TextRecognition;

/**
 * Created by AJ on 11/23/16.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vuforia.gis.geoshare.R;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomList extends BaseAdapter {

    private Activity activity;
    private ArrayList <HashMap<String,String>> data ;
    HashMap map;
    private static LayoutInflater inflater=null;
    //public ImageLoader imageLoader;

    public CustomList(Activity a, ArrayList<HashMap<String,String>> al) {
        activity = a;
        data = al;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       // imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.row_list, null);

        TextView fr_name = (TextView)vi.findViewById(R.id.fr_name); // title
        TextView no_f = (TextView)vi.findViewById(R.id.no_f); // artist name
        //ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image

        //thumb_image.setImageResource(R.drawable.abc);
        HashMap<String,String> name  = new HashMap<String,String>();
        name = data.get(position);

        // Setting all values in listview
        fr_name.setText(name.get(AddFriend.FR_NAME));
        //no_f.setText(name.get(AddFriend.NO_F));
       /// Picasso.with(activity.getApplicationContext())
          ///      .load("/Users/AJ/Documents/GeoShare/GeoShare/app/src/main/res/drawable/add.png")
             ///   .into(thumb_image);
       // imageLoader.DisplayImage(, thumb_image);
        return vi;
    }
}