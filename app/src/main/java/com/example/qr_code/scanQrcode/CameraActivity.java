package com.example.qr_code.scanQrcode;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qr_code.R;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.Size;

import java.util.regex.Pattern;

public class CameraActivity extends AppCompatActivity {
    private Button btResult;
    private DecoratedBarcodeView barcodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr_code);
        scanCode();
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
                        Toast.makeText(CameraActivity.this, saveText, Toast.LENGTH_SHORT).show();
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
                    function = 3;
                }
                btResult.setEnabled(true);
                barcodeView.pause();
            }
        }

    };
}
