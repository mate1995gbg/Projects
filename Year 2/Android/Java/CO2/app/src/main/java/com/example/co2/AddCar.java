package com.example.co2;

import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddCar extends AppCompatActivity {
    private static final String URL = "jdbc:mysql://64.71.156.102:3306/hvmate0025_mintestdatabas?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true"; //URLen till databasen
    private static final String USER = "hvmate0025_user"; //anv. namn till databasen
    private static final String PASSWORD = "Mynameisjeff1"; //lösenord till databasen
    private static final String DRIVER = "com.mysql.jdbc.Driver";

    ArrayList<String> carModelList=new ArrayList<>();
    ArrayList<String> carEngList=new ArrayList<>();
    ArrayList<String> carFuelList=new ArrayList<>();
    ArrayList<String> carMakeList=new ArrayList<>();

    public static String selectedModel;
    public static String selectedFuel;
    public static String selectedEngine;
    public static String selectedManufacturer;
    public static Integer selectedCarEmissions;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        new SqlOnSeparateThread().execute();
        buttonAddToMakeTextView();
        buttonAddToModelTextView();
        buttonAddToFuelTextView();
        buttonAddToEngTextView();
        buttonSaveCar();

    }
    public void TillbakaMain(View backButton)
    {
        Intent intent2 = new Intent(this, MainActivity.class);
        startActivity(intent2);
    }

    private class SqlOnSeparateThread extends AsyncTask<Void,Void,Void> {
        // SQL-query från databas till carMakeList som hämtar strings om tillgängliga biltillverkare
        //ex. Volvo, SAAB osv.
        //klassen implementerar ASyncTask som är en klass med funktion som tillåter ens kod att
        //-köras på en annan tråd i bakgrunden, därav defaultmetoden "doInBackground".
        //ASyncTask behövs för att inte appen skall krascha, då det tar ett tag att hämta info
        //--med hjälp av SQL-queryn.
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName(DRIVER).newInstance();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            try{
                //skapar connection m. hjälp av URL till SQL-server, användarnamn+password.
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                //Queryn som skall köras.
                String sql1= "SELECT MAN_NAME FROM MANUFACTURER";
                //skickar queryn m. hjälp av PreparedStatement.
                PreparedStatement statement1 = connection.prepareStatement(sql1);
                //får tillbaks resultat i resultSet.
                ResultSet resultSet = statement1.executeQuery();
                //"för varje rad av info i resultsettet så matas det in i Arraylisten."
                while (resultSet.next()) {
                    carMakeList.add(resultSet.getString("MAN_NAME"));
                }
                //stänger connection till databasen.
                connection.close();

                //arrayListToManSpinner() måste vara inuti SqlOnSeparateThread och inuti en Runnable -
                // - för att köra på huvudtråden (???) då -
                // - ASyncTask kan skapa problem med att sätta valda värden på addManSpinner
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        arrayListToManSpinner();
                    }
                });
            }
            //för att fånga errors.
            catch(SQLException ex)
            {
                ex.printStackTrace();
            }
            return null;
        }

    }
    private class SqlOnSeparateThread2 extends AsyncTask<Void,Void,Void> {
    //denna class liknar SqlOnSeparateThread men hämtar modellinfo med villkor-
        //beroende på värdet på selectedManufacturer.
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName(DRIVER).newInstance();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            try{
                TextView selectedManTextView = findViewById(R.id.selectedMakeTextView);
                String selectedManufacturer = selectedManTextView.getText().toString();

                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                String sqlString= "SELECT MODEL_NAME FROM MODEL,MANUFACTURER WHERE MAN_NAME='"+selectedManufacturer+"' AND MANUFACTURER.MAN_ID=MODEL.MAN_ID";

                PreparedStatement statement1 = connection.prepareStatement(sqlString);
                ResultSet resultSet = statement1.executeQuery();
                while (resultSet.next()) {
                    carModelList.add(resultSet.getString("MODEL_NAME"));
                }
                connection.close();
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        arrayListToModelSpinner();
                    }
                });

            }
            catch(SQLException ex)
            {
                ex.printStackTrace();
            }
            return null;
        }

    }
    private class SqlOnSeparateThread3 extends AsyncTask<Void,Void,Void>{
        //oanvänd ASyncTask-SQL-klass för att hämta data om bränsletyp. hann ej implementera denna.

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName(DRIVER).newInstance();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            try{
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                String sqlString= "SELECT FUEL_NAME FROM FUELTYPE";
                PreparedStatement statement1 = connection.prepareStatement(sqlString);
                ResultSet resultSet = statement1.executeQuery();
                while (resultSet.next()) {
                    carFuelList.add(resultSet.getString("FUEL_TYPE"));
                }
                connection.close();
            }
            catch(SQLException ex)
            {
                ex.printStackTrace();
            }
            return null;
        }
    }
    private class SqlOnSeparateThread4 extends AsyncTask<Void,Void,Void> {
    //hämtar motoralternativ
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName(DRIVER).newInstance();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            try{
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                //SQL queryn som skall köras.
                String sql4= "SELECT ENG_NAME FROM ENGINE,FUELTYPE,MODEL WHERE FUEL_NAME='"+selectedFuel+"' AND MODEL_NAME='"+selectedModel+"' AND ENGINE.ENG_FUELTYPE=FUELTYPE.FUEL_ID AND MODEL.MODEL_ID=ENGINE.MODEL_ID";
                PreparedStatement statement1 = connection.prepareStatement(sql4);
                ResultSet resultSet = statement1.executeQuery();
                //medan det finns ett resultat från queryn så sätts värdet för "ex. ENG_NAME" in i carEngList.
                while (resultSet.next()) {
                    carEngList.add(resultSet.getString("ENG_NAME"));
                }
                //stänger "connectionen"
                connection.close();

                runOnUiThread(new Runnable() {
                //kör denna kod på "main-tråden" efter att det andra är klart.
                    @Override
                    public void run() {
                        arrayListToEngSpinner();
                    }
                });
            }
            catch(SQLException ex)
            {
                ex.printStackTrace();
            }
            return null;
        }

    }
    private class SqlOnSeparateThread5 extends AsyncTask<Void,Void,Void> {
    //hämtar CO2-värdet för vald bil, modell, bränsletp samt motor.
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName(DRIVER).newInstance();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            try{
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                String sql4= "SELECT ENG_CO2 FROM ENGINE,FUELTYPE,MODEL WHERE MODEL_NAME='"+selectedModel+"' AND FUEL_NAME='"+selectedFuel+"' AND ENG_NAME='"+selectedEngine+"' AND ENGINE.ENG_FUELTYPE=FUELTYPE.FUEL_ID AND MODEL.MODEL_ID=ENGINE.MODEL_ID";
                PreparedStatement statement1 = connection.prepareStatement(sql4);
                ResultSet resultSet = statement1.executeQuery();
                while (resultSet.next()) {
                    selectedCarEmissions=resultSet.getInt("ENG_CO2");

                }
                connection.close();
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        //arrayListToEmissonsSpinner();
                    }
                });
            }
            catch(SQLException ex)
            {
                ex.printStackTrace();
            }
            return null;
        }

    }

    public void buttonAddToMakeTextView(){ //sätter variabeln val till textview samt-
        // -kör nästa SqlOnSeparateTask.-
        //sätter även selectedManufacturer till vald biltillverkare -
        // då den skall visas i MainActivity med.
        Button button = findViewById(R.id.addMaketoTextViewbutton);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Spinner spin = findViewById(R.id.addManSpinner);
                String val = spin.getSelectedItem().toString();
                TextView selectedManTextView = findViewById(R.id.selectedMakeTextView);
                selectedManTextView.setText(val);
                //OBS
                new SqlOnSeparateThread2().execute();
                selectedManufacturer = spin.getSelectedItem().toString();
            }
        });

    }
    public void buttonAddToModelTextView(){ //
        Button button = findViewById(R.id.addModeltoTextViewbutton);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Spinner spin = findViewById(R.id.addModelSpinner);
                String val = spin.getSelectedItem().toString();
                TextView selectedModelTextView = findViewById(R.id.selectedModelTextView);
                selectedModelTextView.setText(val);
                arrayListToFuelSpinner();
                selectedModel=spin.getSelectedItem().toString();
            }
        });

    }
    public void buttonAddToFuelTextView(){ //
        Button button = findViewById(R.id.addFueltoTextViewbutton);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Spinner spin = findViewById(R.id.addFuelSpinner);
                String val = spin.getSelectedItem().toString();
                TextView selectedModelTextView = findViewById(R.id.selectedFuelTextView);
                selectedModelTextView.setText(val);
                new SqlOnSeparateThread4().execute();
                arrayListToEngSpinner();
                selectedFuel=spin.getSelectedItem().toString();

            }
        });

    }
    public void buttonAddToEngTextView(){ //
        Button button = findViewById(R.id.addEngtoTextViewbutton);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Spinner spin = findViewById(R.id.addEngSpinner);
                String val = spin.getSelectedItem().toString();
                TextView selectedEngTextView = findViewById(R.id.selectedEngineTextView);
                selectedEngTextView.setText(val);
                new SqlOnSeparateThread4().execute();
                arrayListToEngSpinner();
                selectedEngine=spin.getSelectedItem().toString();

            }
        });

    }
    public void buttonSaveCar(){
        //hämtar CO2-värdet från remote SQL-DB och sätter in i Integern selectedCarEmissions.
        //selectedCarEmissons och selectedManufacturer+selectedModel visas sedan i MainActivity
        //-som info om vald bil och utsläppsinformation
        Button button = findViewById(R.id.saveCarSpecsButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SqlOnSeparateThread5().execute();
            }
        });
    }

    public void arrayListToManSpinner(){
        //Lägger till värden från carMakeList till addManSpinner
        Spinner makeSpin = findViewById(R.id.addManSpinner);
        ArrayAdapter<String> adapter12 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, carMakeList);
        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        makeSpin.setAdapter(adapter12); //för Manufacturer-spinner
    }
    public void arrayListToModelSpinner(){
        //Lägger till värden från carModelList till addModelSpinner
        Spinner modelSpin = findViewById(R.id.addModelSpinner);
        ArrayAdapter<String> modelAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, carModelList);
        modelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modelSpin.setAdapter(modelAdapter); //för Manufacturer-spinner
    }
    public void arrayListToFuelSpinner(){
        //lägger till bränslen i FuelSpinner
        carFuelList.add("Bensin"); //TEMP - LÄGG TILL FRÅN DB SENARE
        carFuelList.add("Diesel"); //TEMP - LÄGG TILL FRÅN DB SENARE
        Spinner fuelSpin = findViewById(R.id.addFuelSpinner);
        ArrayAdapter<String> fuelAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, carFuelList);
        fuelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fuelSpin.setAdapter(fuelAdapter);

    }
    public void arrayListToEngSpinner(){
        Spinner engSpin = findViewById(R.id.addEngSpinner);
        ArrayAdapter<String> engAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, carEngList);
        engAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        engSpin.setAdapter(engAdapter);
    }

}

