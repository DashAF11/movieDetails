package com.example.moviedetails.data.apiService

import com.example.moviedetails.data.apiService.APIConstant.CASE_GET_MOVIES
import com.example.moviedetails.data.apiService.APIConstant.CASE_GET_MOVIES_PAGING
import com.example.moviedetails.data.apiService.APIConstant.CASE_GET_MOVIE_DETAILS
import com.example.moviedetails.data.apiService.APIConstant.CASE_SEARCH_MOVIES
import com.example.moviedetails.data.pojo.BaseResponse
import com.example.moviedetails.data.pojo.MovieData
import com.example.moviedetails.data.pojo.MovieDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET(CASE_GET_MOVIES)
    suspend fun getMovies(): BaseResponse<List<MovieData>>

    @GET(CASE_GET_MOVIES_PAGING)
    suspend fun getMoviesPaging(@Query("page") count: Int): BaseResponse<List<MovieData>>

    @GET(CASE_GET_MOVIE_DETAILS)
    suspend fun getMovieDetails(@Path("movie_id") movie_id: Int): BaseResponse<MovieDetail>


    @GET(CASE_SEARCH_MOVIES)
    suspend fun getSearchedMovieData(
        @Query("query") search_string: String,
        @Query("page") page_count: Int
    ): BaseResponse<List<MovieData>>
}