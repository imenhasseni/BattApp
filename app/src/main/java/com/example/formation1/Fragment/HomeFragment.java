package com.example.formation1.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.media.audiofx.BassBoost;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.formation1.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.intentfilter.androidpermissions.PermissionManager;
import com.intentfilter.androidpermissions.models.DeniedPermission;
import com.intentfilter.androidpermissions.models.DeniedPermissions;

import java.util.Arrays;

import static java.util.Collections.singleton;

public class HomeFragment extends Fragment  implements OnMapReadyCallback {

    private SupportMapFragment mapFragment;
    private GoogleMap gmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getPermission();
    }

    private void getPermission() {
        PermissionManager permissionManager = PermissionManager.getInstance(getActivity());
        permissionManager.checkPermissions(singleton(Manifest.permission.ACCESS_FINE_LOCATION), new PermissionManager.PermissionRequestListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(getActivity(), "Permissions Granted", Toast.LENGTH_SHORT).show();

                if (isGpsEnabled()) {

getDeviceLocation();
                } else{
                        openGpsDialog();
                    }
                }




            @Override
            public void onPermissionDenied(DeniedPermissions deniedPermissions) {
                String deniedPermissionsText = "Denied: " + Arrays.toString(deniedPermissions.toArray());
                Toast.makeText(getActivity(), deniedPermissionsText, Toast.LENGTH_SHORT).show();

                for (DeniedPermission deniedPermission : deniedPermissions) {
                    if (deniedPermission.shouldShowRationale()) {
                        // Display a rationale about why this permission is required
                    }
                }
            }
        });
    }


    private void getDeviceLocation (){
        FusedLocationProviderClient mFusedLocationProciedClient = LocationServices.getFusedLocationProviderClient(getActivity());
        final Task Location = mFusedLocationProciedClient.getLastLocation();
        Location.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){
                    Location currentLocation = (Location) task.getResult();
                    if (currentLocation !=null){
                        moveCameraPosition(currentLocation.getLatitude(),currentLocation.getLongitude());
                        gmap.setMyLocationEnabled(true);
                    }else {
                        Toast.makeText(getActivity(),"location null", Toast.LENGTH_SHORT).show();


                    }


                }
                    else {
                        Toast.makeText(getActivity(),"Could't get your current location", Toast.LENGTH_SHORT).show();
                    }

            }
        });

    }

    public boolean isGpsEnabled (){
        LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);

    }

    private void openGpsDialog(){
        AlertDialog.Builder alertbuilder =new AlertDialog.Builder(getActivity());


        alertbuilder.setMessage("Please activate your GPs")
                .setCancelable(false)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertbuilder.create();
        alert.setTitle("GPS");
        alert.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
       gmap = googleMap;
       double lat= 48.864716;
       double lng =  2.349014;
       MarkerOptions markerOptions =new MarkerOptions();
       markerOptions.position(new LatLng(lat,lng));
       markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin));
       markerOptions.title("Paris");
       markerOptions.snippet("this is the capital of france");
       markerOptions.anchor(0.5f,0.5f);
       gmap.addMarker(markerOptions);

moveCameraPosition(lat,lng);
    }

    private void moveCameraPosition( double latitude, double longitude){
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude ,longitude),12f));

    }



}
