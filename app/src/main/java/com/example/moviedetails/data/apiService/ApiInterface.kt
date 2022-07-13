package com.example.moviedetails.data.apiService

import com.example.moviedetails.data.pojo.BaseResponse
import com.example.moviedetails.data.pojo.MovieData
import com.example.moviedetails.data.apiService.APIConstant.CASE_GET_MOVIES
import retrofit2.http.GET

interface ApiInterface {
    @GET(CASE_GET_MOVIES)
    suspend fun getMovies(): BaseResponse<List<MovieData>>
}