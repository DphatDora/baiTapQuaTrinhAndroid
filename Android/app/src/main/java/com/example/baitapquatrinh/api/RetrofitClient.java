package com.example.baitapquatrinh.api;

public class RetrofitClient extends BaseClient {
    private static final String BASE_URL = "http://10.0.2.2:8080";
    private static APIService apiService;
    public static APIService getInstance(){
        if(apiService == null){
            apiService = createService( BASE_URL, APIService.class);
        }
        return apiService;
    }
}
