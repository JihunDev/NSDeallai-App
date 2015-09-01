package com.nsdeallai.ns.ns_deallai;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by SangSang on 2015-08-20.
 */
public class Customer_Cart_Frag extends AppCompatActivity {

    DbHelper dbHelper;
    DbAdapter dbAdapter;
    SQLiteDatabase db;
    String sql;
    Cursor cursor;

    ListView list;

  /*  String[] names = {
            "옥수수",
            "배추",
            "토마토",
            "딸기",
            "옥수수",
            "배추",
            "토마토",
            "딸기"
    } ;
    Integer[] images = {
            R.drawable.product1,
            R.drawable.product1,
            R.drawable.product1,
            R.drawable.product1,
            R.drawable.product1,
            R.drawable.product1,
            R.drawable.product1,
            R.drawable.product1
    };*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_cart_layout);

    //    CustomList adapter = new CustomList(Customer_Cart_Frag.this);
        list=(ListView)findViewById(R.id.list);

        dbHelper = new DbHelper(this);
      //  list.setAdapter(adapter);

        selectDB();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                cursor.moveToPosition(position);
                String str = cursor.getString(cursor.getColumnIndex("name"));
                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
            }
        });
    }


//    public class CustomList extends ArrayAdapter<String> {
//        private final Activity context;
//        public CustomList(Activity context ) {
//            super(context, R.layout.listitem, names);
//            this.context = context;
//        }
//        @Override
//        public View getView(int position, View view, ViewGroup parent) {
//            LayoutInflater inflater = context.getLayoutInflater();
//            View rowView= inflater.inflate(R.layout.listitem, null, true);
//            ImageView imageView = (ImageView) rowView.findViewById(R.id.image);
//            TextView title = (TextView) rowView.findViewById(R.id.name);
//            TextView price = (TextView) rowView.findViewById(R.id.price);
//          /*  TextView genre = (TextView) rowView.findViewById(R.id.genre);*/
//            TextView quantity = (TextView) rowView.findViewById(R.id.quantity);
//
//            title.setText(names[position]);
//            imageView.setImageResource(images[position]);
//            price.setText("3" + ((position * 100) + 200));
//           /* genre.setText("DRAMA");*/
//            quantity.setText(50+position+"");
//            return rowView;
//        }
//    }



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


    //sqlite에 있는 리스트를 listview에 적용하는 함수
     /*
    * Method : onCreate될때 동작
    * Parameter : 없음
    * Result Type : void
    * Result : 안드로이드 내에 customer.db에 cart table에서 리스트 꺼내와 listview에 뿌려줌
    * Explain
    * DbAdapter에서 listitem.xml에 있는 것을 listview에 붙여주고 select한 것의 row 하나하나를 listview에 넣어줌.
   */
    private void selectDB(){
        db = dbHelper.getWritableDatabase();
        sql = "SELECT * FROM cart;";

        cursor = db.rawQuery(sql, null);
        if(cursor.getCount() > 0){
            startManagingCursor(cursor);
            dbAdapter = new DbAdapter(this, cursor);
            list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            list.setAdapter(dbAdapter);
        }
    }


}
