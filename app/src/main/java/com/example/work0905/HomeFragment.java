package com.example.work0905;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.work0905.util.DatabaseHandler;

import java.lang.ref.WeakReference;

public class HomeFragment extends Fragment {

    private ImageButton checkInOut;
    private static String username;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        username = NavigationBar.username;

        checkInOut = view.findViewById(R.id.checkInOut_imb);
        checkInOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("\n\n" + username + "\n\n");
            }
        });
        return view;
    }


    /**
     * AsyncTask for handling the connection to the database and login of the user.
     * Used to avoid the networking error thrown by the UI thread.
     * (UI thread is not able to handle network operations and such assignment has to handed in a separate task-thread)
     **/
    private static class CheckInOutTask extends AsyncTask<Void, Void, Boolean> {

        private DatabaseHandler dbHandler = new DatabaseHandler();
        private String uname;
        private String pass;

        private WeakReference<HomeFragment> fragmentWeakReference;

        // Our AsyncTask constructor
        CheckInOutTask(HomeFragment activity) {
            fragmentWeakReference = new WeakReference<HomeFragment>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Checking if activity still exists before make any changes in it's UI thread.
            // If does not exist or is going to get destroyed, there is no point to update any of its Views.
            /*
            Note that our Activity can be gone while doInBackground() is in progress
            (so the reference returned can become null), but by using WeakReference we do not
            prevent Garbage Collector from collecting it (and leaking memory) and as Activity
            is gone, it's usually pointless to even try to update its state (still, depending
            on your logic you may want to do something like changing internal state or update DB,
            but touching UI must be skipped).
            */
            HomeFragment fragment = fragmentWeakReference.get();
            if(fragment == null || fragment.isRemoving()) {
                return;
            }
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            if(dbHandler.openDbConnection(DatabaseHandler.FOR_CHECK_IN_OUT) && dbHandler.checkInOut(username)){
                dbHandler.closeDbConnection();
                // Connection to db successful
                return true;
            }
            dbHandler.closeDbConnection();
            // Connection to db unsuccessful
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            HomeFragment fragment = fragmentWeakReference.get();
            if(fragment == null || fragment.isRemoving()) {
                return;
            }


            if(result){
//                Toast.makeText(NavigationBar.getApplicationContext(), "Logged In as\n\n" + uname,Toast.LENGTH_LONG).show();
            } else {

            }
        }


    }
}
