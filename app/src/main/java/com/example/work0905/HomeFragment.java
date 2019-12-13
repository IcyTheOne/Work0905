package com.example.work0905;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.work0905.util.DatabaseHandler;

import java.lang.ref.WeakReference;

public class HomeFragment extends Fragment {

    private ImageButton checkInOutBtn;
    private TextView emailTv;
    private String username;
    private CheckEmployeeStatusTask employeeStatus;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        username = NavigationBar.employee.getId();

        checkInOutBtn = view.findViewById(R.id.checkInOut_imb);
        emailTv = view.findViewById(R.id.email_content);

        if(NavigationBar.employee.isCheckedOut()){
            checkInOutBtn.setBackgroundResource(R.drawable.check_in_btn);
        } else {
            checkInOutBtn.setBackgroundResource(R.drawable.check_out_btn);
        }

//        employeeStatus = new CheckEmployeeStatusTask(HomeFragment.this);
//        employeeStatus.execute();

        emailTv.setText(NavigationBar.employee.getEmail());

        checkInOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckInOutTask checkInOutTask = new CheckInOutTask(HomeFragment.this);
                checkInOutTask.execute();
            }
        });
        return view;
    }


    /**
     * AsyncTask for handling the connection to the database and insert check in and check out timestamps in the database.
     * Used to avoid the networking error thrown by the UI thread.
     * (UI thread is not able to handle network operations and such assignment has to be handed in a separate task-thread)
     **/

    // Used on the creation of the view to check if employee has already checked in and set up the UI content and the functionality of the check in-out button.
    private static class CheckEmployeeStatusTask extends AsyncTask<Void, Void, Boolean> {

        private WeakReference<HomeFragment> fragmentWeakReference;
        private DatabaseHandler dbHandler;
        private String username;

        CheckEmployeeStatusTask(HomeFragment fragment){
            this.username = fragment.username;
            dbHandler = new DatabaseHandler();
            fragmentWeakReference = new WeakReference<>(fragment);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            if(dbHandler.openDbConnection(DatabaseHandler.FOR_CHECK_IN_OUT)){
                return dbHandler.isCheckedOut(this.username);
            }
            // Connection to dbHandler unsuccessful
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            dbHandler.closeDbConnection();

            HomeFragment fragment = fragmentWeakReference.get();
            if(fragment == null || fragment.isRemoving()) {
                return;
            }

            if(result) {
                fragment.checkInOutBtn.setBackgroundResource(R.drawable.check_in_btn);
                NavigationBar.employee.setCheckedOut(true);
//                fragment.checkedOut = true;
            } else {
                fragment.checkInOutBtn.setBackgroundResource(R.drawable.check_out_btn);
                NavigationBar.employee.setCheckedOut(false);
//                fragment.checkedOut = false;
            }
        }
    }

    // *********************************************************************************************

    private static class CheckInOutTask extends AsyncTask<Void, Void, Boolean> {

        private DatabaseHandler dbHandler;
        private String username;
        private boolean checkedOut;

        private WeakReference<HomeFragment> fragmentWeakReference;

        // Our AsyncTask constructor
        CheckInOutTask(HomeFragment fragment) {
            this.username = fragment.username;
//            this.checkedOut = fragment.checkedOut;
            this.checkedOut = NavigationBar.employee.isCheckedOut();
            dbHandler = new DatabaseHandler();
            fragmentWeakReference = new WeakReference<>(fragment);
        }


        @Override
        protected Boolean doInBackground(Void... voids) {
            if(dbHandler.openDbConnection(DatabaseHandler.FOR_CHECK_IN_OUT)){
                if(this.checkedOut) {
                    // Employee is checked out so we check him in (inserting new dbHandler row)
                    return dbHandler.checkIn(username);
                } else {
                    // Employee is only checked in so we check him out for the day (updating the last dbHandler row)
                    return dbHandler.checkOut(username);
                }
            }
            // Connection to dbHandler unsuccessful
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            // Closing dbHandler after all operations
            dbHandler.closeDbConnection();

            HomeFragment fragment = fragmentWeakReference.get();
            if(fragment == null || fragment.isRemoving()) {
                return;
            }


            if(result){
                if(checkedOut) {
                    // After we did the check in we set the button for check out
                    fragment.checkInOutBtn.setBackgroundResource(R.drawable.check_out_btn);
                    NavigationBar.employee.setCheckedOut(false);
//                    fragment.checkedOut = false;
                    Toast.makeText(fragment.getContext(), "User Checked In!", Toast.LENGTH_SHORT).show();
                } else {
                    // After we did the check out we set the button for check in
                    fragment.checkInOutBtn.setBackgroundResource(R.drawable.check_in_btn);
                    NavigationBar.employee.setCheckedOut(true);
//                    fragment.checkedOut = true;
                    Toast.makeText(fragment.getContext(), "User Checked Out!", Toast.LENGTH_SHORT).show();
                }
                System.out.println("User CheckInOut Successful! : " + username);
            } else {
                System.out.println("Connection problem : " + username);

            }
        }
    }
}
