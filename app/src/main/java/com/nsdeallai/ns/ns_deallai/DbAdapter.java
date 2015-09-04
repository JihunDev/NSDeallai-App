package com.nsdeallai.ns.ns_deallai;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by SangSang on 2015-08-25.
 */
//sqlite의 데이터와 listview를 연결해주기 위해 만든 클래스
public class DbAdapter extends CursorAdapter {

//    private Context context;
//    private Cursor cursor;
    DbHelper dbhelper;

//* ListView는 스크롤할때마다 bindView를 하므로 checked값을 따로 가지고 있어야 한다.
//* 체크가 되면 배열에 저장을 해두었다가
// * bindVIew를 할때 배열에 저장된 값을 가지고 와서 setChecked를 해주는 식으로 코딩해야 함.
    private boolean[] checked;

    private CheckBox checkBox;

//    private ArrayList<Boolean> itemChecked = new ArrayList<Boolean>();


    //생성자에서 추출할 data의 column index를 가져오는 코드를 구현
    public DbAdapter(Context context, Cursor c) {
        super(context, c);
//        this.context = context;
//        this.cursor = c;
//        for(int i =0; i<this.getCount(); i++) {
//            itemChecked.add(i, false);
//        }
        checked = new boolean[c.getCount()];

        dbhelper= new DbHelper(context);
    }

    //cursor가 가리키고 있는 data를 view에 bind함.
    //listview 안의 row 설정
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        final ImageView image = (ImageView)view.findViewById(R.id.image);
        final TextView name = (TextView)view.findViewById(R.id.name);
        final TextView price = (TextView)view.findViewById(R.id.price);
        final TextView quantity = (TextView)view.findViewById(R.id.quantity);
        final TextView options = (TextView)view.findViewById(R.id.options);
        checkBox = (CheckBox)view.findViewById(R.id.cart_checkbox);
        final int position = cursor.getPosition();
        final Button decrease = (Button)view.findViewById(R.id.decrease);
        final Button increase = (Button)view.findViewById(R.id.increase);

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
        options.setText(cursor.getString(cursor.getColumnIndex("options")));
        decrease.setTag(cursor.getPosition());
        increase.setTag(cursor.getPosition());
        final int _id =Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id")));

        //quantity 감소하는 함수
        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity_number <=1) {
                    // Show an error message as a toast
                   // Toast.makeText(this, "You cannot have more than 100", Toast.LENGTH_SHORT).show();
                    // Exit this method early because there's nothing left to do
                    return;
                }
                int q_number = quantity_number-1;

                dbhelper.updateCart(new Cart(_id, q_number, options.getText().toString()));
                options.setText(options.getText().toString());
                onContentChanged();
            }
        });

        //quantity 증가 함수
        increase.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (quantity_number == 100) {
                    // Show an error message as a toast
                    //  Toast.makeText(this, "You cannot have smaller than 1", Toast.LENGTH_SHORT).show();
                    // Exit this method early because there's nothing left to do
                    return;
                }
                int q_number = quantity_number+1;

                dbhelper.updateCart(new Cart(_id , q_number,options.getText().toString()));
                options.setText(options.getText().toString());
                onContentChanged();
            }
        });



    }

    //List를 나타낼 View를 return.
    //listview row 안에 bindview에서 설정한것을 적용함.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.listitem, parent, false);

        return v;
    }

    @Override
    protected void onContentChanged() {
        super.onContentChanged();
        notifyDataSetChanged();
    }

    //checkbox를 모두 선택&해제하는 메소드
    public void setAllChecked(boolean ischecked){
        int tempsize = checked.length;
        for(int a=0; a<tempsize; a++){
            checked[a] = ischecked;
            checkBox.setChecked(checked[a]);
        }
    }



//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//
//        if (convertView == null) {
//            LayoutInflater inflater = (LayoutInflater) context
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.listitem, null);
//        }
//        View v = convertView;
//
//        final CheckBox checkBox = (CheckBox) v.findViewById(R.id.cb_checkbox);
//
//        checkBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CheckBox cb = (CheckBox) v.findViewById(R.id.cb_checkbox);
//
//                if(cb.isChecked()) {
//                    itemChecked.set(position, true);
//                    // do some operations here
//                }else if(!cb.isChecked()) {
//                    itemChecked.set(position,false);
//                    // do some operations here
//                }
//            }
//        });
//        checkBox.setChecked(itemChecked.get(position));
//
//        return v;
//        }
}

