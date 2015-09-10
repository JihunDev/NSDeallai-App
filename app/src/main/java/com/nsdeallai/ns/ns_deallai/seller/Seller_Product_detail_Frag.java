package com.nsdeallai.ns.ns_deallai.seller;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.nsdeallai.ns.ns_deallai.R;
import com.nsdeallai.ns.ns_deallai.customer.Customer_Buy_Frag;
import com.nsdeallai.ns.ns_deallai.customer.Customer_Cart_Frag;
import com.nsdeallai.ns.ns_deallai.db.helper.DbCartHelper;
import com.nsdeallai.ns.ns_deallai.entity.Cart;

/**
 * Created by SangSang on 2015-08-21.
 */
public class Seller_Product_detail_Frag extends AppCompatActivity {

    DbCartHelper dbCartHelper;
    SQLiteDatabase db;
    Spinner spinner;

    Button DirectBuy, GotoCart;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_product_detail_layout);

        //옵션 넣어주기
        spinner = (Spinner) findViewById(R.id.Spinner_product_options);
        //adapter넣어주어야 동작
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(
                this, R.array.products_options_arrays, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);


        //sqlite helper 불러옴.
        dbCartHelper = new DbCartHelper(this);

        ClickDirectBuy();
        //ClickGotoCart();

    }

    //바로구매 버튼
    private void ClickDirectBuy() {
        DirectBuy = (Button) findViewById(R.id.button3);

        DirectBuy.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Customer_Buy_Frag.class);
                startActivity(intent);
            }
        });
    }

    //장바구니 버튼
    private void ClickGotoCart() {
        GotoCart = (Button) findViewById(R.id.button2);


        GotoCart.setOnClickListener(new Button.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Customer_Cart_Frag.class);
                startActivity(intent);


            }
        });

    }


    public void insert(View target) {

        //제품 아이디
        int p_id = 1;

        //마켓 아이디
        int m_id = 1;

        //상품 이름
        TextView product_name = (TextView) findViewById(R.id.product_name);
        String name = product_name.getText().toString();

        //상품 이미지
        ImageView product_image = (ImageView) findViewById(R.id.product_image);
        String image = String.valueOf(product_image.getDrawable());

        //상품 가격
        TextView product_price = (TextView) findViewById(R.id.product_price);
        int price = Integer.parseInt(product_price.getText().toString());

        //상품 수량
        EditText product_quantity = (EditText) findViewById(R.id.product_buynumber);
        if (product_quantity.getText().toString().equals("")) { //상품 수량 안 적을 경우 1로 넘어감
            product_quantity.setText("1");
        }
        int quantity = Integer.parseInt(product_quantity.getText().toString());


        //상품 배송비
        TextView product_delivery = (TextView) findViewById(R.id.product_delivery);
        int delivery = Integer.parseInt(product_delivery.getText().toString());


        //상품 옵션
        String options = spinner.getSelectedItem().toString();


        //sqlite 실행
        dbCartHelper.addCart(new Cart(p_id, m_id, name, image, price, quantity, delivery, options));

//        Toast.makeText(getApplicationContext(), "장바구니 등록 성공",
//                Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), Customer_Cart_Frag.class);
        startActivity(intent);

    }
}
