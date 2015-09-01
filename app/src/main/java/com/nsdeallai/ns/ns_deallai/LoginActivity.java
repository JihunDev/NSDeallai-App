package com.nsdeallai.ns.ns_deallai;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Kermit on 2015-08-30.
 */
public class LoginActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);
    }
    /*
    * Method : Button Click
    * Parameter : View
    * Result Type : Layout
    * Result : Layout
    *    login_login_bu -> main layout
    *    login_register_bu -> sinup layout
    *
    * Explain
    * XML 에서 android:onClick 으로 동작
    * Parameter 값으로 View 받아 v.getId()를 이용하여 버튼 아이디 찾음
    * intent 를 사용하여 화면을 출력
    */
    public void loginOnClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.login_login_bu:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.login_register_bu:
                intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }
}
