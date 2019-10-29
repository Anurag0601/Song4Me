package com.song4me;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.pickMood_button)
    Button pickMood_button;
    @BindView(R.id.help_me_decide_btn)
    Button helpMeDecideBtn;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    FirebaseAuth mAuth;

    ProgressDialog progressDialog;
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.pickMood_button)
    public void onClick() {
        Toast.makeText(this, "Button Clicked!", Toast.LENGTH_LONG).show();

        startActivity(new Intent(this, HelpMeDecideActivity.class));

    }

    @OnClick(R.id.help_me_decide_btn)
    public void onClick_helpMe() {

        startActivity(new Intent(this, PickMyMoodActivity.class));
    }


    @Override
    public void onBackPressed() {

        finishAffinity();
    }

    private void about() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Ya'll know who it is!");
        alert.show();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch (id) {
            case R.id.logout_item:
                logout();
                break;

            case R.id.about_item:
                about();
                break;


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_items, menu);

        return super.onCreateOptionsMenu(menu);

    }


    private void logout() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Logout?");
        alertDialogBuilder.setMessage("Are you sure you want to log out of Boltraz?");
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                Log.d(TAG, "onAuthStateChanged: " + mAuth.getCurrentUser().getDisplayName() + " is signed out");
                progressDialog.setTitle("Sign out");
                progressDialog.setMessage("Signing you out");
                progressDialog.show();
                Toast.makeText(getApplicationContext(), "Signing you out", Toast.LENGTH_SHORT).show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        FirebaseAuth.getInstance().signOut();

                        Intent intent = new Intent(MainActivity.this, Loginactivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }, 3000);


            }
        });

        alertDialogBuilder.show();

    }
}

