package com.project.verification.EmpPkg;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("employees")
    Call<EmpPojo> getEmployees();
}