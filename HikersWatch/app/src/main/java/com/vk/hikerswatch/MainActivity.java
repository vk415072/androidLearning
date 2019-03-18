package com.vk.hikerswatch;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;

        //to be executed once the location permission is granted
            //detailed notes can b found in "Map And Location Demo App".
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = findViewById(R.id.textView);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //Log.i("Location Data", location.toString());
                    //getting the required data.
                String latitude = new String(String.valueOf(location.getLatitude()));
                String longitude = new String(String.valueOf(location.getLongitude()));
                String altitude = new String (String.valueOf(location.getAltitude()));
                String accuracy = new String(String.valueOf(location.getAccuracy()));
                String speed = new String(String.valueOf(location.getSpeed()*1.852));
                    //setting the texts...
                textView.setText("Latitude: "+latitude+"\nLongitude: "+longitude+"\nAltitude: "+altitude+" meters"+"\nAccuracy: "+accuracy+"\nSpeed: "+speed+" Km/h");

                //FOR ADDRESS FIELD,we'll use revere geo coding.
                        //Geo coding is the process of going from an address to a pair of coordinates. But we're going the other way so we'll use reverse geo coding.
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    //creating a list of addresses.
                try {
                        //using geo coder to get location data from location variable. The 1 in the end tells that hoe much addresses we want to be stored in the list.
                    List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        //checking if the addressList is'nt null, also the size of addressList is greater then zero.
                    if(addressList != null && addressList.size()>0){
                            //get(0) is only asking for 1st element from the list, also which is the only item in the list. Also getting only the address line from the list of complete address data.
                        //Log.i("addressList", addressList.get(0).getAddressLine(0));
                            //checking if the AddressLine(0) is'nt null.
                        if(addressList.get(0).getAddressLine(0) != null) {
                                //appending the address text to out textView.
                            textView.append("\n\nAddress: " + addressList.get(0).getAddressLine(0));
                        }else{
                            textView.append("\n\nAddress: Could not find the address...");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }
            @Override
            public void onProviderEnabled(String provider) {
            }
            @Override
            public void onProviderDisabled(String provider) {
            }
        };
            //getting user's permission & location:
                //detailed info can be found in the "Map And Location Demo App".
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }
}
