package com.nsdeallai.ns.ns_deallai.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.nsdeallai.ns.ns_deallai.Server;
import com.nsdeallai.ns.ns_deallai.R;
import com.nsdeallai.ns.ns_deallai.db.adapter.MessageAdapter;
import com.nsdeallai.ns.ns_deallai.entity.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * NS_Deallai
 * Created by Kermit on 2015-08-30.
 */
public class User_ChatActivity extends AppCompatActivity {

    private RecyclerView mMessagesView;
    private List<Message> mMessages = new ArrayList<Message>();
    private MessageAdapter mAdapter;
    public int itemLayout;

    private Socket mSocket = Server.SererConnect();

//    {
//        try {
//            mSocket = IO.socket(Server.SERVER_URL);
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public User_ChatActivity() {
        super();

    }

    /**
     * RecyclerView 초기화
     * <p/>
     * RecyclerView 초기 Layout을 recyclerView지정
     * LayoutManager 초기화
     * Animator 초기화
     * Adapter 초기화
     */
    private void initLayout() {
        mMessagesView = (RecyclerView) findViewById(R.id.recyclerView);
        mMessagesView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mMessagesView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new MessageAdapter(mMessages, itemLayout);
        mMessagesView.setAdapter(mAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_chat_main);

        /*Layout 초기화*/
        initLayout();

        mSocket.connect().emit("start", "Chat go!!");

       /*Server로부터 받는 메세지 리스너*/
        mSocket.on("reChat", newMessage);

        Button sendButton = (Button) this.findViewById(R.id.chat_send_bt);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSend(v);
            }
        });
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

        String username = "Tes1";// test
        sendMessage(message, username);

    }

    /**
     * 채팅 수신 리스너
     *
     * @discription 미완성
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
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    /**
     * 메세지 전송 이벤트
     *
     * @discription 미완성
     */
    public void sendMessage(String message, String username) {
        mMessages.add(new Message.Builder(Message.TYPE_MESSAGE).username(username).message(message).build());
        mAdapter.notifyItemInserted(mMessages.size() - 1);
        scrollPosition();
    }

    /**
     * 메세지 스크롤 포지션 이벤트
     *
     * @discription mAdapter에서 size의 값을 가져와 포지션 위치를 지정
     */
    public void scrollPosition() {
        mMessagesView.scrollToPosition(mAdapter.getItemCount() - 1);
    }
}
