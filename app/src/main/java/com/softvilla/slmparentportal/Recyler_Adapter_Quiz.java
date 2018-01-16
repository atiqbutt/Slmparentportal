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

public class Recyler_Adapter_Quiz extends RecyclerView.Adapter<View_Holde_Quiz> {
    ArrayList<QuizInfo> list;
    Context context;
    Recyler_Adapter_Quiz.OnCardClickListner onCardClickListner;


    public Recyler_Adapter_Quiz(ArrayList<QuizInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public View_Holde_Quiz onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.monthlytestrow, parent, false);
        View_Holde_Quiz holder = new View_Holde_Quiz(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(View_Holde_Quiz holder, final int position) {

        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView

        try {
            holder.date.setText("Date: " + getMonth(list.get(position).date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.name.setText("Subject: " + list.get(position).name);
        holder.totalMarks.setText("Total Marks: " + list.get(position).totalMarks);
        holder.obtMarks.setText("Obtain Marks: " + list.get(position).obtainMarks);
        //holder.description.setText(list.get(position).description);
        // holder.imageView.setImageResource(list.get(position).imageId);
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //onCardClickListner.OnCardClicked(view, position);
            }
        });


        //animate(holder);

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
    public void insert(int position, QuizInfo data) {
        list.add(position, data);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(QuizInfo data) {
        int position = list.indexOf(data);
        list.remove(position);
        notifyItemRemoved(position);
    }


    public interface OnCardClickListner {
        void OnCardClicked(View view, int position);
    }

    public void setOnCardClickListner(Recyler_Adapter_Quiz.OnCardClickListner onCardClickListner) {
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
