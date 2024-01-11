package com.example.qrcodesample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends AppCompatActivity {

    ZXingScannerView scannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        initLayout();
    }

    private void initLayout() {
        scannerView = findViewById(R.id.scanner_view);
        scannerView.setResultHandler(new ZXingScannerView.ResultHandler() {
            @Override
            public void handleResult(Result rawResult) {
                Log.i("handleResult", "qrcode: " + rawResult.getText());
                scannerView.stopCamera();
                scannerView.stopCameraPreview();


                Intent getQrcodeIntent = new Intent();
                getQrcodeIntent.putExtra("resultData", rawResult.getText());
                setResult(RESULT_OK, getQrcodeIntent);

                finish();
            }
        });

        scannerView.startCamera();
    }
}