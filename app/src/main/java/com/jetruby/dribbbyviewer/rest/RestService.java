package com.jetruby.dribbbyviewer.rest;

import com.jetruby.dribbbyviewer.model.Shot;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestService {
    @GET("shots")
    Call<List<Shot>> getRecentPopularShots(@Query("access_token") String accessToken, @Query("per_page") int perPage);
}
