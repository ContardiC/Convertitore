package it.spacecoding.convertitore;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText leftEditText;
    EditText rightEditText;
    Spinner categorySpinner;
    Spinner leftSpinner;
    Spinner rightSpinner;
    String leftChoice, rightChoice;
    String leftText,rightText;
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

        categorySpinner = findViewById(R.id.spinnerCategory);
        leftSpinner = findViewById(R.id.spinnerLeft);
        rightSpinner = findViewById(R.id.spinnerRight);
        leftEditText = findViewById(R.id.editTextLeft);
        rightEditText = findViewById(R.id.editTextRight);
        // Init Data Setup
        leftChoice = "kilogram";
        rightChoice = "kilogram";
        leftText = "1";
        rightText = "1";
        // EditText setup
        leftEditText.setText(leftText);
        rightEditText.setText(rightText);

        // Dati
        String[] physicalQuantities = {"Mass","Time"};
        String[] mass = {"kilogram","grams","pounds"};
        String[] time = {"hours","minutes","seconds"};
        // Array Adapter
        ArrayAdapter<String> categoryArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,physicalQuantities);
        ArrayAdapter<String> massArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,mass);
        ArrayAdapter<String> timeArrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,time);

        // Layout da usare quando la lista appare
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        massArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applica l'Adapter allo spinner
        categorySpinner.setAdapter(categoryArrayAdapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = parent.getItemAtPosition(position).toString();
                Log.d("MainActivity","Hai selezionato: " + selectedCategory);
                // In base alla categoria selezionata cambio l'ArrayAdapter
                if(selectedCategory.equals("Mass")){
                   leftSpinner.setAdapter(massArrayAdapter);
                   rightSpinner.setAdapter(massArrayAdapter);
                }else if(selectedCategory.equals("Time")){
                    leftSpinner.setAdapter(timeArrayAdapter);
                    rightSpinner.setAdapter(timeArrayAdapter);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        leftSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                leftChoice = adapterView.getItemAtPosition(i).toString();
                String leftText = leftEditText.getText().toString();
                Double leftValue = Double.parseDouble(leftText);
                Double res = conversion(leftValue,leftChoice,rightChoice);
                rightEditText.setText(String.valueOf(res));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        rightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                rightChoice = adapterView.getItemAtPosition(i).toString();
                String rightText = rightEditText.getText().toString();
                Double rightValue = Double.parseDouble(rightText);
                Double res = conversion(rightValue,rightChoice,leftChoice);
                leftEditText.setText(String.valueOf(res));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        leftEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                leftText = editable.toString();
                Double leftValue = Double.parseDouble(leftText);
                Double res = conversion(leftValue,leftChoice,rightChoice);
                rightEditText.setText(String.valueOf(res));
            }
        });
        rightEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                rightText = editable.toString();
            }
        });

    }
    public Double conversion(Double value, String from, String to){
        Double res = (double) 0;
        switch (from){
            case "kilogram":
                switch(to){
                    case "kilogram":
                        res = value;
                        break;
                    case "grams":
                        res = value * 1000;
                        break;
                    case "pounds":
                        res = value * 2.20462;
                        break;
                }
                break;
            case "grams":
                switch(to){
                    case "kilogram":
                        res = value/1000;
                        break;
                    case "grams":
                        res = value;
                        break;
                    case "pounds":
                        res = value/453.592;
                        break;
                }
                break;
            case "pounds":
                switch(to){
                    case "kilogram":
                        res = value * 0.543592;
                        break;
                    case "grams":
                        res = value * 453.592;
                        break;
                    case "pounds":
                        res = value;
                        break;
                }
                break;
            default:
                // error?
        }
        return res;
    }

}