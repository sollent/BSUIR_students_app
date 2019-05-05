package com.example.diana.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diana.myapplication.Adapter.StudentListAdapter;
import com.example.diana.myapplication.Common.Common;
import com.example.diana.myapplication.Model.Student;
import com.example.diana.myapplication.Model.StudentListResponse;
import com.example.diana.myapplication.Remote.IMyAPI;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentListActivity extends AppCompatActivity {

    IMyAPI mService;

    StudentListResponse[] studentList;

    SharedPreferences preferences;
    SharedPreferences.Editor shareEditor;

    TextView caption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        preferences = getApplicationContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
        shareEditor = preferences.edit();

        String currentToken = preferences.getString("AUTH_TOKEN", null);
        int special_id = preferences.getInt("special_id", 1);

        mService = Common.getAPI();
        caption = (TextView) findViewById(R.id.caption);

        getStudentsData(currentToken, special_id);
    }

    private void getStudentsData(String token, int special_id) {
        mService.getStudentList(token, special_id)
                .enqueue(new Callback<StudentListResponse[]>() {
                    @Override
                    public void onResponse(Call<StudentListResponse[]> call, Response<StudentListResponse[]> response) {
                        studentList = response.body();
                        Toast.makeText(StudentListActivity.this, "Success data save", Toast.LENGTH_SHORT).show();
                        setStudentList();
                    }

                    @Override
                    public void onFailure(Call<StudentListResponse[]> call, Throwable t) {
                        Toast.makeText(StudentListActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setStudentList() {
        ListView studentListView = (ListView) findViewById(R.id.student_list);
        ArrayList<Student> students = new ArrayList<>();
        for (int i = 0; i < studentList.length; i++) {
            if (i == 0) {
                caption.setText("Список поступающих на специальность '" + studentList[i].getSpecialName() + "'");
            }
            String fullName = studentList[i].getFirstName() + " " + studentList[i].getLastName() + " " + studentList[i].getThirdName();
            String number = "Средний балл:";
            Student student = new Student(fullName, number, Float.toString(studentList[i].getAverageMark()));
            students.add(student);
        }
        StudentListAdapter adapter = new StudentListAdapter(StudentListActivity.this, R.layout.adapter_student_list_view, students);
        studentListView.setAdapter(adapter);
    }
}
