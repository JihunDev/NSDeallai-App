package com.nsdeallai.ns.ns_deallai;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Kermit on 2015-08-30.
 */
public class ModifyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_modify);
    }

    /*
    * Method : Button Click
    * Parameter : View
    * Result Type : Layout
    * Result : Layout
    *    modify_modify_bu -> mypage layout
    *    modify_delete_bu -> delete layout
    *
    * Explain
    * XML 에서 android:onClick 으로 동작
    * Parameter 값으로 View 받아 v.getId()를 이용하여 버튼 아이디 찾음
    * intent 를 사용하여 화면을 출력
    */
    public void modifyOnClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.modify_modify_bu:
                intent = new Intent(this, MyPageActivity.class);
                startActivity(intent);
                break;
            case R.id.modify_delete_bu:
                intent = new Intent(this, DeleteActivity.class);
                startActivity(intent);
                break;
        }
    }
}
