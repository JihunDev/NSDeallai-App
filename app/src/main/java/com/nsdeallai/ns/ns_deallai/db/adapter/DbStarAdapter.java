package com.nsdeallai.ns.ns_deallai.db.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nsdeallai.ns.ns_deallai.R;
import com.nsdeallai.ns.ns_deallai.entity.PR_REVIEW;

import java.util.ArrayList;

/**
 * Created by SangSang on 2015-09-08.
 * mariaDB에서 가져온 데이터와 listview를 연결해주는 클래스
 */
public class DbStarAdapter extends ArrayAdapter<PR_REVIEW> {

    private ArrayList<PR_REVIEW> pr_reviewData = new ArrayList<PR_REVIEW>();

    TextView p_id, u_id, star_review;
    RatingBar star_ratingbar;

    /**
     *
     * */
    public DbStarAdapter(Context context, int resource, ArrayList<PR_REVIEW> pr_arraylist) {
        super(context, resource, pr_arraylist);
        pr_reviewData = pr_arraylist;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.starpoint_listitem, null, true);

        p_id = (TextView) view.findViewById(R.id.p_id);
        u_id = (TextView) view.findViewById(R.id.u_id);
        star_ratingbar = (RatingBar) view.findViewById(R.id.star_ratingBar);
        star_review = (TextView) view.findViewById(R.id.star_review_textview);

        /**
         * @description : int형은 valeOf로 가져오지 않으면
         * android.content.res.Resources$NotFoundException: String resource ID 오류가 남.
         * */
        p_id.setText(String.valueOf(pr_reviewData.get(position).getP_id()));
        u_id.setText(pr_reviewData.get(position).getU_id());
        star_ratingbar.setRating(pr_reviewData.get(position).getPr_star());
        star_review.setText(pr_reviewData.get(position).getPr_content());

        return view;
    }

}
