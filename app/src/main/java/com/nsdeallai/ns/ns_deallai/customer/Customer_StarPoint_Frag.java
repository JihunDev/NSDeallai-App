package com.nsdeallai.ns.ns_deallai.customer;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.github.nkzawa.socketio.client.Socket;
import com.nsdeallai.ns.ns_deallai.Server;
import com.nsdeallai.ns.ns_deallai.R;
import com.nsdeallai.ns.ns_deallai.db.handler.DbUserHandler;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by SangSang on 2015-08-12.
 * 상품 별점리뷰 관련 클래스
 */

public class Customer_StarPoint_Frag extends AppCompatActivity {

    private RatingBar ratingBar;
    private String value;
    private Button button;
    private Socket mSocket = Server.SererConnect();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_starpoint_layout);

        mSocket.connect().emit("start", "StarPoint Go!");

        SetupRatingBar();
        SetupButton();
    }

    /**
     * 별점 점수를 받아오는 함수
     * Method : RatingBar 클릭 시 동작
     * Result : 클릭한 RatingBar의 값을 받아옴.
     *
     * @description : RatingBar의 값이 바뀔 때마다 value에 값 저장.
     */
    public void SetupRatingBar() {

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                value = String.valueOf(rating);
            }
        });
    }

    /**
     * 리뷰 등록 버튼 함수
     * Method : 등록 Button Click시 동작
     * Result : insertStar함수 호출
     *
     * @description : 버튼 클릭 시 insertStar 함수로 이동,
     */
    public void SetupButton() {
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        button = (Button) findViewById(R.id.star_register);
        final EditText text = (EditText) findViewById(R.id.productReview);


        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                insertStar();
                //   String productreivew = text.getText().toString();
                //   Toast.makeText(getBaseContext(), "별점 : "+value+", 의견 : "+productreivew, Toast.LENGTH_SHORT).show();


            }
        });
    }

    /**
     * 리뷰 database table에 등록하는 함수
     * Method : 등록 버튼이 클릭 된 후 insertStar() 함수 호출되면 동작
     * Result : database test3.db에 PR_REVIEW table에 insert됨
     *
     * @description : 모든 정보를 jsonObject에 넣어서 같이 보냄.
     * 서버의 SangSangTest 안에 StarCRUD.js를 실행시키면
     * mSocket.emit('insert',jsonObject); 시 서버로 넘어가 마리아db에 저장된다.
     * manifest에 인터넷 사용하는 것을 잊지 말고 쓰도록하자!
     * <uses-permission android:name="android.permission.INTERNET"/>
     */
    private void insertStar() {

        DbUserHandler dbUserHandler = DbUserHandler.open(this);

        Cursor cursor = dbUserHandler.selectDb();
        cursor.moveToFirst();
        String u_id = null;

        if (cursor.getCount() != 0) {
            cursor.moveToLast();
            u_id = String.valueOf(cursor.getString(1));
        }

        int pr_id = 1;
        String o_id = "1_2015-08-28 16:53:24";
        int p_id = 1;

        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        final EditText product_review = (EditText) findViewById(R.id.productReview);
        Button button = (Button) findViewById(R.id.star_register);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pr_id", pr_id);
            jsonObject.put("o_id", o_id);
            jsonObject.put("p_id", p_id);
            jsonObject.put("u_id", u_id);
            jsonObject.put("pr_content", product_review.getText().toString());
            jsonObject.put("pr_star", value);

            mSocket.emit("insert", jsonObject);
            Toast.makeText(getBaseContext(), "별점 : " + value + ", 의견 : " + product_review.getText().toString(), Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    ;
}
