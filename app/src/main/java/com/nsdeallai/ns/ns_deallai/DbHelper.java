package com.nsdeallai.ns.ns_deallai;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SangSang on 2015-08-21.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "customer.db";

    private static final int DATABASE_VERSION = 2;

    // Contacts table name
    private static final String TABLE_CART = "cart";

    // Contacts Table Columns names
    private static final String KEY_ID = "_id";
    private static final String KEY_P_ID = "p_id";
    private static final String KEY_M_ID = "m_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_PRICE = "price";
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_DELIVERY = "delivery";
    private static final String KEY_OPTIONS = "options";

    // 생성자
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // 최초 DB 만들 때 한번만 호출된다.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_CART +" ( "+ KEY_ID +" INTEGER PRIMARY KEY" +
                " AUTOINCREMENT, "+KEY_P_ID+" NUMBER, "+KEY_M_ID+" NUMBER, "+KEY_NAME+" VARCHAR(200),"+KEY_IMAGE
                +" VARCHAR(200),"+KEY_PRICE+" NUMBER(6), " + KEY_QUANTITY+
                " NUMBER, "+KEY_DELIVERY+" NUMBER, "+KEY_OPTIONS+" VARCHAR(200));");
    }

    // 버전이 업데이트 되었을 경우 DB를 다시 만들어 준다.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_CART);
        onCreate(db);
    }

    /**
     *  CART CRUD 함수
     */

    // 새로운 contact 추가
     /*
    * Method : addCart() 함수 호출 시 동작
    * Parameter : Cart (constructor)
    * Result Type : 없음(void)
    * Result : sqlite의 database customer.db의 cart table에 insert 됨
    * Explain
    * DBHelper를 가져와서 addCart를 함수로 호출하면 동작
    * 안의 값은 Cart 객체로 넘겨주어야만 동작함
    * 안드로이드 내부에 db를 만들고 그 안에 table을 만들어서 보관함.
    */
    public void addCart(Cart cart) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_P_ID, cart.getP_id());
        values.put(KEY_M_ID, cart.getM_id());
        values.put(KEY_NAME, cart.getName());
        values.put(KEY_IMAGE, cart.getImage());
        values.put(KEY_PRICE, cart.getPrice());
        values.put(KEY_QUANTITY, cart.getQuantity());
        values.put(KEY_DELIVERY, cart.getDelivery());
        values.put(KEY_OPTIONS, cart.getOptions());

        db.insert(TABLE_CART, null, values);
        db.close();
    }

    // 아이디에 해당하는 contact 가져오기
  /*
    * Method : getCart() 함수 호출 시 동작
    * Parameter : Cart (constructor)
    * Result Type : id
    * Result : sqlite의 database customer.db의 cart table에서 특정 row 가져옴.
    * Explain
    * DBHelper를 가져와서 getCart를 함수로 호출하면 동작
    * sqlite안의 것을 읽어오려면 getReadableDatabase 를 꼭 선언해야함.
    * 또한, 읽어올때는 cursor 필요!
    * 안의 값은 Cart 객체로 넘겨주어야만 동작함
    */
    public Cart getCart(int id) {
        //DB를 사용하기 위해 생성하거나 열어줌. DB를 읽어올 수 있는 권한 부여
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CART, new String[] { KEY_ID, KEY_P_ID , KEY_M_ID, KEY_NAME, KEY_IMAGE, KEY_PRICE
                        , KEY_QUANTITY, KEY_DELIVERY, KEY_OPTIONS }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Cart cart = new Cart(Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(2)),
                cursor.getString(3), cursor.getString(4), Integer.parseInt(cursor.getString(5)), Integer.parseInt(cursor.getString(6)),
                Integer.parseInt(cursor.getString(7)), cursor.getString(8));
        // return contact
        return cart;
    }

    // 모든 contact 리스트 가져오기
    /*
    * Method : getAllCart() 함수 호출 시 동작
    * Parameter : 없음
    * Result Type : List<Cart>
    * Result : sqlite의 database customer.db의 cart table에서 전체 데이터 가져옴.
    * Explain
    * DBHelper를 가져와서 getAllCart를 함수로 호출하면 동작
    * sqlite안의 것을 읽거나 쓸려면 getReadableDatabase나 getWritableDatabase를 꼭 선언해야함.
    * 또한, 읽어올때는 cursor 필요!
    * rawQuery는 평소 많이 보왔던 전체 쿼리로 Data를 다루는 메서드임.
   */
    public List<Cart> getAllCarts() {
        List<Cart> contactList = new ArrayList<Cart>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CART;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Cart cart = new Cart();
                cart.set_id(Integer.parseInt(cursor.getString(0)));
                cart.setP_id(Integer.parseInt(cursor.getString(1)));
                cart.setM_id(Integer.parseInt(cursor.getString(2)));
                cart.setName(cursor.getString(3));
                cart.setImage(cursor.getString(4));
                cart.setPrice(Integer.parseInt(cursor.getString(5)));
                cart.setQuantity(Integer.parseInt(cursor.getString(6)));
                cart.setDelivery(Integer.parseInt(cursor.getString(7)));
                cart.setOptions(cursor.getString(8));

                // Adding contact to list
                contactList.add(cart);
            } while (cursor.moveToNext());
        }
        // return contact list
        return contactList;
    }

    // 가져온 contact 숫자 가져오기
     /*
    * Method : getCartsCount() 함수 호출 시 동작
    * Parameter : 없음
    * Result Type : int
    * Result : sqlite의 database customer.db의 cart table에서 전체 데이터의 갯수 가져옴.
    * Explain
    * DBHelper를 가져와서 getCartsCount를 함수로 호출하면 동작
    * sqlite안의 것을 읽거나 쓸려면 getReadableDatabase나 getWritableDatabase를 꼭 선언해야함.
    * 또한, 읽어올때는 cursor 필요!
    * rawQuery는 평소 많이 보왔던 전체 쿼리로 Data를 다루는 메서드임.
   */
    public int getCartsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CART;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // contact 업데이트
     /*
    * Method : update() 함수 호출 시 동작
    * Parameter : Cart (constructor)
    * Result Type : int
    * Result : sqlite의 database customer.db의 cart table에서 특정 row를 업데이트 함.
    * Explain
    * DBHelper를 가져와서 updateCart를 함수로 호출하면 동작
    * sqlite안의 것을 읽거나 쓸려면 getReadableDatabase나 getWritableDatabase를 꼭 선언해야함.
    * 쓸 때는 Cart 객체로 넘겨주어야 함.
   */
    public int updateCart(Cart cart) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, cart.get_id());
        values.put(KEY_QUANTITY, cart.getQuantity());
        values.put(KEY_OPTIONS, cart.getOptions());

        // updating row
        return db.update(TABLE_CART, values, KEY_ID + " = ?",
                new String[] { String.valueOf(cart.get_id()) });
    }

    // contact 삭제하기
     /*
    * Method : deleteCart() 함수 호출 시 동작
    * Parameter : Cart (constructor)
    * Result Type : 없음
    * Result : sqlite의 database customer.db의 cart table에서 특정 row 지움.
    * Explain
    * DBHelper를 가져와서 deleteCart를 함수로 호출하면 동작
    * sqlite안의 것을 읽거나 쓸려면 getReadableDatabase나 getWritableDatabase를 꼭 선언해야함.
   */
    public void deleteCart(Cart cart) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, KEY_ID + " = ?",
                new String[] { String.valueOf(cart.get_id()) });
        db.close();
    }


}
