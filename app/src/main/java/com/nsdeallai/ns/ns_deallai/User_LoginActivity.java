package com.nsdeallai.ns.ns_deallai;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
    DbUser dbuser;
    SQLiteDatabase db;

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

        dbuser = new DbUser(this);

        mSocket.connect().emit("start", "Login Go!");

    }

    /*
    * Method : Button Click
    * Parameter : View
    * Result Type : Layout
    * Result : Layout
    *    login_login_bu  -> 메소드 이동
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
    * Thead 사용
    * 사용후 mSocket.off("loginCheck") 통하여 리스너 삭제 final 중복값 처리
    */
    public void loginCheck(View v) {
        mSocket.emit("Start", "Login Check GO!");

        final EditText editTextId = (EditText) findViewById(R.id.user_login_id);
        String idLayout = editTextId.getText().toString();

        mSocket.emit("login", idLayout).on("loginCheck", new Emitter.Listener() {
            String pwdLayout = "";

            String id = "";
            int cartId = 0;
            String pwd = "";
            String name = "";
            String address = "";
            String email = "";
            int tel = 0;
            String img = "";
            String isSeller = "";
            String dropout = "";

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
                            cartId = data.getInt("c_id");
                            pwd = data.getString("u_pwd");
                            name = data.getString("u_name");
                            address = data.getString("u_address");
                            email = data.getString("u_email");
                            tel = data.getInt("u_tel");
                            img = data.getString("u_img");
                            isSeller = data.getString("u_isseller");
                            dropout = data.getString("u_dropout");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (pwdLayout.equals(pwd)) {
                            finish();
                            insertDb(data);
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
    }
    /**
     * @Method 로그인 후 Sqlite 저장
     * @param Json
     *
     * @discription
     * DB에서 날라온 json 을 로그인후 sqlite에 저장
     **/
    private void insertDb(JSONObject data) {
        db = dbuser.getWritableDatabase();
        String sql = "INSERT INTO user (u_id, c_id, u_pwd, u_name, u_address, u_email, u_tel, u_img, u_isseller, u_dropout) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?)";
        db.execSQL(sql, new Object[]{data});
    }
}
