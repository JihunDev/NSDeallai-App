package com.nsdeallai.ns.ns_deallai;

import android.content.Intent;
import android.os.Bundle;
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

    /**
     * 버튼 이벤트
     *
     * @param v : View 를 받아 안에 있는 id값 사용을 위해
     * @discription XML 에서 android:onClick 으로 동작
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

    /**
     * 로그인 체크 이벤트
     *
     * @param v : View 를 받아 안에 있는 id값 사용을 위해
     * @discription Socket.io와 통신을 통하여 서버에서 유저정보를 가져옴
     * 가져온 정보를 sqlite에 다시 넣어주는데 메소드에 파라미터를 json 형태로 보냄
     * 로그인 정보가 틀리면 toast 창을 띄움
     * call 메소드의 odbbject 값이 final이라 값이 계속 남아있는데 .off로 .on을 실행후 종료하여 해결
     */
    public void loginCheck(View v) {
        mSocket.emit("Start", "Login Check GO!");

        final DbUserHandler dbUserHandler = DbUserHandler.open(this);
        final EditText editTextId = (EditText) findViewById(R.id.user_login_id);
        String idLayout = editTextId.getText().toString();

        mSocket.emit("login", idLayout).on("loginCheck", new Emitter.Listener() {
            String pwdLayout = "";
            String id = "";
            String pwd = "";

            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
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

                        if (pwdLayout.equals(pwd)) {
                            finish();
                            dbUserHandler.insertDb(data);
                            mSocket.off("loginCheck");
                        }
                        if (!pwdLayout.equals(pwd) || id.equals("fail") && pwd.equals("fail")) {
                            Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 확인하세요.", Toast.LENGTH_SHORT).show();
                            mSocket.off("loginCheck");
                        }
                    }
                });
            }
        });
        dbUserHandler.close();
    }

}
