package com.softvilla.slmparentportal;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Malik on 06/09/2017.
 */

public class View_Holder_Notes extends RecyclerView.ViewHolder {
    CardView cv;
    TextView date,notes;

    public View_Holder_Notes(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardViewNotes);
        date = (TextView) itemView.findViewById(R.id.date);
        notes = (TextView) itemView.findViewById(R.id.notes);
    }
}
