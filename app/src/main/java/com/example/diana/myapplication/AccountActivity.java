package com.example.diana.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diana.myapplication.Common.Common;
import com.example.diana.myapplication.Model.LoginResponse;
import com.example.diana.myapplication.Model.SaveDataResponse;
import com.example.diana.myapplication.Remote.IMyAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountActivity extends AppCompatActivity {

    TextView txt_username;
    Button createData, logout, studentListButton;

    IMyAPI mService;

    SharedPreferences preferences;
    SharedPreferences.Editor shareEditor;

    boolean isFullyUser = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mService = Common.getAPI();

        preferences = getApplicationContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
        shareEditor = preferences.edit();

        String currentToken = preferences.getString("AUTH_TOKEN", null);

        Bundle arguments = getIntent().getExtras();

        if(arguments != null) {
            String special_id = arguments.getString("specialId");
            String fullName = arguments.getString("fullName");
            shareEditor.putInt("special_id", Integer.parseInt(special_id));
            shareEditor.putString("fullName", fullName);
            shareEditor.apply();
        }

        boolean accountStatus = false;
        if (arguments != null) {
            accountStatus = arguments.getBoolean("accountStatus");
        }

        if (accountStatus) {
            Toast.makeText(this, "Теперь вам доступен список поступающих", Toast.LENGTH_SHORT).show();
            shareEditor.putBoolean("accountUpdated", true);
            shareEditor.commit();
        }

        createData = (Button) findViewById(R.id.btn_create_data);
        logout = (Button) findViewById(R.id.logout);
        txt_username = (TextView) findViewById(R.id.txt_username);

        if (preferences.getBoolean("accountUpdated", false)) {
            LinearLayout layout = (LinearLayout) createData.getParent();
            if (layout != null) {
                layout.removeView(createData);
            }
            setUserAfterUpdate();
            createStudentButton(layout);
            studentListButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(AccountActivity.this, StudentListActivity.class));
                }
            });
        } else if(!preferences.getBoolean("accountUpdated", false)) {
            isFullyUser(currentToken);
        }

        createData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this, DataActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareEditor.remove("AUTH_TOKEN");
                shareEditor.remove("fullName");
                shareEditor.remove("special_id");
                shareEditor.remove("averageMark");
                shareEditor.remove("accountUpdated");
                shareEditor.apply();
                startActivity(new Intent(AccountActivity.this, MainActivity.class));
            }
        });
    }

    private void isFullyUser(String token) {
        mService.isFullyUser(token)
                .enqueue(new Callback<SaveDataResponse>() {
                    @Override
                    public void onResponse(Call<SaveDataResponse> call, Response<SaveDataResponse> response) {
                        if (response.code() == 200) {
                            LinearLayout layout = (LinearLayout) createData.getParent();
                            if (layout != null) {
                                layout.removeView(createData);
                            }
                            setUserAfterUpdate();
                            createStudentButton(layout);
                            studentListButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(AccountActivity.this, StudentListActivity.class));
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<SaveDataResponse> call, Throwable t) {

                    }
                });
    }

    private void createStudentButton(LinearLayout layout) {
        studentListButton = new Button(this);
        studentListButton.setText("Список поступающих");
        studentListButton.setTextColor(Color.parseColor("#ffffff"));
        studentListButton.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.green_button_bg));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 50;
        params.rightMargin = 50;
        params.topMargin = 30;
        params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        params.height = 80;
        layout.addView(studentListButton, params);
    }

    private void setUserAfterUpdate() {
        String fullName = preferences.getString("fullName", null);
        txt_username.setText(fullName);
    }

    private void getUserData(String token) {
        mService.getUserByToken(token)
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        LoginResponse result = response.body();
                        if (response.code() == 200) {
                            txt_username.setText(result.getUser().getName());
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {

                    }
                });
    }
}
