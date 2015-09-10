package com.nsdeallai.ns.ns_deallai.seller;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.nsdeallai.ns.ns_deallai.R;
import com.nsdeallai.ns.ns_deallai.Server;
import com.nsdeallai.ns.ns_deallai.db.adapter.DbStarAdapter;
import com.nsdeallai.ns.ns_deallai.entity.PR_REVIEW;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Created by SangSang on 2015-09-08.
 * 상품평 보는 곳... 일부로 우선 만듬...
 */
public class Seller_Product_detail_Test extends AppCompatActivity {

    ListView list;
    DbStarAdapter dbAdapter;
    Cursor cursor;
    private Socket socket = Server.SererConnect();
    ArrayList<PR_REVIEW> pr_arraylist = new ArrayList<PR_REVIEW>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        socket.connect().emit("start", "StarPoint Go!");

        setContentView(R.layout.seller_product_review_layout);


        list = (ListView) findViewById(R.id.Star_listView);

        selectDB();


    }

    private void selectDB() {

        socket.emit("starSelect", "1").on("res", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                System.out.println("args : " + args);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < args.length; i++) {
                            // PR_REVIEW pr = new PR_REVIEW(args[i]);
                            JSONObject arg = (JSONObject) args[i];
                            System.out.println("arg : " + arg);
                            try {
                                int pr_id = Integer.parseInt(arg.getString("pr_id"));
                                String o_id = arg.getString("o_id");
                                int p_id = Integer.parseInt(arg.getString("p_id"));
                                String u_id = arg.getString("u_id");
                                String pr_content = arg.getString("pr_content");
                                float pr_star = Float.parseFloat(arg.getString("pr_star"));

                                pr_arraylist.add(new PR_REVIEW(pr_id, o_id, p_id, u_id, pr_content, pr_star));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        lists();
                    }
                });
            }
        });

    }

    /**
     * 다른 thread안에서는 동작하지 않으므로 함수로 따로 빼서 넣어줌.
     * */
    private void lists() {
        System.out.println("list : " + pr_arraylist);
        dbAdapter = new DbStarAdapter(this, 0, pr_arraylist);
        list.setAdapter(dbAdapter);
    }
}
