package com.example.qrcodesample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    final int RequestPermissionCode = 100;
    Boolean isGrandCameraPermission = false;
    Button btnOpenScanner;
    TextView tvQrcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();
        initLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 101 && data != null){
            String qrCodeContent = data.getStringExtra("resultData");
            Log.i("onActivityResult", "qrCodeIntent: " + qrCodeContent);
            tvQrcode.setText(qrCodeContent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == RequestPermissionCode){
            if(grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "您拒絕了權限 無法開啟相機掃描QrCode", Toast.LENGTH_LONG).show();
            } else {
                isGrandCameraPermission = true;
            }
        }
    }

    private void checkPermission() {
        int resultCode = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if(resultCode != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, RequestPermissionCode);
        } else {
            isGrandCameraPermission = true;
        }
    }

    private void initLayout() {
        btnOpenScanner = findViewById(R.id.btn_openscanner);
        tvQrcode = findViewById(R.id.tv_qrcode);

        btnOpenScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isGrandCameraPermission){
                    Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
                    startActivityForResult(intent, 101);
                } else {
                    Toast.makeText(MainActivity.this, "您拒絕了權限 無法開啟相機掃描QrCode", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}