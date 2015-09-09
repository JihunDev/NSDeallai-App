package com.nsdeallai.ns.ns_deallai;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

/**
 * Created by SangSang on 2015-09-09.
 */
public class Customer_StarPoint_update extends AppCompatActivity {

    TextView update_o_id, update_p_id, update_u_id;
    RatingBar update_ratingBar;
    EditText update_productReview;
    String o_id, u_id, star_review;
    int p_id;
    float star_ratingbar;
    Socket socket;

    {
        try {
            socket = IO.socket("http://211.253.11.138:3004");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_starpoint_update_layout);

        socket.connect().emit("start", "StarUpdate Go!");

        update_o_id = (TextView) findViewById(R.id.update_o_id);
        update_p_id = (TextView) findViewById(R.id.update_p_id);
        update_u_id = (TextView) findViewById(R.id.update_u_id);

        update_ratingBar = (RatingBar) findViewById(R.id.update_ratingBar);
        update_productReview = (EditText) findViewById(R.id.update_productReview);

        Intent intent = getIntent();

        if(intent!= null){
            o_id = intent.getStringExtra("o_id");
            p_id = intent.getIntExtra("p_id", 0);
            u_id = intent.getStringExtra("u_id");
            star_ratingbar = intent.getFloatExtra("star_ratingbar", 3.2f);
            star_review = intent.getStringExtra("star_review");
        }
        update_o_id.setText(o_id);
        update_p_id.setText(Integer.toString(p_id));
        update_u_id.setText(u_id);
        update_ratingBar.setRating(star_ratingbar);
        update_productReview.setText(star_review);
    }


    public void starupdate(View view){

        o_id = update_o_id.getText().toString();
        star_ratingbar = update_ratingBar.getRating();
        star_review = update_productReview.getText().toString();
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("pr_content",star_review);
            jsonObject.put("pr_star",star_ratingbar);
            jsonObject.put("o_id", o_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socket.emit("update",jsonObject);

        Intent intent = new Intent(getApplicationContext(), Seller_Product_detail_Test.class);
        startActivity(intent);
    }
}
