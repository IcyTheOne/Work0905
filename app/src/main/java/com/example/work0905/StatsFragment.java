package com.example.work0905;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class StatsFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        FragmentManager fm = getChildFragmentManager();
        final Fragment fragment = fm.findFragmentById(R.id.fragment_container_stats);
        fm.beginTransaction().add(R.id.fragment_container_stats, new ThisMonthFragment()).addToBackStack(null).commit();

        ImageButton allTimeBtn = view.findViewById(R.id.allTimeBtn);
        ImageButton preMonthBtn = view.findViewById(R.id.preMonthBtn);
        ImageButton thisMonthBtn = view.findViewById(R.id.thisMonthBtn);

        allTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container_stats, new AllTimeFragment());
                ft.addToBackStack(null).commit();
            }
        });

        preMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
              //  ft.replace(R.id.fragment_container_stats, new PreMonthFragment());
                ft.addToBackStack(null).commit();
            }
        });

        thisMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container_stats, new ThisMonthFragment());
                ft.addToBackStack(null).commit();
            }
        });

        return view;
    }
}
