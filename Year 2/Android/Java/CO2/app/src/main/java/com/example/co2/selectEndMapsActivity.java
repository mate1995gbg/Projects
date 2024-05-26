package com.example.co2;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.co2.databinding.ActivitySelectEndMapsBinding;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class selectEndMapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    private Button button;
    private ActivitySelectEndMapsBinding binding;
    public static final String TAG = "MYTAG";
    //public static String streetAdress; oanvänd variabel - ev. implementation senare
    public static double startLatForCalc;
    public static double startLongitudeForCalc;
    public static double endLatForCalc;
    public static double endLongForCalc;
    public static double longDiff; //differens för longitud. används i
    public static double distance; //avstånd mellan punkt 1 och punkt 2

    public static List<Address> AdresserHere;  //adressinfo från destination finns i denna lista.
    public static List<Address> AdresserThere; //adressinfo från avfärdspunkt finns i denna lista.
    public static Integer EmissonsHere; //för att hämta utsläpp i g/km från AddCar så fick denna deklareras här med.
    public static double calculatedEmissions; //uträknade koldioxidutsläpp för sträckan.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectEndMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        button = findViewById(R.id.setDestinationButton);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);
        AdresserThere= Collections.singletonList(PlanActivity.adresser.get(0));
        Log.d(TAG, AdresserThere.get(0).getAddressLine(0));
        EmissonsHere=AddCar.selectedCarEmissions;
        Log.d(TAG, "Emissons value :" + EmissonsHere);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        if (ActivityCompat.checkSelfPermission(selectEndMapsActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //kör denna kod när tillgång till GPS fåtts.
            try {
                Log.d(TAG, "onMapLongCLick"+ latLng.toString());
                Geocoder geo=new Geocoder(this, Locale.getDefault());
                AdresserHere=geo.getFromLocation(latLng.latitude,latLng.longitude,1);
                Log.d(TAG, "onMapLongCLick"+ AdresserHere.get(0));
                Log.d(TAG, "selected loc latitude :"+ AdresserHere.get(0).getLatitude());
                Log.d(TAG, "selected loc longitude :"+ AdresserHere.get(0).getLongitude());
                TextView selectedAddress = findViewById(R.id.chosenAdressTextView);
                selectedAddress.setText(AdresserHere.get(0).getAddressLine(0)); //sätter adressen som text i searchview men "submittar" inte texten.

                Log.d(TAG, "attempting to set Planactivity.destinationlatitude as:" +AdresserHere.get(0).getLatitude());
                PlanActivity.destinationLatitude=AdresserHere.get(0).getLatitude();
                Log.d(TAG, "planactivity.destinationlatitude set as:"+PlanActivity.destinationLatitude);
                PlanActivity.destinationLongtitude=AdresserHere.get(0).getLongitude();
                PlanActivity.destinationCountryName=AdresserHere.get(0).getCountryName();
                PlanActivity.destinationLocality=AdresserHere.get(0).getLocality();
                PlanActivity.destinationAdress=AdresserHere.get(0).getAddressLine(0);

                calcDistance2();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            ActivityCompat.requestPermissions(selectEndMapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    //onClickmetod ur funktion
    public void onSelectedDestinationButton(View buttonGod){
        Log.d(TAG, "onSelectedDestinationButton init...");
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //denna onClickmetod fungerar inte. antagligen pga något static-problem.
                setDestAdressToSearchView();
            }
        });
    }

    public void backtoMainActivity(View BackButton2) {
        Intent intent1 = new Intent(this, PlanActivity.class);
        startActivity(intent1);
    }
    public void setDestAdressToSearchView(){
          PlanActivity.destinationLatitude=AdresserHere.get(0).getLatitude();
          PlanActivity.destinationLongtitude=AdresserHere.get(0).getLongitude();
          PlanActivity.destinationCountryName=AdresserHere.get(0).getCountryName();
          PlanActivity.destinationLocality=AdresserHere.get(0).getLocality();
          PlanActivity.destinationAdress=AdresserHere.get(0).getAddressLine(0); //check index!
          calcDistance2();
    }
    public void calcDistance2(){
        double distance = 0;
        Location locationA = new Location("A");
        locationA.setLatitude(AdresserHere.get(0).getLatitude());
        locationA.setLongitude(AdresserHere.get(0).getLongitude());
        Location locationB = new Location("B");
        locationB.setLatitude(AdresserThere.get(0).getLatitude());
        locationB.setLongitude(AdresserThere.get(0).getLongitude());
        distance = locationA.distanceTo(locationB);
        distance = distance/1000; //från meter till kilometer
        Log.d(TAG, "avstånd i Kilometer:  " + distance);
        calculatedEmissions=EmissonsHere*distance;
        Log.d(TAG, "Beräknade utsläpp för resan är:  " + calculatedEmissions+ " gram CO2!");
    }
}
