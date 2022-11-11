package com.example.csse;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://192.168.137.1:8090";




    EditText email,password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        login = findViewById(R.id.Login_btn);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(email.getText().toString())){
                    email.setError("Email is compulsory");
                    return;
                }
                if(TextUtils.isEmpty(password.getText().toString())){
                    password.setError("Password is compulsory");
                    return;
                }

                HashMap<String,String> map = new HashMap<>();
                map.put("email_address",email.getText().toString());
                map.put("password",password.getText().toString());




                Call<Void> call2 = retrofitInterface.loginUser(map);

                call2.enqueue(new Callback<Void>() {

                    @Override

                    public void onResponse(Call<Void> call, Response<Void> response) {

                        int statusCode = response.code();

//                        user = (Client) call2;
                        System.out.println(statusCode);
                        if(statusCode==500){

                            AlertDialog.Builder builder=new AlertDialog.Builder(email.getContext());
                            builder.setTitle("Alert");
                            builder.setMessage("Incorrect Email Or Password");



                            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            builder.show();



                        }else{



                            Intent intent = new Intent(Login.this,Home.class);
                            intent.putExtra("userid",email.getText().toString());
                            startActivity(intent);
                            finish();




                        }

                    }



                    @Override

                    public void onFailure(Call<Void> call2, Throwable t) {
                        System.out.println(t);


                    }
                });




            }
        });

    }
}