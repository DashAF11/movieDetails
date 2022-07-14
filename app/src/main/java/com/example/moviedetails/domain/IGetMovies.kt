package com.example.moviedetails.domain

import com.example.moviedetails.data.pojo.BaseResponse
import com.example.moviedetails.data.pojo.DataState
import com.example.moviedetails.data.pojo.MovieData
import kotlinx.coroutines.flow.Flow

interface IGetMovies {
    suspend fun getMovies(): Flow<DataState<BaseResponse<List<MovieData>>>>
    suspend fun getMoviesPaging(pageCount: Int): Flow<DataState<BaseResponse<List<MovieData>>>>
    suspend fun getSingleMovie(movieId: Int): Flow<DataState<MovieData>>
    suspend fun getSearchMovieData(
        searchedKey: String,
        pageCount: Int
    ): Flow<DataState<BaseResponse<List<MovieData>>>>
}