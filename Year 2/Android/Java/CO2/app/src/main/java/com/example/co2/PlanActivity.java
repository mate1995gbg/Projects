package com.example.co2;

//import static androidx.constraintlayout.motion.widget.Debug.getLocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.Manifest;
import android.view.inputmethod.InputMethodManager;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class PlanActivity extends AppCompatActivity {

    public static double latitude;
    public static double longtitude;
    public static String countryName;
    public static String locality;
    public static String adress;

    public static double destinationLatitude;
    public static double destinationLongtitude;
    public static String destinationCountryName;
    public static String destinationLocality;
    public static String destinationAdress;
    public static List<Address> adresser;

    public FusedLocationProviderClient fusedLocationProviderClient; //sätt ej som static annars blir det "memory leak"
    public static final String TAG2 = "MYTAG"; //tag för Log.d
    public static double distance; //avstånd mellan de två koordinaterna.



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }

    public void backtoMainActivity(View BackButton) {
        Intent intent1 = new Intent(this, MainActivity.class);
        startActivity(intent1);
    }

    public void getCoords(View startFrGPSButton) {
        //kollar ifall mobilen har tillgång till GPS-funktion
        if (ActivityCompat.checkSelfPermission(PlanActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //kör denna kod när tillgång till GPS fåtts.
            getLocation(); //för att få startkoordinater på färden från nuvarande plats.
        } else {
            //frågar annars om tillgång till gps-funktionalitet
            ActivityCompat.requestPermissions(PlanActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }
    public void openEndCoordinatesMap(View endFromGPSButton){
        Intent intent2 = new Intent(this, selectEndMapsActivity.class);
        startActivity(intent2);
    }

    public void getLocation() {
        //kollar återigen efter tillstånd för gps funktion.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //nedan är kod för att hämta koordinater, adresser mm. från nuvarande plats.
        //de diverse variablerna i try-segmentet är olika variabler som var tänkta att vara -
        //bra att ha för fortsatt utveckling av appen men tiden räckte inte till.
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                //hämtar koordinater/location
                Location location = task.getResult();
                if (location != null) {
                    try {
                        Geocoder geo = new Geocoder(PlanActivity.this, Locale.getDefault());
                        // lägger in information i Listan adreser och hämtar latitud samt longtitud till den via Geocodern.
                        adresser = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        //sätter koordinatvärden på min double latitude.
                        latitude=adresser.get(0).getLatitude();
                        Log.d(TAG2, "latitude value:"+ latitude);
                        //sätter koordinatvärden på doubeln longtitud.
                        longtitude=adresser.get(0).getLongitude();
                        Log.d(TAG2, "longtitude value:"+ longtitude);
                        countryName=adresser.get(0).getCountryName();
                        Log.d(TAG2, "country value:"+ countryName);
                        locality=adresser.get(0).getLocality();
                        Log.d(TAG2, "locality value :"+ locality);
                        adress=adresser.get(0).getAddressLine(0).toString();
                        Log.d(TAG2, "adress value:"+ adress);
                        setAdressToSearchView();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    public void setAdressToSearchView(){
        //sätter nuvarande s.k "AdressLine" från variabeln adress till TextViewen i PlanActivity.
        //TextViewen var tidigare en SearchView då tanken var att kunna söka efter adress m. hjälp av autoFill men tiden räckte inte. -
        // därav namnet "setAdressToSearchView" på metoden. ursäkta om metoders namn refererar till SearchView istället för TextView.
        TextView startSearchView = findViewById(R.id.startLocTextView);
        startSearchView.setText(adress);

    }
}