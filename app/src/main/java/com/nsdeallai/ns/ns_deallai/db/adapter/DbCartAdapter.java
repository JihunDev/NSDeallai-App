package com.nsdeallai.ns.ns_deallai.db.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.nsdeallai.ns.ns_deallai.db.helper.DbCartHelper;
import com.nsdeallai.ns.ns_deallai.R;
import com.nsdeallai.ns.ns_deallai.entity.Cart;

/**
 * Created by SangSang on 2015-08-25.
 * sqlite의 데이터와 listview를 연결해주기 위해 만든 클래스
 */
public class DbCartAdapter extends CursorAdapter {

    DbCartHelper dbhelper;

    /**
     * @description : ListView는 스크롤할때마다 bindView를 하므로 checked값을 따로 가지고 있어야 한다.
     * 체크가 되면 배열에 저장을 해두었다가
     * bindVIew를 할때 배열에 저장된 값을 가지고 와서 setChecked를 해주는 식으로 코딩해야 함.
     */
    private boolean[] checked;

    private CheckBox checkBox;


    /**
     * 생성자에서 추출할 data의 column index를 가져오는 코드를 구현
     */
    public DbCartAdapter(Context context, Cursor c) {
        super(context, c);
        checked = new boolean[c.getCount()];

        dbhelper = new DbCartHelper(context);
    }

    /**
     * cursor가 가리키고 있는 data를 view에 bind함.
     * listview 안의 row 설정
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        final ImageView image = (ImageView) view.findViewById(R.id.image);
        final TextView name = (TextView) view.findViewById(R.id.name);
        final TextView price = (TextView) view.findViewById(R.id.price);
        final TextView quantity = (TextView) view.findViewById(R.id.quantity);


        checkBox = (CheckBox) view.findViewById(R.id.cart_checkbox);
        final int position = cursor.getPosition();
        //  final TextView options = (TextView)view.findViewById(R.id.options);
        final Spinner options = (Spinner) view.findViewById(R.id.options);
        options.setTag(position);

        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(
                context, R.array.products_options_arrays, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        options.setAdapter(adapter1);

        final Button decrease = (Button) view.findViewById(R.id.decrease);
        final Button increase = (Button) view.findViewById(R.id.increase);

        //scroll내려도 check 유지되게 함.
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checked[position] = checkBox.isChecked();
            }
        });
        checkBox.setChecked(checked[position]);


        image.setImageResource(R.drawable.product1);
        name.setText(cursor.getString(cursor.getColumnIndex("name")));
        price.setText(cursor.getString(cursor.getColumnIndex("price")));
        quantity.setText(cursor.getString(cursor.getColumnIndex("quantity")));
        final int quantity_number = Integer.parseInt(quantity.getText().toString());

        decrease.setTag(cursor.getPosition());
        increase.setTag(cursor.getPosition());
        final int _id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id")));


        //options.setText(cursor.getString(cursor.getColumnIndex("options")));
        String[] array = {"배추 10개", "딸기 1박스", "피망 10개", "당근 10개"};

        int cursor2_position = 0;
        for (int i = 0; i < array.length; i++) {
            String option_string = array[i];
            if (option_string.equals(cursor.getString(cursor.getColumnIndex("options")))) {
                cursor2_position = i;
            }
        }
        options.setSelection(cursor2_position);


//        options.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String item = (String) options.getSelectedItem();
//                dbhelper.updateCart(new Cart(_id, quantity_number, item));
//                onContentChanged();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });


        //quantity 감소하는 함수
        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity_number <= 1) {
                    // Show an error message as a toast
                    // Toast.makeText(this, "You cannot have more than 100", Toast.LENGTH_SHORT).show();
                    // Exit this method early because there's nothing left to do
                    return;
                }
                int q_number = quantity_number - 1;

                dbhelper.updateCart(new Cart(_id, q_number, options.getSelectedItem().toString()));
                quantity.setText(quantity.getText().toString());
                onContentChanged();
            }
        });

        //quantity 증가 함수
        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity_number == 100) {
                    // Show an error message as a toast
                    //  Toast.makeText(this, "You cannot have smaller than 1", Toast.LENGTH_SHORT).show();
                    // Exit this method early because there's nothing left to do
                    return;
                }
                int q_number = quantity_number + 1;

                dbhelper.updateCart(new Cart(_id, q_number, options.getSelectedItem().toString()));
                quantity.setText(quantity.getText().toString());
                onContentChanged();
            }
        });


    }

    /**
     * List를 나타낼 View를 return.
     * listview row 안에 bindview에서 설정한것을 적용함.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.listitem, parent, false);

        return v;
    }

    /**
     * list내용 변할 시 동작하는 함수
     */
    @Override
    protected void onContentChanged() {
        super.onContentChanged();
        notifyDataSetChanged();
    }

    /**
     * checkbox를 모두 선택&해제하는 메소드
     *
     * @param ischecked : 모두선택의 checkbox가 체크 되어있는지 true,false로 넘어옴.
     */
    public void setAllChecked(boolean ischecked) {
        int tempsize = checked.length;
        for (int a = 0; a < tempsize; a++) {
            checked[a] = ischecked;
            checkBox.setChecked(checked[a]);
        }
    }

}

