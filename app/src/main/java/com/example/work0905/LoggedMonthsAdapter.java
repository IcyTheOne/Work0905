package com.example.work0905;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.work0905.model.LoggedMonths;

import java.util.ArrayList;

public class LoggedMonthsAdapter extends ArrayAdapter<LoggedMonths> {

    private Context mContext;
    private int mResource;

    public LoggedMonthsAdapter(@NonNull Context context, int resource, @NonNull ArrayList<LoggedMonths> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String month = getItem(position).getMonth();
        String year = getItem(position).getYear();
        int totalHours = getItem(position).getTotalHours();
        int overtimeHours = getItem(position).getOvertimeHours();
        int estimatedSalary = getItem(position).getEstimatedSalary();

        LoggedMonths months = new LoggedMonths(month, year, totalHours, overtimeHours, estimatedSalary);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvMonth = convertView.findViewById(R.id.month_listview);
        TextView tvYear = convertView.findViewById(R.id.year_listview);

        tvMonth.setText(month);
        tvYear.setText(year);
        return convertView;
    }
}
