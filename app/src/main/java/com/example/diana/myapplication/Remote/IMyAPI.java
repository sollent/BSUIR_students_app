package com.example.diana.myapplication.Remote;

import com.example.diana.myapplication.Model.LoginResponse;
import com.example.diana.myapplication.Model.SaveDataResponse;
import com.example.diana.myapplication.Model.StudentListResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IMyAPI {
    @FormUrlEncoded
    @POST("/login")
    Call<LoginResponse> loginUser(@Field("email") String email, @Field("password") String password);

    @GET("/is-fully-user")
    Call<SaveDataResponse> isFullyUser(@Header("Authorization") String token);

    @GET("/get-users-by-average-mark")
    Call<StudentListResponse[]> getStudentList(@Header("Authorization") String token, @Query("special_id") int special_id);

    @FormUrlEncoded
    @POST("/register")
    Call<LoginResponse> registerUser(@Field("name") String name, @Field("email") String email, @Field("password") String password);

    @GET("/user-by-token")
    Call<LoginResponse> getUserByToken(@Query("token") String token);


    @FormUrlEncoded
    @POST("/save-user")
    Call<SaveDataResponse> saveAccountData(
            @Header("Authorization") String token,
            @Field("firstName") String firstName,
            @Field("lastName") String lastName,
            @Field("thirdName") String thirdName,
            @Field("birthDay") String birthDay,
            @Field("isMan") boolean isMan,
            @Field("isWomen") boolean isWomen,
            @Field("protectionInfo") String protectionInfo,
            @Field("averageMark") String averageMark,
            @Field("isFreePosition") boolean isFreePosition,
            @Field("isNotFreePosition") boolean isNotFreePosition,
            @Field("documentType") String documentType,
            @Field("documentIdNumber") String documentIdNumber,
            @Field("documentSeries") String documentSeries,
            @Field("documentNumber") String documentNumber,
            @Field("documentDate") String documentDate,
            @Field("documentWhoGot") String documentWhoGot,
            @Field("placeEducation") String placeEducation,
            @Field("educationDocumentType") String educationDocumentType,
            @Field("schoolNumber") String schoolNumber,
            @Field("documentSchoolNumber") String documentSchoolNumber,
            @Field("dateOfEndEducation") String dateOfEndEducation,
            @Field("index") String index,
            @Field("country") String country,
            @Field("area") String area,
            @Field("raion") String raion,
            @Field("typeOfPoint") String typeOfPoint,
            @Field("nameOfPoint") String nameOfPoint,
            @Field("streetType") String streetType,
            @Field("streetName") String streetName,
            @Field("houseNumber") String houseNumber,
            @Field("housePartNumber") String housePartNumber,
            @Field("apartmentNumber") String apartmentNumber,
            @Field("wantHaveHome") boolean wantHaveHome,
            @Field("countryCode") String countryCode,
            @Field("homePhone") String homePhone,
            @Field("mobilePhone") String mobilePhone,
            @Field("departmentName") String departmentName,
            @Field("special_id") int special_id
    );

}
