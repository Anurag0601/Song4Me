package com.song4me;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.camerakit.CameraKitView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PickMyMoodActivity extends AppCompatActivity {


    @BindView(R.id.fab_capture)
    FloatingActionButton fabCapture;
    @BindView(R.id.cameraKitView)
    CameraKitView cameraKitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_my_mood);
        ButterKnife.bind(this);
        
    }

    @Override
    protected void onStart() {
        super.onStart();
        
        cameraKitView.onStart();
    }

    @OnClick(R.id.fab_capture)
    public void onClick() {
        cameraKitView.captureImage(new CameraKitView.ImageCallback() {
            @Override
            public void onImage(CameraKitView cameraKitView, byte[] bytes) {
                if (bytes==null) {
                    Toast.makeText(PickMyMoodActivity.this, "Failed to capture", Toast.LENGTH_SHORT).show();
                }
                
                else {
                    Toast.makeText(PickMyMoodActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
