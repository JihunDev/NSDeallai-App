package com.nsdeallai.ns.ns_deallai;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kermit on 2015-09-04.
 */
public class DbUserHandler {
    private DbUserHelper dbUserHelper;
    private SQLiteDatabase sqLiteDatabase;

    /**
     * sqlite Construct
     *
     * @param con 시스템 관리 정보 접근하기 위해 받음
     * @discription Construct 설명하지 않아도 되겠죠?
     */
    private DbUserHandler(Context con) {
        this.dbUserHelper = new DbUserHelper(con);
        this.sqLiteDatabase = dbUserHelper.getWritableDatabase();
    }

    /**
     * sqlite 핸들러 함수
     *
     * @param con 시스템 관리 정보 접근하기 위해 받음
     * @return handler : 핸들러 함수를 초기화후 리턴
     * @discription 외부에서 핸들러 사용을 위해 초기화밑
     */
    public static DbUserHandler open(Context con) {
        DbUserHandler handler = new DbUserHandler(con);

        return handler;
    }

    /**
     * sqlite 끄기
     *
     * @discription 구지 설명안해도 알거라 생각함
     */
    public void close() {
        dbUserHelper.close();
    }

    /**
     * sqlite 테이블 초기화
     *
     * @discription onUpgrade를 사용하여 테이블을 지웠다 다시생성
     */
    public void upgrade() {
        sqLiteDatabase = dbUserHelper.getWritableDatabase();
        dbUserHelper.onUpgrade(sqLiteDatabase, 1, 2);
    }


    /**
     * Sqlite 유저 정보 Insert
     *
     * @param data : JSON type 유저 정보
     * @discription DB에서 날라온 json 을 로그인후 sqlite에 저장
     * json데이터를 바로 insert시킬 수 없어 ContentValues 객체에 담아 insert
     */
    public void insertDb(JSONObject data) {
        sqLiteDatabase = dbUserHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        try {
            values.put("u_id", data.getString("u_id"));
            values.put("c_id", data.getString("c_id"));
            values.put("u_pwd", data.getString("u_pwd"));
            values.put("u_name", data.getString("u_name"));
            values.put("u_address", data.getString("u_address"));
            values.put("u_email", data.getString("u_email"));
            values.put("u_tel", data.getString("u_tel"));
            values.put("u_img", data.getString("u_img"));
            values.put("u_isseller", data.getString("u_isseller"));
            values.put("u_dropout", data.getString("u_dropout"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        sqLiteDatabase.insert("user", null, values);
    }

    /**
     * Sqlite 유저 정보 업데이트
     *
     * @param data : JSON type 유저 정보
     * @discription 진행중
     */
    public void updateDb(JSONObject data) {
        sqLiteDatabase = dbUserHelper.getWritableDatabase();

    }

    /**
     * Sqlite 유저 정보 Select
     *
     * @return cursor : user 테이블에 있는 user 정보
     * @discription sqlite에서 유저 정보를 다 빼와서 cursor객체에 담아 리턴
     */
    public Cursor selectDb() {
        sqLiteDatabase = dbUserHelper.getWritableDatabase();
        String sql = "SELECT * FROM user";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        return cursor;
    }
}
