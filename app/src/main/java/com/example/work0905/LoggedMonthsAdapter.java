package com.example.work0905;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

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

        LoggedMonths months = new LoggedMonths(month);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvMonth = convertView.findViewById(R.id.month_listview);

        tvMonth.setText(month);
        return convertView;
    }
}
