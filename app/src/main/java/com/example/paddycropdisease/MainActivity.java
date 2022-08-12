package com.example.paddycropdisease;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paddycropdisease.ml.PaddyCropNew98Epochs10;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;

public class MainActivity extends AppCompatActivity {

    private ImageView imgView;
    private TextView result;
    private Bitmap bitmap;
    String solution = "0";
    ConstraintLayout mainActivity;
    String backgroundImageName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgView = findViewById(R.id.img);
        result = findViewById(R.id.tvResult);
        ImageView btnSelect = findViewById(R.id.btnSelect);
        ImageView btnOpenCamera = findViewById(R.id.btnOpenCamera);
        Button btnPredict = findViewById(R.id.btnPredict);
        Button btnViewSolution = findViewById(R.id.btnViewSolution);
        FloatingActionButton btnReset = findViewById(R.id.btnReset);
        mainActivity = findViewById(R.id.mainActivity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//        Open Camera
        btnOpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgView.setTag("notCamera");
                result.setText("");
                if(ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.CAMERA},101);
                }

                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent1,101);
                intent1.setType("image/*");
            }
        });



        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgView.setTag("notCamera");
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 100);
                result.setText("");
                solution = "-1";
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.setText("");
                imgView.setImageResource(R.drawable.paddy2_2);
                imgView.setTag("bg");
                solution = "0";
            }
        });

        btnPredict.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                backgroundImageName = String.valueOf(imgView.getTag());
                if (bitmap == null || backgroundImageName.equals("bg")) {
                    predictSnackbar();
                } else {
                    bitmap = Bitmap.createScaledBitmap(bitmap, 256, 256, true);

                    try {
//                        Model model = Model.newInstance(getApplicationContext());
                        PaddyCropNew98Epochs10 model = PaddyCropNew98Epochs10.newInstance(getApplicationContext());

                        // Creates inputs for reference.
                        TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 256, 256, 3}, DataType.FLOAT32);

                        TensorImage tensorImage = new TensorImage(DataType.FLOAT32);
                        tensorImage.load(bitmap);
                        ByteBuffer byteBuffer = tensorImage.getBuffer();
                        inputFeature0.loadBuffer(byteBuffer);


                        // Runs model inference and gets result.
                        PaddyCropNew98Epochs10.Outputs outputs = model.process(inputFeature0);
                        TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                        // Releases model resources if no longer used.
                        model.close();

                        float max = outputFeature0.getFloatArray()[0];
                        int index = 0;
                        for (int i = 0; i < outputFeature0.getFloatArray().length; i++) {
                            if (max < outputFeature0.getFloatArray()[i]) {
                                max = outputFeature0.getFloatArray()[i];
                                index = i;
                            }
                        }


                        String res = "";
                        if (index == 0) {
                            res = "Bacterial Blight";
                            solution = "1";
                        } else if (index == 1) {
                            res = "Blast";
                            solution = "2";
                        } else if (index == 2) {
                            res = "BrownSpot";
                            solution = "3";
                        } else if (index == 3) {
                            res = "Tungro";
                            solution = "4";
                        }


//                        result.setText("Bacterial Blight = " + outputFeature0.getFloatArray()[0] + "\n" +
//                                "Blast = " + outputFeature0.getFloatArray()[1] + "\n" +
//                                "BrownSpot = " + outputFeature0.getFloatArray()[2] + "\n" +
//                                "Tungro = " + outputFeature0.getFloatArray()[3] + "\n" +
//                                "Result = " + res);
                        result.setText(res);
                    } catch (IOException e) {
                        // TODO Handle the exception
                    }


                }
            }
        });

        btnViewSolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewSolution(solution);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent aboutIntent = new Intent(this, AboutSceen.class);
        switch (item.getItemId()){
            case R.id.about: startActivity(aboutIntent);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            Uri uri = data.getData();
            imgView.setImageURI(uri);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == 101) {
            Uri uri = data.getData();
            Bitmap bitmap1 = (Bitmap) data.getExtras().get("data");
            imgView.setImageURI(uri);
            imgView.setImageBitmap(bitmap1);
            bitmap = bitmap1;
        }
    }

    public void viewSolution(String res) {
        Intent i = new Intent(this, SolutionsActivity.class);

        switch (res) {
            case "1":
            case "2":
            case "3":
            case "4":
                i.putExtra("result", solution);
                startActivity(i);
                break;
            case "-1": {
                Snackbar.make(mainActivity, "Predict First", Snackbar.LENGTH_SHORT).show();
                break;
            }
            case "0": {
                Snackbar.make(mainActivity, "Identify a disease to view solution", Snackbar.LENGTH_SHORT).show();
                break;
            }
        }

    }

    public void predictSnackbar() {
        Snackbar.make(mainActivity, "Select an image to Predict", Snackbar.LENGTH_SHORT).show();
    }
}