package com.example.qr_code;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qr_code.qrcode.QRCodeActivity;
import com.example.qr_code.scanQrcode.ScanQrcodeActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnQrcode;
    private Button btnScanQrcode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnQrcode = findViewById(R.id.btnQrcode);
        btnScanQrcode = findViewById(R.id.btnScanQrcode);
        btnQrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, QRCodeActivity.class);
                startActivity(intent);
            }
        });
        btnScanQrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ScanQrcodeActivity.class);
                startActivity(intent);
            }
        });

    }
}
