package com.nsdeallai.ns.ns_deallai;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    public List<Message> messageList = new ArrayList<Message>();
    public int itemLayout;

    public MessageAdapter(List<Message> message, int item) {
        messageList = message;
        itemLayout = item;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = -1;
        switch (viewType) {
            case Message.TYPE_MESSAGE:
                layout = R.layout.item_message;
                break;
            case Message.TYPE_LOG:
                layout = R.layout.item_log;
                break;
            case Message.TYPE_ACTION:
                layout = R.layout.item_action;
                break;
        }
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message item = messageList.get(position);

        holder.textViewUserName.setText(item.getUsername());
        holder.textViewMessage.setText(item.getMessage());
    }

    @Override
    public int getItemCount() {
        return  messageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewUserName;
        public TextView textViewMessage;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewMessage = (TextView) itemView.findViewById(R.id.message);
            textViewUserName = (TextView) itemView.findViewById(R.id.username);
        }
    }
}
