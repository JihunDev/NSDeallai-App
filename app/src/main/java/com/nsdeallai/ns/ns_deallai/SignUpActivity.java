package com.nsdeallai.ns.ns_deallai;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

/**
 * Created by Kermit on 2015-08-30.
 */
public class SignUpActivity extends AppCompatActivity {

    private Socket mSocket;

    {
        try {
            mSocket = IO.socket(Constants.SERVER_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    final Thread success = new Thread() {
        public void run() {
            try {
                sleep(2000);
                finish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_singup);

        mSocket.connect().emit("start", "Sign Up Go!");

    }

    /*
   * Method : Button Click
   * Parameter : View
   * Result Type : JsonObject
   * Result : {"insert", JsonObject}
   *            key        value
   *
   * Explain
   * XML 에서 android:onClick 으로 동작
   * Parameter 값으로 View 받아왔지만 사용하지 않음
   * Id를 통하여 값 가져오며 String 변환
   * Socket.io를 통하여 보낼 값을 JsonObject에 담음
   * .emit를 통하여 isert라는 key값으로 JsonObject전송
   * 전송후 나오는 Layout을 thread를 사용하여 일정시간 후 삭제
   */
    public void insertOnClick(View v) {

        final EditText editTextId = (EditText) findViewById(R.id.singup_id);
        final EditText editTextPwd = (EditText) findViewById(R.id.singup_pwd);
        final EditText editTextPwdCheck = (EditText) findViewById(R.id.singup_pwd_check);
        final EditText editTextName = (EditText) findViewById(R.id.singup_name);
        final EditText editTextAddress = (EditText) findViewById(R.id.singup_address);
        final EditText editTextTel = (EditText) findViewById(R.id.singup_tel);
        final EditText editTextEmail = (EditText) findViewById(R.id.singup_email);

        String id = editTextId.getText().toString();
        String pwd = editTextId.getText().toString();
        String pwdCheck = editTextId.getText().toString();
        String name = editTextId.getText().toString();
        String address = editTextId.getText().toString();
        String tel = editTextId.getText().toString();
        String email = editTextId.getText().toString();

        if (id.equals("")) {
            Toast.makeText(getApplicationContext(), "ID를 입력하세요.", Toast.LENGTH_SHORT).show();
            editTextId.requestFocus();
            return;
        }
        if (pwd.equals("") || pwdCheck.equals("")) {
            Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
            if (pwd.equals("")) {
                editTextPwd.requestFocus();
            } else {
                editTextPwdCheck.requestFocus();
            }
            return;
        }
        if (name.equals("")) {
            Toast.makeText(getApplicationContext(), "이름을 입력하세요.", Toast.LENGTH_SHORT).show();
            editTextName.requestFocus();
            return;
        }
        if (address.equals("")) {
            Toast.makeText(getApplicationContext(), "주소를 입력하세요.", Toast.LENGTH_SHORT).show();
            editTextAddress.requestFocus();
            return;
        }
        if (tel.equals("")) {
            Toast.makeText(getApplicationContext(), "전화번호를 입력하세요.", Toast.LENGTH_SHORT).show();
            editTextTel.requestFocus();
            return;
        }
        if (email.equals("")) {
            Toast.makeText(getApplicationContext(), "이메일을 입력하세요.", Toast.LENGTH_SHORT).show();
            editTextEmail.requestFocus();
            return;
        }
        if (pwd.equals(pwdCheck)) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id", id);
                jsonObject.put("pwd", pwd);
                jsonObject.put("name", name);
                jsonObject.put("addr", address);
                jsonObject.put("email", email);
                jsonObject.put("tel", tel);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            mSocket.emit("insert", jsonObject);

            setContentView(R.layout.user_singup_success);
            success.start();
        } else {
            Toast.makeText(getApplicationContext(), "비밀번호가 맞지 않습니다.", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
