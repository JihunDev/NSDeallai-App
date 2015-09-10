package com.nsdeallai.ns.ns_deallai.seller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.nsdeallai.ns.ns_deallai.R;

/**
 * Created by SangSang on 2015-08-24.
 */
public class Seller_Product_Insert_Frag extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_product_insert_layout);

        Spinner spinner = (Spinner)findViewById(R.id.Spinner_product_category);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(
                this, R.array.products_category_arrays, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);


        ClickInsertProcut();

    }

    //상품 등록 버튼
    private void ClickInsertProcut() {
        button = (Button) findViewById(R.id.p_insert_button);
        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
    }
}
