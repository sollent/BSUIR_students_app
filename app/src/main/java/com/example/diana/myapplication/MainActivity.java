package com.example.diana.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diana.myapplication.Common.Common;
import com.example.diana.myapplication.Model.LoginResponse;
import com.example.diana.myapplication.Model.User;
import com.example.diana.myapplication.Remote.IMyAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView txt_register;
    EditText edt_email, edt_password;
    Button btn_login;

    SharedPreferences preferences;
    SharedPreferences.Editor shareEditor;

    IMyAPI mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getApplicationContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
        shareEditor = preferences.edit();

        String currentToken = preferences.getString("AUTH_TOKEN", null);

        if (currentToken != null) {
            startActivity(new Intent(this, AccountActivity.class));
        }

        // Init Service
        mService = Common.getAPI();

        // Init View
        txt_register = (TextView) findViewById(R.id.txt_register);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_password = (EditText) findViewById(R.id.edt_password);
        btn_login = (Button) findViewById(R.id.btn_login);

        // Event
        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticateUser(edt_email.getText().toString(), edt_password.getText().toString());
            }
        });

    }

    private void authenticateUser(String email, String password) {
        mService.loginUser(email, password)
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        LoginResponse result = response.body();
                        if (response.code() == 200) {
                            User user = result.getUser();
                            shareEditor.putString("AUTH_TOKEN", user.getToken());
                            shareEditor.commit();
                            Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                            intent.putExtra("specialId", user.getSpecial_id());
                            intent.putExtra("fullName", user.getFullName());
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Неверный email или пароль", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
