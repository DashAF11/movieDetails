package com.example.moviedetails.domain

import com.example.moviedetails.data.pojo.BaseResponse
import com.example.moviedetails.data.pojo.DataState
import com.example.moviedetails.data.pojo.MovieData
import com.example.moviedetails.data.pojo.MovieDetail
import kotlinx.coroutines.flow.Flow

interface IGetMovies {
    suspend fun getMovies(): Flow<DataState<BaseResponse<List<MovieData>>>>
    suspend fun getMovieDetails(movieId: Int): Flow<DataState<BaseResponse<MovieDetail>>>
}