package com.softvilla.slmparentportal;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Malik on 03/08/2017.
 */

public class View_Holde_Quiz extends RecyclerView.ViewHolder {
    CardView cv;
    TextView name,date,obtMarks,totalMarks;

    public View_Holde_Quiz(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardView);
        name = (TextView) itemView.findViewById(R.id.name);
        date = (TextView) itemView.findViewById(R.id.position);
        obtMarks = (TextView) itemView.findViewById(R.id.obtMarks);
        totalMarks = (TextView) itemView.findViewById(R.id.totlaMarks);
    }
}
