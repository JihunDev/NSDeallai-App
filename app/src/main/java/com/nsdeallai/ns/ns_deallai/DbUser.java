package com.nsdeallai.ns.ns_deallai;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kermit on 2015-09-03.
 */
public class DbUser extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "user3.db";
    private static final int DATABASE_VERSION = 2;

    public DbUser(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE user (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "u_id VARCHAR(20)," +
                "c_id NUMBER(11)," +
                "u_pwd VARCHAR(20)," +
                "u_name VARCHAR(20)," +
                "u_address VARCHAR(100)," +
                "u_email VARCHAR(50)," +
                "u_tel NUMBER(11)," +
                "u_img VARCHAR(500)," +
                "u_isseller CHAR(1)," +
                "u_dropout CHAR(1));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS user");
        onCreate(sqLiteDatabase);
    }
}
