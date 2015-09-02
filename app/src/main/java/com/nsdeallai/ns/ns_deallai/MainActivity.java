package com.nsdeallai.ns.ns_deallai;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
    * Method : Button Click
    * Parameter : View
    * Result Type : Layout
    * Result : Layout
    *   main_login_bu -> login layout
    *   main_mypage_bu -> mypage layout
    *   main_chat_bu -> chat room layout
    *
    * Explain
    * XML 에서 android:onClick 으로 동작
    * Parameter 값으로 View 받아 v.getId()를 이용하여 버튼 아이디 찾음
    * intent 를 사용하여 화면을 출력
    */
    public void mainOnClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.main_login_bu:
                // 추후 수정 요망
                intent = new Intent(this, User_LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.main_mypage_bu:
                intent = new Intent(this, User_MyPageActivity.class);
                startActivity(intent);
                break;
            case R.id.main_chat_bu:
                intent = new Intent(this, User_ChatActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
