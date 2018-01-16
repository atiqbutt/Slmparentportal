package com.softvilla.slmparentportal;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

/**
 * Created by Malik on 04/08/2017.
 */

public class ViewHolder_Fee extends RecyclerView.ViewHolder {

    CardView cv;
    TextView received, remaining, advance,issueDate, expireDate, submittedAt, arrears, fee;
    Button expBtn;
    ExpandableRelativeLayout expandLayout;
    //ImageView imageView;



    ViewHolder_Fee(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardViewfee);
        received = (TextView) itemView.findViewById(R.id.recieved);
        remaining = (TextView) itemView.findViewById(R.id.remaining);
        advance = (TextView) itemView.findViewById(R.id.advance);
        issueDate = (TextView) itemView.findViewById(R.id.issuedate);
        submittedAt = (TextView) itemView.findViewById(R.id.submittedAt);
        arrears = (TextView) itemView.findViewById(R.id.arrears);
        fee = (TextView) itemView.findViewById(R.id.fee);
        expireDate = (TextView) itemView.findViewById(R.id.expiredate);
        expBtn = (Button) itemView.findViewById(R.id.lbs);
        expandLayout = (ExpandableRelativeLayout) itemView.findViewById(R.id.expandableLayout);
        //imageView = (ImageView) itemView.findViewById(R.id.circleView);

    }


}