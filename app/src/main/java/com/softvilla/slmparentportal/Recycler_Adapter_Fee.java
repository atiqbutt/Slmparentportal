package com.softvilla.slmparentportal;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Malik on 04/08/2017.
 */

public class Recycler_Adapter_Fee extends RecyclerView.Adapter<ViewHolder_Fee> {
    List<FeeInfo> list = Collections.emptyList();
    Fee context;
    Recycler_Adapter_Fee.OnCardClickListner onCardClickListner;


    public Recycler_Adapter_Fee(List<FeeInfo> list, Fee context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder_Fee onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feerow, parent, false);
        ViewHolder_Fee holder = new ViewHolder_Fee(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder_Fee holder, final int position) {



        double Aerials = Double.parseDouble(list.get(position).rem) - Double.parseDouble(list.get(position).amount);

        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        holder.received.setText(bold("Received: ",list.get(position).received));
        holder.remaining.setText(bold("Remaining: " , list.get(position).remaining));
        holder.advance.setText(bold("Advances: " , list.get(position).advance));
        try {
            holder.issueDate.setText(bold("Issue Date: " , getMonth(list.get(position).issueDate)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            holder.submittedAt.setText(bold("Submitted Date: " , getMonth(list.get(position).date)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.fee.setText(bold("Fee Package: " , list.get(position).amount));
        holder.arrears.setText(bold("Arrears : " , String.valueOf(Aerials)));
        try {
            holder.expireDate.setText(bold("Expire Date: " , getMonth(list.get(position).expireDate)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // holder.imageView.setImageResource(list.get(position).imageId);
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //onCardClickListner.OnCardClicked(view, position);
            }
        });

        holder.expBtn.setText(list.get(position).month + "              " + list.get(position).Total);
        holder.expBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.expandLayout.toggle();
                        String tag = (String) holder.expBtn.getTag();

                        if(tag.equals("down")){
                            Resources resources = context.getResources();
                            Drawable img = resources.getDrawable(android.R.drawable.arrow_up_float);
                            holder.expBtn.setCompoundDrawablesWithIntrinsicBounds(null,null,img,null);
                            holder.expBtn.setTag("up");
                        }
                        else {
                            Resources resources = context.getResources();
                            Drawable img = resources.getDrawable(android.R.drawable.arrow_down_float);
                            holder.expBtn.setCompoundDrawablesWithIntrinsicBounds(null,null,img,null);
                            holder.expBtn.setTag("down");
                        }
                    }
                }
        );


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
    public void insert(int position, FeeInfo data) {
        list.add(position, data);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(FeeInfo data) {
        int position = list.indexOf(data);
        list.remove(position);
        notifyItemRemoved(position);
    }


    public interface OnCardClickListner {
        void OnCardClicked(View view, int position);
    }

    public void setOnCardClickListner(Recycler_Adapter_Fee.OnCardClickListner onCardClickListner) {
        this.onCardClickListner = onCardClickListner;
    }

    public SpannableString bold(String boldText, String normalText){
//        String boldText = "id";
//        String normalText = "name";
        SpannableString str = new SpannableString(boldText + normalText);
        str.setSpan(new StyleSpan(Typeface.BOLD), 0, boldText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return str;
    }

    private static String getMonth(String date) throws ParseException {
        if(date.equalsIgnoreCase("0000-00-00")){
            return "00-00-0000";
        }
        else {
            Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            String monthName = new SimpleDateFormat("dd-MMM-yyyy").format(cal.getTime());
            return monthName;
        }

    }

}
