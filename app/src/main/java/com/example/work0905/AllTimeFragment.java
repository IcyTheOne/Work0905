package com.example.work0905;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class AllTimeFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_time, container, false);

        ListView listView = view.findViewById(R.id.all_time_listview);

        LoggedMonths jan = new LoggedMonths("January");
        LoggedMonths jan1 = new LoggedMonths("January");
        LoggedMonths jan2 = new LoggedMonths("January");
        LoggedMonths jan3 = new LoggedMonths("January");

        ArrayList<LoggedMonths> loggedMonthsList = new ArrayList<>();
        loggedMonthsList.add(jan);
        loggedMonthsList.add(jan1);
        loggedMonthsList.add(jan2);
        loggedMonthsList.add(jan3);

        LoggedMonthsAdapter adapter = new LoggedMonthsAdapter(getContext(), R.layout.custom_listview, loggedMonthsList);
        listView.setAdapter(adapter);

        return view;
    }
}
