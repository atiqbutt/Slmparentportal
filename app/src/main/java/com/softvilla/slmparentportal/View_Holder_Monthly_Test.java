package com.softvilla.slmparentportal;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Malik on 03/08/2017.
 */

public class View_Holder_Monthly_Test extends RecyclerView.ViewHolder {
    CardView cv;
    TextView name,position,obtMarks,totalMarks;

    public View_Holder_Monthly_Test(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardView);
        name = (TextView) itemView.findViewById(R.id.name);
        position = (TextView) itemView.findViewById(R.id.position);
        obtMarks = (TextView) itemView.findViewById(R.id.obtMarks);
        totalMarks = (TextView) itemView.findViewById(R.id.totlaMarks);
    }
}
