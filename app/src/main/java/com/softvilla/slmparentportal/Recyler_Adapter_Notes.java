package com.softvilla.slmparentportal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Malik on 03/08/2017.
 */

public class Recyler_Adapter_Notes extends RecyclerView.Adapter<View_Holder_Notes> {
    ArrayList<NotesInfo> list;
    Context context;
    Recyler_Adapter_Notes.OnCardClickListner onCardClickListner;


    public Recyler_Adapter_Notes(ArrayList<NotesInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public View_Holder_Notes onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notesrow, parent, false);
        View_Holder_Notes holder = new View_Holder_Notes(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(View_Holder_Notes holder, int position) {

        try {
            holder.date.setText("Date: " + getMonth(list.get(position).date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.notes.setText("Note: " + list.get(position).notes);

        //holder.description.setText(list.get(position).description);
        // holder.imageView.setImageResource(list.get(position).imageId);
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //onCardClickListner.OnCardClicked(view, position);
            }
        });


    }



    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, NotesInfo data) {
        list.add(position, data);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(NotesInfo data) {
        int position = list.indexOf(data);
        list.remove(position);
        notifyItemRemoved(position);
    }


    public interface OnCardClickListner {
        void OnCardClicked(View view, int position);
    }

    public void setOnCardClickListner(Recyler_Adapter_Notes.OnCardClickListner onCardClickListner) {
        this.onCardClickListner = onCardClickListner;
    }

    private static String getMonth(String date) throws ParseException {

            Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            String monthName = new SimpleDateFormat("dd-MMM-yyyy").format(cal.getTime());
            return monthName;
        }

}
