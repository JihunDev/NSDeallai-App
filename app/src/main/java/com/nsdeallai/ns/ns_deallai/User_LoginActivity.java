package com.nsdeallai.ns.ns_deallai;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

/**
 * Created by Kermit on 2015-08-30.
 */
public class User_LoginActivity extends AppCompatActivity {

    private Socket mSocket;

    {
        try {
            mSocket = IO.socket(Constants.SERVER_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);

        mSocket.connect().emit("start", "Login Go!");
    }
    /***/
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
                loginCheck(v);
                break;
            case R.id.login_register_bu:
                intent = new Intent(this, User_SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }

    /*
    * Method : Button Click
    * Parameter : View
    * Result Type : Layout
    * Result : Layout
    *
    * Explain
    * Socket.io와 통신부
    * Emitter.Listener() 을 통해 emit 날리고 바로 받아 처리
    * Thead, Headler, Looper 사용
    */
    public void loginCheck(View v) {
        mSocket.emit("Start", "Login Check GO!");

        final EditText editTextId = (EditText) findViewById(R.id.user_login_id);
        String idLayout = editTextId.getText().toString();

        mSocket.emit("login", idLayout).on("loginCheck", new Emitter.Listener() {
            String id = "";
            String pwd = "";
            String pwdLayout = "";

            @Override
            public void call(final Object... args) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        EditText editTextPassWord = (EditText) findViewById(R.id.user_login_password);
                        pwdLayout = editTextPassWord.getText().toString();

                        JSONObject data = (JSONObject) args[0];
                        try {
                            id = data.getString("u_id");
                            pwd = data.getString("u_pwd");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Looper.prepare();
                        if (pwdLayout.equals(pwd)) {
                            mHandler.sendMessage(Message.obtain(mHandler, 1));
                        }
                        if (!pwdLayout.equals(pwd)) {
                            mHandler.sendMessage(Message.obtain(mHandler, 2));
                        }
                        if (id.equals("fail")) {
                            mHandler.sendMessage(Message.obtain(mHandler, 3));
                        }
                        if (!pwdLayout.equals(pwd) || id.equals("fail")) {
                            mHandler.sendMessage(Message.obtain(mHandler, 4));
                        }
                        Looper.loop();
                    }
                }).start();
            }
        });
    }

    /*
    * Method : Button Click
    * Parameter : View
    * Result Type : Layout
    * Result : Layout
    *
    * Explain
    * Socket.io와 통신부
    * Emitter.Listener() 을 통해 emit 날리고 바로 받아 처리
    * Thead, Headler, Looper 사용
    */
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    finish();
                    break;
                case 2:
                    Toast.makeText(getApplicationContext(), "pwd아이디와 비밀번호를 확인하세요.", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(getApplicationContext(), "id아이디와 비밀번호를 확인하세요.", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(getApplicationContext(), "idpwd아이디와 비밀번호를 확인하세요.", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}
