package com.nsdeallai.ns.ns_deallai;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.nsdeallai.ns.ns_deallai.db.adapter.DbCartAdapter;
import com.nsdeallai.ns.ns_deallai.db.helper.DbCartHelper;

import java.util.ArrayList;

/**
 * Created by SangSang on 2015-08-20.
 */
public class Customer_Cart_Frag extends AppCompatActivity {

    DbCartHelper dbCartHelper;
    DbCartAdapter dbCartAdapter;
    Cursor cursor;
    ListView list;
    CheckBox allselect;
    Button selectdelete;
    ArrayList<String> idlist = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_cart_layout);

        //    CustomList adapter = new CustomList(Customer_Cart_Frag.this);
        list = (ListView) findViewById(R.id.list);
        allselect = (CheckBox) findViewById(R.id.allSelect);
        selectdelete = (Button) findViewById(R.id.selectItemsDelete);

        dbCartHelper = new DbCartHelper(this);

        //  list.setAdapter(adapter);

        selectDB();
        setAllselect();


        //체크박스 선택된것을 삭제하는 버튼...
        selectdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int listCount = list.getChildCount();

                CheckBox cb;
                for (int i = 0; i < listCount; i++) {
                    cb = (CheckBox) (list.getChildAt(i).findViewById(R.id.cart_checkbox));
                    Log.d("Tag", "cursor position : " + i);
                    if (cb.isChecked()) {
                        cursor.moveToPosition(i);
                        Log.d("Tag", "CHECK! -> cursor position : " + i);
                        Log.d("Tag", "_id : " + cursor.getString(0));
                        idlist.add(cursor.getString(0));

                    }
                }
                if (idlist.size() > 0) {
                    String[] _id = new String[idlist.size()];
                    idlist.toArray(_id);
                    dbCartHelper.SelectedDeleteCarts(_id);
                    refreshList();
                }
            }
        });

        //리스트 하나 클릭시 토스트 발생하게 함.
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                cursor.moveToPosition(position);
                String str = cursor.getString(cursor.getColumnIndex("name"));


                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
            }
        });

    }


//    public void search(View target) {
    //   String name = edit_name.getText().toString();
//        Cursor cursor;
    //   cursor = db.rawQuery("SELECT name, tel FROM contacts WHERE name='"
    //           + name + "';", null);

//        while (cursor.moveToNext()) {
//            String tel = cursor.getString(1);
//            edit_tel.setText(tel);
//        }
//    }


    /**
     * sqlite에 있는 리스트를 listview에 적용하는 함수
     * Method : onCreate될때 동작
     * Result : 안드로이드 내에 customer.db에 cart table에서 리스트 꺼내와 listview에 뿌려줌
     * @description : DbAdapter에서 listitem.xml에 있는 것을 listview에 붙여주고 select한 것의 row 하나하나를 listview에 넣어줌.
     */
    private void selectDB() {

        cursor = dbCartHelper.getAllCarts();
        if (cursor.getCount() > 0) {
            startManagingCursor(cursor);
            dbCartAdapter = new DbCartAdapter(this, cursor);
            list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            list.setAdapter(dbCartAdapter);
        }
    }

    /**
     * 모든 체크박스를 선택 또는 해체 하는 함수
     * Method :모두선택 체크박스를 클릭 또는 해제 했을 때 실행
     * Result : 모든 listview에 있는 체크박스를 선택하거나 해제 시킴.
     * @description : allselect가 check되어 있는지 여부에 따라 체크 되었으면 true가 함수로 보내져서 모두 선택되고
     * 체크가 되어있지 않으면 false가 보내져 모두 해제된다.
     */
    private void setAllselect() {

        allselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //전체체크 감지하기 위한 함수(DbAdapter에서 가져옴)
                dbCartAdapter.setAllChecked(allselect.isChecked());
                dbCartAdapter.notifyDataSetChanged();
            }
        });

    }

    /**
     * Refresh the list. 데이터 변경 시 바뀐 list들을 다시 뿌려줌.
     */
    public void refreshList() {
        boolean requery = cursor.requery();
        if (requery) {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dbCartAdapter.notifyDataSetChanged();
                }
            });
        }
    }


}
