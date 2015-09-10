package com.nsdeallai.ns.ns_deallai.user;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.nsdeallai.ns.ns_deallai.Constants;
import com.nsdeallai.ns.ns_deallai.R;
import com.nsdeallai.ns.ns_deallai.db.handler.DbUserHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

/**
 * Created by Kermit on 2015-08-30.
 */
public class User_ModifyActivity extends AppCompatActivity {

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
        setContentView(R.layout.user_modify);

        modifyTextList();

        mSocket.connect().emit("start", "modify Go!");
    }

    /**
     * 버튼 이벤트
     *
     * @param v : View 를 받아 안에 있는 id값 사용을 위해
     * @discription XML 에서 android:onClick 으로 동작
     * Parameter 값으로 View 받아 v.getId()를 이용하여 버튼 아이디 찾음
     * intent 를 사용하여 화면을 출력
     */
    public void modifyOnClick(View v) {

        switch (v.getId()) {
            case R.id.modify_modify_bu:
                userModify();
                break;
            case R.id.modify_delete_bu:
                userDelete();
                break;
        }
    }

    /**
     * modify 정보 출력
     *
     * @discription dbUserHandler를 통하여 유저 정보 를 다가져와 Cursor 객체에 담음
     * cursor를 처음으로 이동
     * cursor에 getCount를 통해 값이 없으면 동작하지 않게 작성
     * cursor에 마지막으로 등록된값을 화면에 출력
     */
    private void modifyTextList() {
        DbUserHandler dbUserHandler = DbUserHandler.open(this);

        EditText idEt = (EditText) findViewById(R.id.modify_id_et);
        EditText nameEt = (EditText) findViewById(R.id.modify_name_et);
        EditText telEt = (EditText) findViewById(R.id.modify_tel_et);
        EditText eMailEt = (EditText) findViewById(R.id.modify_email_et);
        EditText addressEt = (EditText) findViewById(R.id.modify_address_et);

        Cursor cursor = dbUserHandler.selectDb();

        cursor.moveToFirst();

        if (cursor.getCount() != 0) {
            cursor.moveToLast();

            idEt.setText(String.valueOf(cursor.getString(1)));
            nameEt.setText(String.valueOf(cursor.getString(4)));
            addressEt.setText(String.valueOf(cursor.getString(5)));
            eMailEt.setText(String.valueOf(cursor.getString(6)));
            telEt.setText(String.valueOf(cursor.getString(7)));
        }
    }

    /**
     * 유저 정보 변경 이벤트
     *
     * @discription socket.io로 화면에 바뀐 값들을 json으로 전송
     * 전송후 dbUserHandler를 통하여 sqlite값도 변경
     */
    private void userModify() {

        final DbUserHandler dbUserHandler = DbUserHandler.open(this);

        EditText idEt = (EditText) findViewById(R.id.modify_id_et);
        EditText nameEt = (EditText) findViewById(R.id.modify_name_et);
        EditText telEt = (EditText) findViewById(R.id.modify_tel_et);
        EditText eMailEt = (EditText) findViewById(R.id.modify_email_et);
        EditText addressEt = (EditText) findViewById(R.id.modify_address_et);
        EditText pwdEt = (EditText) findViewById(R.id.modify_pwd_et);
        EditText pwdCheckEt = (EditText) findViewById(R.id.modify_pwd_check_et);

        String id = idEt.getText().toString();
        String name = nameEt.getText().toString();
        String tel = telEt.getText().toString();
        String eMail = eMailEt.getText().toString();
        String address = addressEt.getText().toString();
        String pwd = pwdEt.getText().toString();
        String pwdCheck = pwdCheckEt.getText().toString();

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("id", id);
            jsonObject.put("name", name);
            jsonObject.put("tel", tel);
            jsonObject.put("eMail", eMail);
            jsonObject.put("address", address);
            jsonObject.put("pwd", pwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (pwd.equals(pwdCheck) && !(pwd.equals("") && pwdCheck.equals(""))) {
            mSocket.emit("update", jsonObject);
            try {
                /* updateDb 메소드에서 throws 던짐*/
                dbUserHandler.updateDb(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(this, User_MyPageActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "비밀번호를 확인하세요.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 유저 삭제 이벤트
     *
     * @discription 유저의 id를 socket으로 전송하여 삭제
     * 삭제후 삭제 되었다는 페이지 intent로 띄움
     */
    private void userDelete() {
        EditText idEt = (EditText) findViewById(R.id.modify_id_et);
        String id = idEt.getText().toString();

        mSocket.emit("Start", "delete GO!");

        mSocket.emit("delete", id);

        Intent intent = new Intent(this, User_DeleteActivity.class);
        startActivity(intent);
    }
}
