package com.nsdeallai.ns.ns_deallai;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Kermit on 2015-08-30.
 */
public class User_MyPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_mypage);
        myPageTextList();

    }

    /**
     * 버튼 이벤트
     *
     * @param v : View 를 받아 안에 있는 id값 사용을 위해
     * @discription XML 에서 android:onClick 으로 동작
     * Parameter 값으로 View 받아 v.getId()를 이용하여 버튼 아이디 찾음
     * intent 를 사용하여 화면을 출력
     */
    public void myPageOnClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.mypage_modify_bu:
                intent = new Intent(this, User_ModifyActivity.class);
                startActivity(intent);
                break;
            case R.id.mypage_myhistory_bu:
                intent = new Intent(this, User_MyHistoryActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * myPage 정보 출력
     *
     * @discription dbUserHandler를 통하여 유저 정보 를 다가져와 Cursor 객체에 담음
     * cursor를 처음으로 이동
     * cursor에 getCount를 통해 값이 없으면 동작하지 않게 작성
     * cursor에 마지막으로 등록된값을 화면에 출력
     */
    public void myPageTextList() {
        DbUserHandler dbUserHandler = DbUserHandler.open(this);

        TextView idTv = (TextView) findViewById(R.id.mypage_id_tv);
        TextView nameTv = (TextView) findViewById(R.id.mypage_name_tv);
        TextView telTv = (TextView) findViewById(R.id.mypage_tel_tv);
        TextView emailTv = (TextView) findViewById(R.id.mypage_email_tv);
        TextView addressTv = (TextView) findViewById(R.id.mypage_address_tv);
        TextView isSellerTv = (TextView) findViewById(R.id.mypage_isseller_tv);

        Cursor cursor = dbUserHandler.selectDb();

        cursor.moveToFirst();

        if (cursor.getCount() != 0) {
            cursor.moveToLast();
            idTv.setText(String.valueOf(cursor.getString(1)));
            nameTv.setText(String.valueOf(cursor.getString(4)));
            addressTv.setText(String.valueOf(cursor.getString(5)));
            emailTv.setText(String.valueOf(cursor.getString(6)));
            telTv.setText(String.valueOf(cursor.getString(7)));
            isSellerTv.setText(String.valueOf(cursor.getString(9)));
        }
    }
}

