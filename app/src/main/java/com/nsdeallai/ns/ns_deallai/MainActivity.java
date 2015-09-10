package com.nsdeallai.ns.ns_deallai;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.nsdeallai.ns.ns_deallai.customer.Customer_Cart_Frag;
import com.nsdeallai.ns.ns_deallai.customer.Customer_StarPoint_Frag;
import com.nsdeallai.ns.ns_deallai.db.handler.DbUserHandler;
import com.nsdeallai.ns.ns_deallai.seller.Seller_Product_Insert_Frag;
import com.nsdeallai.ns.ns_deallai.seller.Seller_Product_detail_Frag;
import com.nsdeallai.ns.ns_deallai.seller.Seller_Product_detail_Test;
import com.nsdeallai.ns.ns_deallai.user.User_ChatActivity;
import com.nsdeallai.ns.ns_deallai.user.User_LoginActivity;
import com.nsdeallai.ns.ns_deallai.user.User_MyPageActivity;


public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ClickStar();
        ClickProduct();
        ClickInsertProduct();
        ClickCart();
        ClickStarList();
    }

    private void ClickStarList() {
        button = (Button) findViewById(R.id.product_review_list_button);

        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Seller_Product_detail_Test.class);
                startActivity(intent);
            }
        });
    }

    private void ClickCart() {
        button = (Button) findViewById(R.id.see_cart_button);

        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Customer_Cart_Frag.class);
                startActivity(intent);
            }
        });
    }

    private void ClickInsertProduct() {
        button = (Button) findViewById(R.id.product_insert_button);

        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Seller_Product_Insert_Frag.class);
                startActivity(intent);
            }
        });
    }

    private void ClickProduct() {
        button = (Button) findViewById(R.id.product_button);

        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Seller_Product_detail_Frag.class);
                startActivity(intent);
            }
        });
    }

    public void ClickStar() {
        button = (Button) findViewById(R.id.starbutton);

        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getApplicationContext(),"zzzz",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Customer_StarPoint_Frag.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 버튼 이벤트
     *
     * @param v : View 를 받아 안에 있는 id값 사용을 위해
     * @discription XML 에서 android:onClick 으로 동작
     * Parameter 값으로 View 받아 v.getId()를 이용하여 버튼 아이디 찾음
     * intent 를 사용하여 화면을 출력
     */
    public void mainOnClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.main_login_bu:
                intent = new Intent(this, User_LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.main_mypage_bu:
                intent = new Intent(this, User_MyPageActivity.class);
                startActivity(intent);
                break;
            case R.id.main_chat_bu:
                intent = new Intent(this, User_ChatActivity.class);
                startActivity(intent);
                break;
            case R.id.main_logout_bu:
                logOut();
                break;
        }
    }

    /**
     * 로그 아웃 버튼 클릭 이벤트
     *
     * @discription 로그 아웃 버튼 클릭시 sqlite 디비 테이블을 upgrade를 사용하여 초기화
     */
    private void logOut() {
        DbUserHandler dbUserHandler = DbUserHandler.open(this);
        dbUserHandler.upgrade();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
