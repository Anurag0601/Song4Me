package com.song4me;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.camerakit.CameraKitView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PickMyMoodActivity extends AppCompatActivity {


    @BindView(R.id.fab_capture)
    FloatingActionButton fabCapture;
    @BindView(R.id.cameraKitView)
    CameraKitView cameraKitView;
    private String TAG = "PickMyMood";

    double smilingProbability, leftEyeOpenProbability, rightEyeOpenProbability;

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

                    Bitmap imageBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    FirebaseVisionImageMetadata metadata = new FirebaseVisionImageMetadata.Builder()
                            .setWidth(1000)   // 480x360 is typically sufficient for
                            .setHeight(1400)  // image recognition
                            .setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21)
                            .build();

                    FirebaseVisionFaceDetectorOptions options =
                            new FirebaseVisionFaceDetectorOptions.Builder()
                                    .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                                    .enableTracking()
                                    .build();

                    FirebaseVisionFaceDetector detector = FirebaseVision.getInstance().getVisionFaceDetector(options);

                    FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(imageBitmap);

                    Task<List<FirebaseVisionFace>> result = detector.detectInImage(firebaseVisionImage)
                            .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
                                @Override
                                public void onSuccess(List<FirebaseVisionFace> firebaseVisionFaces) {
                                    for (FirebaseVisionFace face : firebaseVisionFaces) {
                                        Log.d(TAG, "**********");
                                        Log.d(TAG, "face ["+face+"]");
                                        Log.d(TAG, "Smiling Prob ["+face.getSmilingProbability()+"]");
                                        Log.d(TAG, "Left eye open ["+face.getLeftEyeOpenProbability()+"]");
                                        Log.d(TAG, "Right eye open ["+face.getRightEyeOpenProbability()+"]");

                                        smilingProbability = face.getSmilingProbability();
                                        leftEyeOpenProbability = face.getLeftEyeOpenProbability();
                                        rightEyeOpenProbability = face.getRightEyeOpenProbability();

                                        BigDecimal b1 = new BigDecimal(smilingProbability);
                                        MathContext m1 = new MathContext(4);

                                        BigDecimal b2 = b1.round(m1);

                                        Log.d(TAG, "onSuccess: BIG DECIMAL : " + b2.floatValue());
                                        if (b2.doubleValue() > 0.7000) {
                                            Toast.makeText(PickMyMoodActivity.this, "HAPPY AF!", Toast.LENGTH_SHORT).show();
                                        }

                                        if (b2.floatValue() > 0.2000 && b2.floatValue() < 0.7000) {
                                            Toast.makeText(PickMyMoodActivity.this, "Semma sad!", Toast.LENGTH_SHORT).show();
                                        }

                                        finish();

                                    }
                                }
                            });
                }
            }
        });
    }
}
