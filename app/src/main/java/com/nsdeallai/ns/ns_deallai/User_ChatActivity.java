package com.nsdeallai.ns.ns_deallai;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * NS_Deallai
 * Created by Kermit on 2015-08-30.
 */
public class User_ChatActivity extends AppCompatActivity {
    private RecyclerView lecyclerView;
    private Socket mSocket;

    {
        try {
            mSocket = IO.socket(Constants.SERVER_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public User_ChatActivity() {
        super();
    }

    private void initLayout() {
        lecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_chat_main);

        initLayout();
        mSocket.on("reChat", newMessage);
        mSocket.connect().emit("start", "Chat go!!");
    }

    /**
     * 메세지 전송 이벤트
     *
     * @param view
     * @discription 화면의 EditText의 값을 처리 하여 socket 통신
     */
    public void attemptSend(View view) {
        EditText mInputMessageView = (EditText) findViewById(R.id.message_input);

        String message = mInputMessageView.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            return;
        }
        mInputMessageView.setText("");
        mSocket.emit("newMessage", message);
    }

    /**
     * 채팅 수신 이벤트
     */
    private Emitter.Listener newMessage = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];

                    String msg;
                    try {
                        msg = data.getString("message");
                    } catch (JSONException e) {
                        return;
                    }

                    String username = "Test"; // Test

                    Message message = new Message();
                    message.setMessage(msg);
                    message.setName(username);

                    List<Message> list = new ArrayList<Message>();
                    list.add(message);

                    lecyclerView.setAdapter(new MessageAdapter(list,R.layout.item_message));
                    lecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    lecyclerView.setItemAnimator(new DefaultItemAnimator());

                    Log.d("Tag Socket message : ", msg);
                }
            });
        }
    };


}
