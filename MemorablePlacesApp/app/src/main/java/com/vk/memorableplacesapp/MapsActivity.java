package com.vk.memorableplacesapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    //STARTING FROM onMapReady() METHOD...

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;

    //Method used to pan the map to the marker.
    public void centerMapOnLocation(Location location, String string){
        if(location != null) {
            //getting and storing Lat & Lng from the Location.
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            //clearing the previous markers.
            mMap.clear();
            //setting the markers & and centering (panning) the map.
            mMap.addMarker(new MarkerOptions().position(latLng).title(string));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    //after allowing/denying this method is called...
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            //again checking if permission is granted or not.
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                //noe again getting the GPS updates.
                locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0,0,locationListener);

                //again, if no updates are provided by the GPS, getting the last known location
                //and sending it to the centerMapOnLocation method for panning the map.
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                centerMapOnLocation(lastKnownLocation, "Your Location");
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //getting intents
        Intent intent = getIntent();
        mMap.setOnMapLongClickListener(this);

        //if this intent is equals to zero then we know user has picked the very first item.
        //so we'll show his current location while zooming in.
        if(intent.getIntExtra("placeNumber",0) == 0){
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    //if update is get by the onLocationChanged method, centerMapOnLocation method is called to pan the map to that location.
                    centerMapOnLocation(location, "Your Location");
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
            //if location permission is granted by the user then update the location.
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                //updating location
                locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0,0,locationListener);
                //if no update is get from the GPS, getting last know location.
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                //sending lsat known location to centerMapOnLocation method. to pan the map to that location.
                centerMapOnLocation(lastKnownLocation, "Your Location");

            }else {
                //else asking the permission from the user.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
                //after the permission is granted or denied by the user, onRequestPermissionResult method will be called.
            }
            //'else' method will execute only if user long press on map.(check onMapLongClick() method).
            //else
        }else{
            Location placeLocation = new Location(LocationManager.GPS_PROVIDER);
            placeLocation.setLatitude(MainActivity.locations.get(intent.getIntExtra("placeNumber",0)).latitude);
            placeLocation.setLongitude(MainActivity.locations.get(intent.getIntExtra("placeNumber",0)).longitude);

            //again sending the place location and the arrayList item name as the string to the centerMapOnLocation() method.
            centerMapOnLocation(placeLocation, MainActivity.places.get(intent.getIntExtra("placeNumber", 0)));
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        String address = "";
        try {
            //using geo coder to get location data from location variable. The 1 in the end tells that how much addresses we want to be stored in the list
            List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if(addressList != null && addressList.size() >0){
                /*
                //
                if(addressList.get(0).getThoroughfare() != null){
                    if(addressList.get(0).getSubThoroughfare() != null){
                        address += addressList.get(0).getSubThoroughfare() + "";
                    }
                    address += addressList.get(0).getThoroughfare();
                }
                */
                address += addressList.get(0).getAddressLine(0);
            }
            //else if address line is null then set the date and time to the arrayList.
            else if(address == null){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                address += simpleDateFormat.format(new Date());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //adding new marker to the long pressed location, setting the title to the address.
        //latLang contains the co-ordinated received by the onMapLongClick() method.
        mMap.addMarker(new MarkerOptions().position(latLng).title(address));

        //accessing the MainActivity to update the places & locations ArrayList's.
        MainActivity.places.add(address);
        MainActivity.locations.add(latLng);
        MainActivity.arrayAdapter.notifyDataSetChanged();

        Toast.makeText(this, "Location Saved!", Toast.LENGTH_SHORT).show();
    }
}
