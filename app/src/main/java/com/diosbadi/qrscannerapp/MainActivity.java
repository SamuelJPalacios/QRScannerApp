package com.diosbadi.qrscannerapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class MainActivity extends AppCompatActivity {
    private TextView textoResultado;
    private ActivityResultLauncher<ScanOptions> lanzadorEscaneo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Referncias a los elementos del layout
        Button botonEscanear = findViewById(R.id.scanButton);
        textoResultado = findViewById(R.id.resultTextView);

        //Configurara el lanzador de escaneo
        lanzadorEscaneo = registerForActivityResult(new ScanContract(), resultado -> {
            if (resultado.getContents() != null) {
                textoResultado.setText("Resultado: " + resultado.getContents());
            }
        });

        //Configurar el boton par inicial escaneo
        botonEscanear.setOnClickListener(v -> {
            ScanOptions opciones = new ScanOptions();
            opciones.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
            opciones.setPrompt("Escanea un código QR");
            opciones.setCameraId(0); //Usar la cámara trasera
            opciones.setBeepEnabled(true); //Activa el sonido al escanear
            opciones.setBarcodeImageEnabled(true); //Guarda imagen del código escaneado

            lanzadorEscaneo.launch(opciones);
        });
    }
}