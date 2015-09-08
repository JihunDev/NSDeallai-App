package com.nsdeallai.ns.ns_deallai;

/**
 * Created by SangSang on 2015-08-26.
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.nsdeallai.ns.ns_deallai.db.helper.DbCartHelper;
import com.nsdeallai.ns.ns_deallai.entity.Cart;

public class Test extends Activity { //Cart CRUD 확인 CLASS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DbCartHelper db = new DbCartHelper(this);

        /**
         * CRUD Operations
         * */
        // 샘플데이타 넣기
        Log.d("Insert: ", "Inserting ..");

        db.addCart(new Cart(1, 1, "배추", "a.jpg", 2000, 5, 2500, "양배추"));
        db.addCart(new Cart(2, 1, "딸기", "b.jpg", 6000, 1, 4000, "참 예쁜 딸기"));
        db.addCart(new Cart(3, 2, "사과", "c.jpg", 2000, 4, 4000, "적사과"));
        db.addCart(new Cart(4, 2, "양파", "d.jpg", 2000, 3, 2500, "알이 큰 양파"));
        db.addCart(new Cart(5, 3, "피망", "e.jpg", 2000, 10, 3000, "적 피망"));
        db.addCart(new Cart(6, 3, "옥수수", "f.jpg", 2000, 7, 3000, "알이 큰 옥수수"));

        // 집어넣은 데이타 다시 읽어들이기
        Log.d("Reading: ", "Reading all contacts..");
       //List<Cart> carts = db.getAllCarts();

//        for (Cart cn : carts) {
//            String log = "Id: "+cn.get_id()+" ,Name: " + cn.getName() + " ,Option: " + cn.getOptions();
//            Log.d("Result : ", log);
//        }

    }


}