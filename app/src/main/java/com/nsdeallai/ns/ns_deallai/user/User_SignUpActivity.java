package com.nsdeallai.ns.ns_deallai.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.nsdeallai.ns.ns_deallai.Constants;
import com.nsdeallai.ns.ns_deallai.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

/**
 * Created by Kermit on 2015-08-30.
 */
public class User_SignUpActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private String isSeller = "";
    private Socket mSocket;

    {
        try {
            mSocket = IO.socket(Constants.SERVER_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /*스레드를 이용하여 화면 finish로 날려버림*/
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

        RadioGroup group = (RadioGroup) findViewById(R.id.singup_saller_rg);
        group.setOnCheckedChangeListener(this);
    }

    /**
     * 회원가입 이벤트
     *
     * @param v : View 를 받아 안에 있는 id값 사용을 위해
     * @discription view에있는 R.id값을을 가져와 공백이면 toast 출력후 포커스를 줌
     * 입력 비밀번호 두개가 맞으면 Json 형태로 담아 sccket.io를 통해 서버로 전달
     * 후 회원가입 페이지를 띄우고 스레드 동작
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
        String pwd = editTextPwd.getText().toString();
        String pwdCheck = editTextPwdCheck.getText().toString();
        String name = editTextName.getText().toString();
        String address = editTextAddress.getText().toString();
        String tel = editTextTel.getText().toString();
        String email = editTextEmail.getText().toString();

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
                jsonObject.put("isseller", isSeller);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println(isSeller);
            mSocket.emit("insert", jsonObject);

            setContentView(R.layout.user_singup_success);
            success.start();
        } else {
            Toast.makeText(getApplicationContext(), "비밀번호가 맞지 않습니다.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int id) {
        if(id == R.id.singup_seller_cu_rb){
            isSeller = "n";
        }
        if(id == R.id.singup_seller_se_rb){
            isSeller = "y";
        }
    }
}
