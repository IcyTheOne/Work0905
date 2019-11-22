package com.example.work0905;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

        LoggedMonths jan = new LoggedMonths("January", "2019", 123, 123, 123);
        LoggedMonths jan1 = new LoggedMonths("February", "2019", 123, 234, 234);
        LoggedMonths jan2 = new LoggedMonths("March", "2019", 123, 543, 345);
        LoggedMonths jan3 = new LoggedMonths("April", "2019", 34, 232, 234);
        LoggedMonths jan4 = new LoggedMonths("May", "2019", 34, 232, 234);
        LoggedMonths jan5 = new LoggedMonths("June", "2019", 123, 123, 123);
        LoggedMonths jan6 = new LoggedMonths("July", "2019", 123, 234, 234);
        LoggedMonths jan7 = new LoggedMonths("August", "2019", 123, 543, 345);
        LoggedMonths jan8 = new LoggedMonths("September", "2019", 34, 232, 234);
        LoggedMonths jan9 = new LoggedMonths("October", "2019", 34, 232, 234);
        LoggedMonths jan10 = new LoggedMonths("November", "2019", 34, 232, 234);
        LoggedMonths jan11 = new LoggedMonths("December", "2019", 34, 232, 234);
        LoggedMonths jan12 = new LoggedMonths("January", "2018", 34, 232, 234);


        ArrayList<LoggedMonths> loggedMonthsList = new ArrayList<>();
        loggedMonthsList.add(jan);
        loggedMonthsList.add(jan1);
        loggedMonthsList.add(jan2);
        loggedMonthsList.add(jan3);
        loggedMonthsList.add(jan4);
        loggedMonthsList.add(jan5);
        loggedMonthsList.add(jan6);
        loggedMonthsList.add(jan7);
        loggedMonthsList.add(jan8);
        loggedMonthsList.add(jan9);
        loggedMonthsList.add(jan10);
        loggedMonthsList.add(jan11);
        loggedMonthsList.add(jan12);

        LoggedMonthsAdapter adapter = new LoggedMonthsAdapter(getContext(), R.layout.custom_listview, loggedMonthsList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openDialog();
            }
        });

        return view;
    }

    private void openDialog(){
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getChildFragmentManager(), "Example Dialog");
    }
}
