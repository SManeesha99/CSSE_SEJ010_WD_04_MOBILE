package com.example.csse;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home extends AppCompatActivity {
    double latitute,longitude;
    String Email;
    static String  City="Location Not Found";
    TextView user_email;
    Button qrscan,setDestinstion,logout;
    FusedLocationProviderClient fusedLocationProviderClient;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://192.168.137.1:8090";
    EditText email,source;
    String userEmail;

    private final static int REQUEST_CODE =100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent homepageIntent = getIntent();

        userEmail = homepageIntent.getStringExtra("userid");

        user_email = findViewById(R.id.user_email);
        user_email.setText("Hi "+userEmail);

        logout = findViewById(R.id.logout);

        qrscan = findViewById(R.id.qrscan_btn);

        setDestinstion = findViewById(R.id.qrscan_btn2);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Home.this,Login.class);

                    startActivity(intent);
                    finish();

            }
        });


        qrscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                scanCode();
//                setSource("hi@gmail.com","india");
                scanCode();

            }
        });

        setDestinstion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getLastLocation(1);
                scanCode2();

            }
        });
    }

    private void scanCode(){
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLaunch.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLaunch = registerForActivityResult(new ScanContract(),result -> {
        if(result.getContents()!=null){

//            AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
//            builder.setTitle(result.getContents());
              Email = result.getContents();
//            builder.setMessage("hello");
              getLastLocation(1);



//           builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//               @Override
//               public void onClick(DialogInterface dialogInterface, int i) {
//                   dialogInterface.dismiss();
//               }
//           }).show();
        }
    });


    private void scanCode2(){
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLaunch2.launch(options);
    }

     ActivityResultLauncher<ScanOptions> barLaunch2 = registerForActivityResult(new ScanContract(),result -> {
        if(result.getContents()!=null){

//            AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
//            builder.setTitle(result.getContents());
            Email = result.getContents();
//            builder.setMessage("hello");
            getLastLocation(2);



//            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    dialogInterface.dismiss();
//                }
//            }).show();
        }
    });

    private void getLastLocation(int no){

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                    if(location!=null){
//                        System.out.println(location);
                        Geocoder geocoder =new Geocoder(Home.this, Locale.getDefault());
                        List<Address> addresses= null;
                        try {
                            addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
//                            latitute = addresses.get(0).getLatitude();
//                            System.out.println(addresses.get(0).getLatitude());
//                            longitude = addresses.get(0).getLongitude();
//                            System.out.println(addresses.get(0).getLongitude());
//                            city.setText("City :"+addresses.get(0).getLocality());
                            City = addresses.get(0).getLocality();
//                            System.out.println(addresses.get(0).getLocality());
                            if (no==1){
                                setSource(Email,City);
//                                city.setText(Email +" "+City);

                            }else{
                                setDestination(Email,"colombo");
                            }




                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }


                }
            });

        }else{

            askPermission();


        }


    }

    private void askPermission() {
        ActivityCompat.requestPermissions(Home.this,new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                getLastLocation(1);
            }else{
                Toast.makeText(this, "Required Permission", Toast.LENGTH_SHORT).show();
            }
        }



        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private void setSource(String e,String l){

        HashMap<String,String> map = new HashMap<>();
        map.put("passenger_email",e);
        map.put("source",l);

        Call<Void> call2 = retrofitInterface.setSource(map);

        call2.enqueue(new Callback<Void>() {

            @Override

            public void onResponse(Call<Void> call, Response<Void> response) {

                int statusCode = response.code();

//
                System.out.println(statusCode);
                if(statusCode==500){

                    AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                    builder.setTitle("Alert");
                    builder.setMessage("Error");



                    builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();



                }else{

                    AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                    builder.setTitle("Success");
                    builder.setMessage("Source Location Updated");

//                    Intent intent = new Intent(Home.this,Home.class);
////                    intent.putExtra("userid",email.getText().toString());
//                    startActivity(intent);
//                    finish();

                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();




                }

            }



            @Override

            public void onFailure(Call<Void> call2, Throwable t) {
                System.out.println(t);


            }
        });






    }

    private void setDestination(String e,String l){

        HashMap<String,String> map = new HashMap<>();
        map.put("passenger_email",e);
        map.put("destination",l);

        Call<Void> call2 = retrofitInterface.setDestination(map);

        call2.enqueue(new Callback<Void>() {

            @Override

            public void onResponse(Call<Void> call, Response<Void> response) {

                int statusCode = response.code();

//                        user = (Client) call2;
                System.out.println(statusCode);
                if(statusCode==500){

                    AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                    builder.setTitle("Alert");
                    builder.setMessage("Error");



                    builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();



                }else{

                    AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                    builder.setTitle("Success");
                    builder.setMessage("Destination Location Updated");

//                    Intent intent = new Intent(Home.this,Home.class);
////                    intent.putExtra("userid",email.getText().toString());
//                    startActivity(intent);
//                    finish();

                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();


                }

            }



            @Override

            public void onFailure(Call<Void> call2, Throwable t) {
                System.out.println(t);


            }
        });






    }

}