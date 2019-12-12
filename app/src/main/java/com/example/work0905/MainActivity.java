package com.example.work0905;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.work0905.model.Employee;
import com.example.work0905.util.DatabaseHandler;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity implements TextWatcher, CompoundButton.OnCheckedChangeListener {
    private EditText username, password;
    private ImageButton loginBtn;
    private CheckBox rem_userpass;
    private TextView error_message;
    private ProgressBar progressBar;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Employee employee;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_REMEBER = "remeber";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        employee = new Employee();
        username = findViewById(R.id.username_et);
        password = findViewById(R.id.pass_et);
        rem_userpass = findViewById(R.id.save_login_cb);
        error_message = findViewById(R.id.errorMsg_tv);
        progressBar = findViewById(R.id.progressBar);

        if (sharedPreferences.getBoolean(KEY_REMEBER,false)){
            rem_userpass.setChecked(true);
        } else {
            rem_userpass.setChecked(false);
        }

        username.setText(sharedPreferences.getString(KEY_USERNAME,""));
        password.setText(sharedPreferences.getString(KEY_PASSWORD, ""));
        error_message.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.GONE);

        username.addTextChangedListener(this);
        password.addTextChangedListener(this);
        rem_userpass.setOnCheckedChangeListener(this);


        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LogInTask logInTask = new LogInTask(MainActivity.this);
                logInTask.execute(username.getText().toString().trim(), password.getText().toString().trim());
//                checkInput();
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        managePrefs();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        managePrefs();
    }

    private void managePrefs(){
        if (rem_userpass.isChecked()){
            editor.putString(KEY_USERNAME, username.getText().toString().trim());
            editor.putString(KEY_PASSWORD, password.getText().toString().trim());
            editor.putBoolean(KEY_REMEBER, true);
            editor.apply();
        } else {
            editor.putBoolean(KEY_REMEBER, false);
            editor.remove(KEY_PASSWORD);
            editor.remove(KEY_USERNAME);
            editor.apply();
        }
    }


    /**
     * AsyncTask for handling the connection to the database and login of the user.
     * Used to avoid the networking error thrown by the UI thread.
     * (UI thread is not able to handle network operations and such assignment has to handed in a separate task-thread)
     **/
    private static class LogInTask extends AsyncTask<String, Void, Boolean> {

        private DatabaseHandler dbHandler;
        private String uname;
        private Employee employee;

        private WeakReference<MainActivity> activityWeakReference;

        // Our AsyncTask constructor
        LogInTask(MainActivity activity) {
            activityWeakReference = new WeakReference<MainActivity>(activity);
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
            MainActivity activity = activityWeakReference.get();
            if(activity == null || activity.isFinishing()) {
                return;
            }
            // Making a STRONG reference to MainActivity's progressBar View element
            activity.progressBar.setVisibility(View.VISIBLE);
            employee = activity.employee;
            dbHandler = new DatabaseHandler();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            if(dbHandler.openDbConnection(DatabaseHandler.FOR_LOG_IN) &&
                    dbHandler.userAuthentication(strings[0], strings[1], employee)){
                uname = strings[0];
                // Connection to db successful
                return true;
            }
            // Connection to db unsuccessful
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            // Closing db after all operations
            dbHandler.closeDbConnection();

            MainActivity activity = activityWeakReference.get();
            if(activity == null || activity.isFinishing()) {
                return;
            }

            activity.progressBar.setVisibility(View.GONE);

            if(result){
                Toast.makeText(activity.getApplicationContext(), "Logged In as\n\n" + uname,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(activity, NavigationBar.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("Employee", employee);
                activity.startActivity(intent);
            } else {
                activity.error_message.setTextColor(Color.RED);
                activity.error_message.setText("# Something went wrong!\nPlease try again");
                activity.error_message.setVisibility(View.VISIBLE);
            }
        }


    }

}
