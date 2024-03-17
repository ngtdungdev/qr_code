package com.example.qr_code.scanQrcode;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.qr_code.R;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.Size;

import java.util.regex.Pattern;

public class ScanQrcodeActivity extends AppCompatActivity {
    private Button btResult;
    private DecoratedBarcodeView barcodeView;
    private String[] permissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };
    private static final int REQUEST_PERMISSIONS_CODE = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr_code);
        if (checkPermissions()) {
            scanCode();
        }
    }
    private void scanCode()
    {
        barcodeView = findViewById(R.id.barcode_scanner);
        btResult = findViewById(R.id.btResult);
        barcodeView.decodeContinuous(callback);
        barcodeView.getBarcodeView().setFramingRectSize(new Size(720,1300));
        barcodeView.setStatusText("");
        barcodeView.getViewFinder().setVisibility(View.INVISIBLE);
        barcodeView.resume();
        btResult.setEnabled(false);
        btResult.setText("Scan a QR Code");
        btResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (function) {
                    case 1 : {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(saveText));
                        startActivity(intent);
                    } break;
                    default: {
                        Toast.makeText(ScanQrcodeActivity.this, saveText, Toast.LENGTH_SHORT).show();
                        btResult.setEnabled(false);
                    }
                }
                finish();
            }
        });

    }
    private String saveText;
    private int function = 0;
    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if (result.getText() != null) {
                saveText = result.getText();
                if (Pattern.compile("(https?://)?(www\\.)?[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,})(:[0-9]+)?(/\\S*)?").matcher(saveText).find()) {
                    btResult.setText("ADD CONTENT TO THE URL");
                    function = 1;
                }
                btResult.setEnabled(true);
                barcodeView.pause();
            }
        }

    };

    private int currentPermissionIndex = 0;
    private boolean checkPermissions() {
        if (currentPermissionIndex < permissions.length) {
            String permission = permissions[currentPermissionIndex];
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{permission}, REQUEST_PERMISSIONS_CODE);
            }
            else {
                currentPermissionIndex++;
                checkPermissions();
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                currentPermissionIndex++;
                checkPermissions();
            } else {
                finish();
            }
        }
    }
}
