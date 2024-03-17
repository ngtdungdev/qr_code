package com.example.qr_code.qrcode;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qr_code.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRCodeActivity extends AppCompatActivity {
    private EditText textView;
    private Button btnTextQrCode;
    private ImageView imageViewQRCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        textView = findViewById(R.id.urlText);
        btnTextQrCode = findViewById(R.id.btnQrcode);
        imageViewQRCode = findViewById(R.id.imageQrcode);
        btnTextQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QRCodeWriter writer = new QRCodeWriter();
                BitMatrix bitMatrix = null;
                try {
                    bitMatrix = writer.encode( textView.getText().toString(), BarcodeFormat.QR_CODE, 240, 260);
                } catch (WriterException e) {
                    throw new RuntimeException(e);
                }
                int width = bitMatrix.getWidth();
                int height = bitMatrix.getHeight();
                Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        bmp.setPixel(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFF00BFFF);
                    }
                }
                imageViewQRCode.setImageBitmap(bmp);
            }
        });
    }

}
