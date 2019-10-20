package com.song4me;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.help_me_decide_btn)
    Button helpMeDecideBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button1)
    public void onClick() {
        Toast.makeText(this, "Button Clicked!", Toast.LENGTH_LONG).show();

        startActivity(new Intent(this, HelpMeDecideActivity.class));

    }

    @OnClick(R.id.help_me_decide_btn)
    public void onClick_helpMe() {

        startActivity(new Intent(this, PickMyMoodActivity.class));
    }
}
