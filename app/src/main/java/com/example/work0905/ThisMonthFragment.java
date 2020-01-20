package com.example.work0905;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ThisMonthFragment extends Fragment {

    TextView employeeName;
    String fullName;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_this_month, container, false);

        employeeName = view.findViewById(R.id.employee_name);
        fullName = NavigationBar.employee.getName() + " " + NavigationBar.employee.getLast_name();
        employeeName.setText(fullName);

        return view;
    }
}
