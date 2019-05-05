package com.example.diana.myapplication;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diana.myapplication.Common.Common;
import com.example.diana.myapplication.Model.SaveDataResponse;
import com.example.diana.myapplication.Remote.IMyAPI;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataActivity extends AppCompatActivity {

    Spinner documentType;
    String[] documentTypes =
            {
                    "Паспорт гражданина Беларусь",
                    "Паспорт гражданина Российской федерации",
                    "Паспорт гражданина Республики Казахстан"
            };

    Spinner placeEducation;
    String[] schoolTypes =
            {
                    "Средняя школа",
                    "Вечерняя школа",
                    "Гимназия",
                    "Гимназия-интернат",
                    "Лицей",
                    "Специализированный лицей",
                    "Суворовское училище",
                    "Кадетское училище"
            };

    Spinner educationDocumentType;
    String[] documentSchoolTypes =
            {
                    "Аттестат об общем среднем образовании",
                    "Аттестат с награждением медалью"
            };

    // place of special and special types
    Spinner placeOfSpecialType;
    String[] placeOfSpecialTypes =
            {
                    "Радиотехническое отделение",
                    "Отделение компьютерных технологий",
                    "Отделение электроники"
            };

    Spinner specialType;
    String[][] specialTypes =
            {
                    {
                            "Проектирование и производство радиоэлектронных средств",
                            "Техническая эксплуатация радиоэлектронных средств",
                            "Программируемые мобильные системы"
                    },
                    {
                            "Программное обеспечение информационных технологий"
                    },
                    {
                            "Электронные вычислительные средства",
                            "Микро- и наноэлектронные технологии и системы"
                    }
            };

    String[] specialTypesIds =
            {
                    "Проектирование и производство радиоэлектронных средств",
                    "Техническая эксплуатация радиоэлектронных средств",
                    "Программируемые мобильные системы",
                    "Программное обеспечение информационных технологий",
                    "Электронные вычислительные средства",
                    "Микро- и наноэлектронные технологии и системы"
            };

    TextView birthDay;
    Button setBirthDay;

    TextView documentDate;
    Button set_pasport_date;

    TextView dateOfEndEducation;
    Button set_schoolDateEnd;

    Calendar c;
    DatePickerDialog dpd;

    // Checkboxes
    CheckBox isMan, isWomen;
    CheckBox isFree, isNotFree;
    CheckBox wantHaveHome;

    EditText firstName, lastName, thirdName,
            protectionInfo, averageMark,
            documentIdNumber, documentSeries,
            documentNumber, documentWhoGot,
            schoolNumber, documentSchoolNumber,
            index, country, area, raion, typeOfPoint, nameOfPoint,
            streetType, streetName, houseNumber, housePartNumber,
            apartmentNumber, countryCode, homePhone, mobilePhone;


    Button saveData;

    IMyAPI mService;

    SharedPreferences preferences;
    SharedPreferences.Editor shareEditor;
    String currentToken;

    int specialId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        mService = Common.getAPI();

        preferences = getApplicationContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
        shareEditor = preferences.edit();

        currentToken = preferences.getString("AUTH_TOKEN", null);

        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        thirdName = (EditText) findViewById(R.id.thirdName);
        protectionInfo = (EditText) findViewById(R.id.protectionInfo);
        averageMark = (EditText) findViewById(R.id.averageMark);
        documentIdNumber = (EditText) findViewById(R.id.pasport_idNumber);
        documentSeries = (EditText) findViewById(R.id.pasport_series);
        documentNumber = (EditText) findViewById(R.id.pasport_number);
        documentWhoGot = (EditText) findViewById(R.id.pasport_whoIs);
        schoolNumber = (EditText) findViewById(R.id.schoolNumber);
        documentSchoolNumber = (EditText) findViewById(R.id.documentNumber);
        index = (EditText) findViewById(R.id.address_index);
        country = (EditText) findViewById(R.id.address_country);
        area = (EditText) findViewById(R.id.address_area);
        raion = (EditText) findViewById(R.id.address_raion);
        typeOfPoint = (EditText) findViewById(R.id.address_typeOfPoint);
        nameOfPoint = (EditText) findViewById(R.id.address_nameOfPoint);
        streetType = (EditText) findViewById(R.id.address_streetType);
        streetName = (EditText) findViewById(R.id.address_streetName);
        houseNumber = (EditText) findViewById(R.id.address_homeNumber);
        housePartNumber = (EditText) findViewById(R.id.address_homePartNumber);
        apartmentNumber = (EditText) findViewById(R.id.address_apartmentNumber);
        countryCode = (EditText) findViewById(R.id.codeOfCounty);
        homePhone = (EditText) findViewById(R.id.housePhone);
        mobilePhone = (EditText) findViewById(R.id.mobilePhone);

        saveData = (Button) findViewById(R.id.saveData);

        wantHaveHome = (CheckBox) findViewById(R.id.wantHaveHome);

        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (firstName.getText().toString() != null &&
//                        lastName.getText().toString() != null &&
//                        thirdName.getText().toString() != null &&
//                        protectionInfo.getText().toString() != null &&
//                        averageMark.getText().toString() != null &&
//                        documentIdNumber.getText().toString() != null &&
//                        documentSeries.getText().toString() != null &&
//                        documentNumber.getText().toString() != null &&
//                        documentWhoGot.getText().toString() != null &&
//                        schoolNumber.getText().toString() != null &&
//                        documentSchoolNumber.getText().toString() != null &&
//                        index.getText().toString() != null &&
//                        country.getText().toString() != null &&
//                        area.getText().toString() != null &&
//                        raion.getText().toString() != null &&
//                        typeOfPoint.getText().toString() != null &&
//                        nameOfPoint.getText().toString() != null &&
//                        streetType.getText().toString() != null &&
//                        streetName.getText().toString() != null &&
//                        houseNumber.getText().toString() != null &&
//                        housePartNumber.getText().toString() != null &&
//                        apartmentNumber.getText().toString() != null &&
//                        countryCode.getText().toString() != null &&
//                        homePhone.getText().toString() != null &&
//                        mobilePhone.getText().toString() != null &&
//                        birthDay.getText().toString() != null &&
//                        documentDate.getText().toString() != null &&
//                        dateOfEndEducation.getText().toString() != null
//                        ) {
//                    saveData();
//                } else {
//                    Toast.makeText(DataActivity.this, "Не все поля заполнены", Toast.LENGTH_SHORT).show();
//                }
                saveData();
            }
        });

        // Spinners
        documentType = (Spinner) findViewById(R.id.documentType);
        ArrayAdapter<String> documentAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, documentTypes);
        documentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        documentType.setAdapter(documentAdapter);

        placeEducation = (Spinner) findViewById(R.id.schoolType);
        ArrayAdapter<String> schoolAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, schoolTypes);
        schoolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        placeEducation.setAdapter(schoolAdapter);

        educationDocumentType = (Spinner) findViewById(R.id.documentSchoolType);
        ArrayAdapter<String> documentSchoolTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, documentSchoolTypes);
        documentSchoolTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        educationDocumentType.setAdapter(documentSchoolTypeAdapter);

        placeOfSpecialType = (Spinner) findViewById(R.id.placeOfSpecialType);
        ArrayAdapter<String> placeOfSpecialTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, placeOfSpecialTypes);
        placeOfSpecialTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        placeOfSpecialType.setAdapter(placeOfSpecialTypeAdapter);

        specialType = (Spinner) findViewById(R.id.specialType);

        placeOfSpecialType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<String> newAdapter = new ArrayAdapter<String>(DataActivity.this, android.R.layout.simple_spinner_item, specialTypes[position]);
                newAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                specialType.setAdapter(newAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Checkboxes logic
        isMan = (CheckBox) findViewById(R.id.isMan);
        isWomen = (CheckBox) findViewById(R.id.isWomen);

        isFree = (CheckBox) findViewById(R.id.isFree);
        isNotFree = (CheckBox) findViewById(R.id.isNotFree);

        isMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMan.isChecked()) {
                    isWomen.setChecked(false);
                }
                if (!isMan.isChecked()) {
                    isWomen.setChecked(true);
                }
            }
        });

        isWomen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isWomen.isChecked()) {
                    isMan.setChecked(false);
                }
                if (!isWomen.isChecked()) {
                    isMan.setChecked(true);
                }
            }
        });

        isFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFree.isChecked()) {
                    isNotFree.setChecked(false);
                }
                if (!isFree.isChecked()) {
                    isNotFree.setChecked(true);
                }
            }
        });

        isNotFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNotFree.isChecked()) {
                    isFree.setChecked(false);
                }
                if (!isNotFree.isChecked()) {
                    isFree.setChecked(true);
                }
            }
        });


        // Date pickers
        birthDay = (TextView) findViewById(R.id.birthDay);
        setBirthDay = (Button) findViewById(R.id.setBirthDay);

        documentDate = (TextView) findViewById(R.id.pasport_date);
        set_pasport_date = (Button) findViewById(R.id.set_pasport_date);

        dateOfEndEducation = (TextView) findViewById(R.id.schoolDateEnd);
        set_schoolDateEnd = (Button) findViewById(R.id.set_schoolDateEnd);

        setBirthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(DataActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        birthDay.setText(dayOfMonth + "." + (month + 1) + "." + year);
                    }
                }, day, month, year);
                dpd.show();
            }
        });

        set_pasport_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(DataActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        documentDate.setText(dayOfMonth + "." + (month + 1) + "." + year);
                    }
                }, day, month, year);
                dpd.show();
            }
        });

        set_schoolDateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(DataActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateOfEndEducation.setText(dayOfMonth + "." + (month + 1) + "." + year);
                    }
                }, day, month, year);
                dpd.show();
            }
        });

    }

    private void saveData() {

        String currentSpecial = specialType.getSelectedItem().toString();

        int special_id = 1;

        for (int i = 0; i < specialTypesIds.length; i++) {
            if (specialTypesIds[i].equals(currentSpecial)) {
                special_id = i + 1;
            }
        }

        specialId = special_id;

        mService.saveAccountData(
                currentToken,
                firstName.getText().toString(),
                lastName.getText().toString(),
                thirdName.getText().toString(),
                birthDay.getText().toString(),
                isMan.isChecked(), isWomen.isChecked(),
                protectionInfo.getText().toString(),
                averageMark.getText().toString(),
                isFree.isChecked(),
                isNotFree.isChecked(),
                documentTypes[documentType.getSelectedItemPosition()],
                documentIdNumber.getText().toString(),
                documentSeries.getText().toString(),
                documentNumber.getText().toString(),
                documentDate.getText().toString(),
                documentWhoGot.getText().toString(),
                schoolTypes[placeEducation.getSelectedItemPosition()],
                documentSchoolTypes[educationDocumentType.getSelectedItemPosition()],
                schoolNumber.getText().toString(),
                documentSchoolNumber.getText().toString(),
                dateOfEndEducation.getText().toString(),
                index.getText().toString(),
                country.getText().toString(),
                area.getText().toString(),
                raion.getText().toString(),
                typeOfPoint.getText().toString(),
                nameOfPoint.getText().toString(),
                streetType.getText().toString(),
                streetName.getText().toString(),
                houseNumber.getText().toString(),
                housePartNumber.getText().toString(),
                apartmentNumber.getText().toString(),
                wantHaveHome.isChecked(),
                countryCode.getText().toString(),
                homePhone.getText().toString(),
                mobilePhone.getText().toString(),
                placeOfSpecialTypes[placeOfSpecialType.getSelectedItemPosition()],
                special_id
        )
                .enqueue(new Callback<SaveDataResponse>() {
                    @Override
                    public void onResponse(Call<SaveDataResponse> call, Response<SaveDataResponse> response) {
                        if (response.code() == 200) {
                            Toast.makeText(DataActivity.this, "Ваши данные успешно сохранены", Toast.LENGTH_SHORT).show();
                            shareEditor.putString("fullName", firstName.getText().toString() + " " + lastName.getText().toString() + " " + thirdName.getText().toString());
                            shareEditor.putString("averageMark", averageMark.getText().toString());
                            shareEditor.putInt("special_id", specialId);
                            shareEditor.commit();
                            Intent intent = new Intent(DataActivity.this, AccountActivity.class);
                            intent.putExtra("accountStatus", true);
                            startActivity(intent);
                        }
                        if (response.code() == 400) {
                            Toast.makeText(DataActivity.this, "Не все поля заполнены", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SaveDataResponse> call, Throwable t) {
                        Toast.makeText(DataActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
