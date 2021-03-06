package com.softvilla.slmparentportal;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Malik on 07/08/2017.
 */

public class CurrentMonth extends Fragment implements OnChartValueSelectedListener {


    public static final String CURRENTMONTH_URL = "http://slmhighschool.com/edusolutions/Api/getCurrentMonthAttendance";
    ArrayList<AttendanceInfo> currentMonthData;
    int total, present, absent, leave, shortLeave;
    Dialog dialog;
    ListView listView;
    TextView Title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.currentmonth, container, false);

        total = present = absent = leave = shortLeave = 0;
        getCurrentMonthAtt(view.getContext());
        dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.attendancereport);
        dialog.setTitle("Current Moth Attendance");
        Title = (TextView) dialog.findViewById(R.id.DialogTitle);
        currentMonthData = new ArrayList<AttendanceInfo>();

        final ProgressDialog dialog = new ProgressDialog(view.getContext());
        dialog.setMessage("Loading");
        dialog.setCancelable(false);







        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, CURRENTMONTH_URL,
                new Response.Listener<String>() {

                    String a = "";
                    @Override
                    public void onResponse(String response) {


                        try{
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i = 0; i<jsonArray.length();i++){
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                AttendanceInfo obj = new AttendanceInfo();
                                obj.date = jsonObject1.getString("date"+i);
                                obj.status = jsonObject1.getString("status"+i);
                                total = total + 1;
                                String status = jsonObject1.getString("status"+i);
                                if(status.equalsIgnoreCase("1")){
                                    present = present + 1;
                                    //a = a + "Present ";

                                }
                                else if(status.equalsIgnoreCase("2")){
                                    Toast.makeText(view.getContext(),"Presenet",Toast.LENGTH_SHORT).show();
                                    absent = absent + 1;
                                    //a = a + "Absent ";
                                }
                                else if(status.equalsIgnoreCase("3")){
                                    leave = leave + 1;
                                    //a = a + "Leave ";
                                }
                                else if(status.equalsIgnoreCase("4")){
                                    shortLeave = shortLeave + 1;
                                    //a = a + "Short Leave ";
                                }
                                currentMonthData.add(obj);


                            }
                            dialog.dismiss();
                            //Toast.makeText(view.getContext(),a,Toast.LENGTH_LONG).show();
                            PieChart pieChart = (PieChart) view.findViewById(R.id.currentmonth);
                            pieChart.setUsePercentValues(true);

                            // IMPORTANT: In a PieChart, no values (Entry) should have the same
                            // xIndex (even if from different DataSets), since no values can be
                            // drawn above each other.

                            ArrayList<Entry> yvalues = new ArrayList<Entry>();
                            if(total == 0){

                                yvalues.add(new Entry(0f, 0));
                                yvalues.add(new Entry(0f, 1));
                                yvalues.add(new Entry(0f, 2));
                                yvalues.add(new Entry(0f, 3));
                                Toast.makeText(view.getContext(),"No Data For Current Month",Toast.LENGTH_LONG).show();
                            }
                            else {
                                //ArrayList<Entry> yvalues = new ArrayList<Entry>();
                                yvalues.add(new Entry(((present*100)/total), 0));
                                yvalues.add(new Entry((absent*100)/total, 1));
                                yvalues.add(new Entry((leave*100)/total, 2));
                                yvalues.add(new Entry((shortLeave*100)/total, 3));



                            }




                            PieDataSet dataSet = new PieDataSet(yvalues, "Attendance");

                            ArrayList<String> xVals = new ArrayList<String>();

                            String p= "Present",a ="Absent",l = "Leave",sl = "Short Leave";
                            if(present == 0.0){
                                p = "";
                                //Toast.makeText(view.getContext(),"Presenet",Toast.LENGTH_SHORT).show();
                            }
                            if(absent == 0.0){
                                a = "";
                                //Toast.makeText(view.getContext(),"Absent",Toast.LENGTH_SHORT).show();
                            }
                            if(leave == 0.0){
                                l = "";
                                //Toast.makeText(view.getContext(),"Leave",Toast.LENGTH_SHORT).show();
                            }
                            if(shortLeave == 0.0){
                                //Toast.makeText(view.getContext(),"Short Leave",Toast.LENGTH_SHORT).show();
                                sl = "";
                            }
                            xVals.add(p);
                            xVals.add(a);
                            xVals.add(l);
                            xVals.add(sl);





                            PieData data = new PieData(xVals, dataSet);
                            // In Percentage term
                            data.setValueFormatter(new PercentFormatter());
                            // Default value
                            //data.setValueFormatter(new DefaultValueFormatter(0));
                            pieChart.setData(data);
                            pieChart.setDescription("Current Month Attendance Report");

                            pieChart.setDrawHoleEnabled(true);
                            pieChart.setTransparentCircleRadius(25f);
                            pieChart.setHoleRadius(25f);

                            dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
                            data.setValueTextSize(13f);
                            data.setValueTextColor(Color.DKGRAY);
                            pieChart.setOnChartValueSelectedListener(CurrentMonth.this);

                            pieChart.animateXY(1400, 1400);

                        }catch (JSONException e){

                            // Toast.makeText(LogIn.this,"Json Error",Toast.LENGTH_SHORT).show();

                            //Toast.makeText(LogIn.this,e.toString(),Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Toast.makeText(view.getContext(),"Connection Failed", Toast.LENGTH_LONG ).show();

                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                map.put("id",AttendanceInfo.id);
                return map;
            }

        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
        requestQueue.add(stringRequest);



        return view;
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {


            if(e.getXIndex()==0){
                ArrayList<String> data = new ArrayList<>();


                for(int i = currentMonthData.size() - 1; i >=0 ; i--){
                    if(currentMonthData.get(i).status.equalsIgnoreCase("1")){
                        try {
                            data.add(getMonth(currentMonthData.get(i).date));
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }

                    }
                }
                listView = (ListView) dialog.findViewById(R.id.att);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.layout.name_list,data);
                listView.setAdapter(adapter);
                Title.setText("Present");
                dialog.show();
                return;
            }
            else if(e.getXIndex()==1){
                ArrayList<String> data = new ArrayList<>();


                for(int i = currentMonthData.size() - 1 ; i >=0  ; i--){
                    if(currentMonthData.get(i).status.equalsIgnoreCase("2")){
                        try {
                            data.add(getMonth(currentMonthData.get(i).date));
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
                listView = (ListView) dialog.findViewById(R.id.att);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.layout.name_list,data);
                listView.setAdapter(adapter);
                Title.setText("Absent");
                dialog.show();
                return;
            }
            else if(e.getXIndex()==2){
                ArrayList<String> data = new ArrayList<>();


                for(int i = currentMonthData.size() - 1 ; i >=0 ; i--){
                    if(currentMonthData.get(i).status.equalsIgnoreCase("3")){
                        try {
                            data.add(getMonth(currentMonthData.get(i).date));
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
                listView = (ListView) dialog.findViewById(R.id.att);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.layout.name_list,data);
                listView.setAdapter(adapter);
                Title.setText("Leave");
                dialog.show();
                return;
            }
            else if(e.getXIndex()==3){
                ArrayList<String> data = new ArrayList<>();


                for(int i = currentMonthData.size() - 1 ; i >=0  ; i--){
                    if(currentMonthData.get(i).status.equalsIgnoreCase("4")){
                        try {
                            data.add(getMonth(currentMonthData.get(i).date));
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
                listView = (ListView) dialog.findViewById(R.id.att);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.layout.name_list,data);
                listView.setAdapter(adapter);
                Title.setText("Short Leave");
                dialog.show();

                return;
            }
            //String v = "VAL SELECTED"+ "Value: " + e.getVal() + ", xIndex: " + e.getXIndex() + ", DataSet index: " + dataSetIndex;
            //Toast.makeText(getContext(),v,Toast.LENGTH_LONG).show();

       // Log.i("VAL SELECTED", "Value: " + e.getVal() + ", xIndex: " + e.getXIndex() + ", DataSet index: " + dataSetIndex);
    }



    @Override
    public void onNothingSelected() {
        //Log.i("PieChart", "nothing selected");
    }


    void getCurrentMonthAtt(final Context context){


    }
    private static String getMonth(String date) throws ParseException {
        Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        String monthName = new SimpleDateFormat("dd-MMM-yyyy").format(cal.getTime());
        return monthName;
    }
}
