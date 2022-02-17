package com.example.convertidor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    final String[] datos = new String[]{"DOLAR","REAL","PESO URUGUAYO"};

    private Spinner monedaActualSP;
    private Spinner monedaCambioSP;
    private EditText valorCambioET;
    private TextView resultadoTV;

    final private double factorDolarReal = 5.17;
    final private double factorPesoDolar = 0.023;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,datos);
        monedaActualSP = (Spinner) findViewById(R.id.monedaActualSP);
        monedaActualSP.setAdapter(adaptador);
    }
    public void clickConvertir(View v){
        monedaActualSP = (Spinner) findViewById(R.id.monedaActualSP);
        monedaCambioSP = (Spinner) findViewById(R.id.monedaCambioSP);
        valorCambioET = (EditText) findViewById(R.id.valorCambioET);
        resultadoTV = (TextView) findViewById(R.id.resultadoTV);

        String monedaActual = monedaActualSP.getSelectedItem().toString();
        String monedaCambio = monedaCambioSP.getSelectedItem().toString();

        double valorCambio = Double.parseDouble(valorCambioET.getText().toString());

        double resultado = procesarConversion(monedaActual,monedaCambio,valorCambio);

        if (resultado>0){
            resultadoTV.setText(String.format("Por %5.2f %s, usted recibira %5.2f %s",valorCambio,monedaActual,resultado,monedaCambio));
            valorCambioET.setText("");
        }else{
            resultadoTV.setText(String.format("Usted recibira"));
            Toast.makeText(MainActivity.this, "las opciones no tienen factor de conversion", Toast.LENGTH_SHORT).show();
        }
}

    private double procesarConversion(String monedaActual, String monedaCambio, double valorCambio) {
        double resultadoConversion = 0;
        switch (monedaActual){
            case "DOLAR":
                if (monedaCambio.equals("REAL")){
                    resultadoConversion = valorCambio * factorDolarReal;
                }
                if (monedaCambio.equals("PESO URUGUAYO")){
                    resultadoConversion = valorCambio / factorPesoDolar;
                }
                break;
            case "REAL":
                if (monedaCambio.equals("DOLAR")){
                    resultadoConversion = valorCambio / factorDolarReal;
                }
                break;
            case "PESO URUGUAYO":
                if (monedaCambio.equals("DOLAR")){
                    resultadoConversion = valorCambio * factorPesoDolar;
                }
                break;
        }
        return resultadoConversion;
    }
}