package com.nsdeallai.ns.ns_deallai;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SangSang on 2015-08-25.
 */
//sqlite의 데이터와 listview를 연결해주기 위해 만든 클래스
public class DbAdapter extends CursorAdapter {

    private Context context;
    private Cursor cursor;

    private ArrayList<Boolean> itemChecked = new ArrayList<Boolean>();

    public DbAdapter(Context context, Cursor c) {
        super(context, c);
        this.context = context;
        this.cursor = c;
        for(int i =0; i<this.getCount(); i++) {
            itemChecked.add(i, false);
        }
    }
    //listview 안의 row 설정
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        final ImageView image = (ImageView)view.findViewById(R.id.image);
        final TextView name = (TextView)view.findViewById(R.id.name);
        final TextView price = (TextView)view.findViewById(R.id.price);
        final TextView quantity = (TextView)view.findViewById(R.id.quantity);
        final TextView options = (TextView)view.findViewById(R.id.options);
        final CheckBox checkBox = (CheckBox)view.findViewById(R.id.cb_checkbox);

        image.setImageResource(R.drawable.product1);
        name.setText(cursor.getString(cursor.getColumnIndex("name")));
        price.setText(cursor.getString(cursor.getColumnIndex("price")));
        quantity.setText(cursor.getString(cursor.getColumnIndex("quantity")));
        options.setText(cursor.getString(cursor.getColumnIndex("options")));
        checkBox.setChecked(false);
    }

    //listview row 안에 bindview에서 설정한것을 적용함.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.listitem, parent, false);
        return v;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listitem, null);
        }
        View v = convertView;

        bindView(v,context,cursor);

        final CheckBox checkBox = (CheckBox) v.findViewById(R.id.cb_checkbox);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v.findViewById(R.id.cb_checkbox);

                if(cb.isChecked()) {
                    itemChecked.set(position, true);
                    // do some operations here
                }else if(!cb.isChecked()) {
                    itemChecked.set(position,false);
                    // do some operations here
                }
            }
        });
        checkBox.setChecked(itemChecked.get(position));

        return v;
        }
}

