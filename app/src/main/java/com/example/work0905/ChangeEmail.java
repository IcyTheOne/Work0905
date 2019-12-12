package com.example.work0905;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.work0905.model.Employee;
import com.example.work0905.util.DatabaseHandler;

import java.lang.ref.WeakReference;

public class ChangeEmail extends AppCompatActivity {

    public static final String TAG = "ChangeEmail";

    ImageButton backBtn;
    ImageButton saveChangesBtn;
    EditText mEdit, mEdit2;

    Employee employee;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);

        mEdit = findViewById(R.id.new_email_et);
        mEdit2 = findViewById(R.id.repeat_email_ET);

        employee = (Employee) getIntent().getSerializableExtra("Employee");

        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStackImmediate();
                } else {
                    finish();
                }
            }
        });

        saveChangesBtn = findViewById(R.id.change_email);
        saveChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mEdit2Text = mEdit2.getText().toString().trim();
                String mEditText = mEdit.getText().toString().trim();
                if(mEdit2Text.equals(mEditText)){
                    ChangeEmailTask changeEmailTask = new ChangeEmailTask(ChangeEmail.this);
                    changeEmailTask.execute(mEdit2Text);
                }else{
                    Toast.makeText(ChangeEmail.this,"E-mails not matching",Toast.LENGTH_LONG).show();
                }
                // Prepared statement update



            }
        });
    }

    static class ChangeEmailTask extends AsyncTask<String, Void, Boolean> {
        private WeakReference<ChangeEmail> activityWeakReference;
        Employee employee;
        DatabaseHandler db;


        ChangeEmailTask(ChangeEmail activity){
            activityWeakReference = new WeakReference<ChangeEmail>(activity);
            db = new DatabaseHandler();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            ChangeEmail activity = activityWeakReference.get();
            if(activity == null || activity.isFinishing()) {
                return;
            }

            this.employee = activity.employee;
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            if(db.openDbConnection(DatabaseHandler.FOR_UPDATE_EMAIL) && db.changeEmail(strings[0], this.employee.getId())){
                Log.d(TAG, "doInBackground: " + this.employee.getId() + " : " + strings[0]);
                return true;
            }
            return false;

        }


        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            ChangeEmail activity = activityWeakReference.get();
            if(activity == null || activity.isFinishing()) {
                return;
            }
            if(result){
                Toast.makeText(activity.getApplicationContext(),"Updating email successful",Toast.LENGTH_LONG).show();
            }else Toast.makeText(activity.getApplicationContext(),"Updating email unsuccessful",Toast.LENGTH_LONG).show();

        }
    }
}
