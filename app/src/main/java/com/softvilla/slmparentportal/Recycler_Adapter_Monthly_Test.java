package com.softvilla.slmparentportal;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Malik on 03/08/2017.
 */

public class Recycler_Adapter_Monthly_Test extends RecyclerView.Adapter<View_Holder_Monthly_Test> {
    List<MonthlyTestInfo> list = Collections.emptyList();
    Context context;
    FragmentTransaction transaction;
    Recycler_Adapter_Monthly_Test.OnCardClickListner onCardClickListner;


    public Recycler_Adapter_Monthly_Test(ArrayList<MonthlyTestInfo> list, Context context, FragmentTransaction transaction) {
        this.list = list;
        this.context = context;
        this.transaction = transaction;
    }

    @Override
    public View_Holder_Monthly_Test onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.monthlytestrow, parent, false);
        View_Holder_Monthly_Test holder = new View_Holder_Monthly_Test(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(View_Holder_Monthly_Test holder, final int position) {

        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        holder.name.setText(list.get(position).name);
        holder.position.setText("Position: " + list.get(position).psition);
        holder.obtMarks.setText("Obtained Marks: " + list.get(position).obttMarks);
        holder.totalMarks.setText("Total Marks: " + list.get(position).totalMarks);
        //holder.description.setText(list.get(position).description);
       // holder.imageView.setImageResource(list.get(position).imageId);
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* AttendanceInfo.resultId = list.get(position).resultId;
                FragmentTransaction transaction = ((Activity)context).getFragmentManager().beginTransaction();
                //transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                transaction.replace(R.id.monthtestfragment, new Quiz());
                //transaction.addToBackStack(null);
                transaction.commit();*/
                /*Toast.makeText(context,"Clicked",Toast.LENGTH_SHORT).show();*/
                //Toast.makeText(context,list.get(position).resultId, Toast.LENGTH_SHORT).show();
                AttendanceInfo.whichFragment = "Subject";
                AttendanceInfo.resultId = list.get(position).resultId;
                transaction.replace(R.id.monthtestfragment, new Quiz());
                transaction.commit();
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
    public void insert(int position, MonthlyTestInfo data) {
        list.add(position, data);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(MonthlyTestInfo data) {
        int position = list.indexOf(data);
        list.remove(position);
        notifyItemRemoved(position);
    }


    public interface OnCardClickListner {
        void OnCardClicked(View view, int position);
    }

    public void setOnCardClickListner(Recycler_Adapter_Monthly_Test.OnCardClickListner onCardClickListner) {
        this.onCardClickListner = onCardClickListner;
    }

}
