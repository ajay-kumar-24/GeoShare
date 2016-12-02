package com.vuforia.gis.geoshare.app.TextRecognition;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.vuforia.gis.geoshare.R;

public class post_view_full_page extends AppCompatActivity {

    public String post_sender;
    public String post;
    public ImageView image_1;
    public TextView text1;
    public TextView text2;

    public void get_values_from_intent(){
        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            post_sender = extras.getString("post_sender");
            post = extras.getString("post");
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view_full_page);
        get_values_from_intent();
        image_1 = (ImageView) findViewById(R.id.image);
        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        System.out.println("post sender is "+post_sender.trim());
        if(post_sender.trim().equals("Ajay")) {
            image_1.setImageResource(R.drawable.ajay);
            text1.setText("Ajay says");
            text1.setTypeface(null, Typeface.BOLD_ITALIC);
            text1.setTextSize(40);
            text1.setGravity(Gravity.CENTER);

            text2.setText(post);
            text2.setTextSize(20);
            text2.setGravity(Gravity.CENTER);


        }

        else if(post_sender.trim().equals("Indhu")){
            image_1.setImageResource(R.drawable.indhu);
            text1.setText("Indhu says");
            text1.setTypeface(null, Typeface.BOLD_ITALIC);
            text1.setGravity(Gravity.CENTER);
            text1.setTextSize(40);

            text2.setText(post);
            text2.setTextSize(20);
            text2.setGravity(Gravity.CENTER);




        }
        else {
            image_1.setImageResource(R.drawable.susanth);
            text1.setText("Susanth says");
            text1.setTypeface(null, Typeface.BOLD_ITALIC);
            text1.setGravity(Gravity.CENTER);
            text1.setTextSize(40);

            text2.setText(post);
            text2.setTextSize(20);
            text2.setGravity(Gravity.CENTER);




        }

    }
}
