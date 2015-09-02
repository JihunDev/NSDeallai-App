package com.nsdeallai.ns.ns_deallai;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Kermit on 2015-09-02.
 */
public class User_LoginActivity_Test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);
    }

    /**
     * Method : Button Click
     * Parameter : View
     * Result Type : Layout
     * Result : Layout
     * login_login_bu -> main layout
     * login_register_bu -> sinup layout
     * <p/>
     * Explain
     * XML 에서 android:onClick 으로 동작
     * Parameter 값으로 View 받아 v.getId()를 이용하여 버튼 아이디 찾음
     * intent 를 사용하여 화면을 출력
     */
    public void loginOnClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.login_login_bu:
                Runnable run = new Runnable() {
                    @Override
                    public void run() {
                        HttpURLConnection conn = null;
                        OutputStream os = null;
                        InputStream is = null;
                        ByteArrayOutputStream baos = null;
                        URL url;
                        try {
                            url = new URL(Constants.SERVER_URL);
                            conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("Cache-Control", "no-cache");
                            conn.setRequestProperty("Content-Type", "application/json");
                            conn.setRequestProperty("Accept", "application/json");
                            conn.setDoOutput(true);
                            conn.setDoInput(true);

                            os = conn.getOutputStream();
                            //os.write(job.toString().getBytes());
                            os.flush();

                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } 
                    }
                };
                break;
            case R.id.login_register_bu:
                intent = new Intent(this, User_SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }
}
