package team.gis.geoshare;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class post_activity extends AppCompatActivity {

    Button button_post;
    Context context;
    EditText post;
    int user_id;
    double lat;
    double lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_post);
        get_intent_variables();
        addListenerOnButton();
    }

    public void get_intent_variables(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user_id = extras.getInt("user_id");
            lat = extras.getDouble("lat");
            lon = extras.getDouble("lon");
            System.out.println("user id in post activity is "+user_id);
            //The key argument here must match that used in the other activity
        }
    }


    public void addListenerOnButton() {
        button_post = (Button) findViewById(R.id.button_post);
        post = (EditText) findViewById(R.id.post_text);
        button_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Log.d("Location", "my location is " + lat + " "+lon);
                System.out.println(post.getText().toString());
                String temp = Uri.encode(post.getText().toString());
                String param = "post_status?user="+user_id+"&lat="+String.valueOf(lat)+"&lon="+String.valueOf(lon)+"&post="+temp;
                //param = Uri.encode(param);
                new send_request().execute(param);
                Toast.makeText(context, post.getText()+" is posted",Toast.LENGTH_SHORT).show();
                post_activity.super.onBackPressed();


            }

        });

    }
}