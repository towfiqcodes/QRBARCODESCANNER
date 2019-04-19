package com.eomsbd.qrbarcodescanner;

import android.app.AlertDialog;
import android.app.UiAutomation;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
    private Button clickButton;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clickButton = findViewById(R.id.click_btn);
        textView = findViewById(R.id.textView);
        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQRScanner();
            }
        });
    }


    public void startQRScanner() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        // intentIntegrator.setDesiredBarcodeFormats( IntentIntegrator.ONE_D_CODE_TYPES );
        intentIntegrator.setCaptureActivity(CameraCaptureActivity.class);
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setPrompt("scan");
        intentIntegrator.initiateScan();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
          textView.setText(result.getContents());
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder( MainActivity.this ).create();
            alertDialog.setTitle( "Alert" );
            alertDialog.setMessage( "QR Or Barcode does not match" );
            alertDialog.setButton( AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            } );
            alertDialog.show();
        }
    }
}
