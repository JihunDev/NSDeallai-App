package com.nsdeallai.ns.ns_deallai.db.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.github.nkzawa.socketio.client.Socket;
import com.nsdeallai.ns.ns_deallai.Server;
import com.nsdeallai.ns.ns_deallai.customer.Customer_StarPoint_update;
import com.nsdeallai.ns.ns_deallai.R;
import com.nsdeallai.ns.ns_deallai.entity.PR_REVIEW;

import java.util.ArrayList;

/**
 * Created by SangSang on 2015-09-08.
 * mariaDB에서 가져온 데이터와 listview를 연결해주는 클래스
 */
public class DbStarAdapter extends ArrayAdapter<PR_REVIEW> {

    private ArrayList<PR_REVIEW> pr_arraylist = new ArrayList<PR_REVIEW>();

    TextView p_id, u_id, star_review;
    RatingBar star_ratingbar;
    Button star_delete_button, star_update_button;

    private AlertDialog mDialog = null;
    Socket socket = Server.SererConnect();


    public void setItems(ArrayList<PR_REVIEW> pr_arraylist){
        this.pr_arraylist = pr_arraylist;
    }

    /**
     *
     * */
    public DbStarAdapter(Context context, int resource, ArrayList<PR_REVIEW> pr_arraylist) {
        super(context, resource, pr_arraylist);
        this.pr_arraylist = pr_arraylist;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.starpoint_listitem, null, true);

        final int pos = position;

        p_id = (TextView) view.findViewById(R.id.p_id);
        u_id = (TextView) view.findViewById(R.id.u_id);
        star_ratingbar = (RatingBar) view.findViewById(R.id.star_ratingBar);
        star_review = (TextView) view.findViewById(R.id.star_review_textview);
        star_update_button = (Button) view.findViewById(R.id.star_update_button);
        star_delete_button = (Button) view.findViewById(R.id.star_delete_button);

        /**
         * @description : int형은 valeOf로 가져오지 않으면
         * android.content.res.Resources$NotFoundException: String resource ID 오류가 남.
         * */
        p_id.setText(String.valueOf(pr_arraylist.get(position).getP_id()));
        u_id.setText(pr_arraylist.get(position).getU_id());
        star_ratingbar.setRating(pr_arraylist.get(position).getPr_star());
        star_review.setText(pr_arraylist.get(position).getPr_content());

        final String o_id = pr_arraylist.get(position).getO_id();
        final int sp_id = Integer.parseInt(String.valueOf(pr_arraylist.get(position).getP_id()));
        final String su_id = pr_arraylist.get(position).getU_id();
        final float st_Ratingbar = Float.parseFloat(String.valueOf(pr_arraylist.get(position).getPr_star()));
        final String st_reivew = pr_arraylist.get(position).getPr_content();

        star_update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Customer_StarPoint_update.class);
                intent.putExtra("o_id", o_id);
                intent.putExtra("p_id", sp_id);
                intent.putExtra("u_id", su_id);
                intent.putExtra("star_ratingbar", st_Ratingbar);
                intent.putExtra("star_review", st_reivew);
                getContext().startActivity(intent);
            }
        });


        star_delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog = deleteDialog(o_id, pos);
                mDialog.show();
            }
        });

        return view;
    }

    /**
     * Infalter 삭제 다이얼로그
     *
     * @return delete
     */
    private AlertDialog deleteDialog(final String o_id, final int position) {
        Log.d("o_id", "주문 아이디는 : " + o_id);
        AlertDialog.Builder delete = new AlertDialog.Builder(getContext());
        delete.setTitle("상품평 삭제");
        delete.setMessage("정말로 삭제하시겠습니까?");
        delete.setCancelable(false);

        delete.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setDismiss(mDialog);
            }
        });

        delete.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                socket.connect();
                socket.emit("starDelete", o_id);
                Log.d("DELETE", "DELETE 완료");

                pr_arraylist.remove(pr_arraylist.get(position));
                DbStarAdapter.this.notifyDataSetChanged();
            }
        });

        return delete.create();
    }

    /**
     * 다이얼로그 종료
     *
     * @param dialog
     */
    private void setDismiss(Dialog dialog) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

}
