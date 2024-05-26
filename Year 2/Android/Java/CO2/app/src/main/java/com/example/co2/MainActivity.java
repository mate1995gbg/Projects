package com.example.co2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initCarTextView();
        initCarCo2Values(); //bil co2 string metod

    }
    public void openAddCar(View button1)
    {
        Intent intent1 = new Intent(this, AddCar.class);
        startActivity(intent1);

    }
    public void openPlanResa(View Map_button){
        Intent intent1 = new Intent(this, PlanActivity.class);
        startActivity(intent1);
    }
    public void initCarTextView(){ //hämtar alltid en string med vald bilmodell som den får från SQL-query
        try {
            TextView currentCarText = findViewById(R.id.currentCarTextView);
            currentCarText.setText(AddCar.selectedManufacturer +" " + AddCar.selectedModel);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void initCarCo2Values(){ //hämtar alltid stringen AddCar.selectedCarEmisons som den får från SQL-query
        try{
            TextView co2ValueText = findViewById(R.id.co2ValueTextView);
            co2ValueText.setText(AddCar.selectedCarEmissions.toString());
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}