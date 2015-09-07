package com.nsdeallai.ns.ns_deallai;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    public List<Message> messageList;
    public int itemLayout;

    public MessageAdapter(List<Message> messageList, int itemLayout) {
        this.messageList = messageList;
        this.itemLayout = itemLayout;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message item = messageList.get(position);

        holder.textViewUserName.setText(item.getName());
        holder.textViewMessage.setText(item.getMessage());

    }

    @Override
    public int getItemCount() {
        return messageList.size();
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
