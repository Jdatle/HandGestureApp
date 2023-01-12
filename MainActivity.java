package com.example.johnssmarthomeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Construct Variables
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set Button
        Button btnLearn = findViewById(R.id.btnLearn);
        btnLearn.setOnClickListener(this);

        Initialize();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLearn:
                LearnGesture();
                break;
            default:
                break;
        }
    }

    private void Initialize(){
        spinner = findViewById(R.id.spinner);
    }

    private void LearnGesture() {
        String action = spinner.getSelectedItem().toString();
        String actionID = "Unknown";
        actionID = GestureID(action, actionID);
        //Toast.makeText(MainActivity.this, action+" "+actionID, Toast.LENGTH_SHORT).show();

        // To Next Screen
        Intent myIntent = new Intent(MainActivity.this, MainActivity2.class);
        myIntent.putExtra("actionID",actionID);
        myIntent.putExtra("action",action);
        startActivity(myIntent);
    }

    private String GestureID(String action, String actionID) {
        Map<String, String> actionPair = new TreeMap<String, String>();
        actionPair.put("Turn On Light","LightOn");
        actionPair.put("Turn Off Light","LightOff");
        actionPair.put("Turn On Fan","FanOn");
        actionPair.put("Turn Off Fan","FanOff");
        actionPair.put("Increase Fan Speed","FanUp");
        actionPair.put("Decrease Fan Speed","FanDown");
        actionPair.put("Set Thermostat to specified temperature","SetThermo");
        actionPair.put("0","Num0");
        actionPair.put("1","Num1");
        actionPair.put("2","Num2");
        actionPair.put("3","Num3");
        actionPair.put("4","Num4");
        actionPair.put("5","Num5");
        actionPair.put("6","Num6");
        actionPair.put("7","Num7");
        actionPair.put("8","Num8");
        actionPair.put("9","Num9");

        actionID = actionPair.get(action);
        return actionID;
    }

}