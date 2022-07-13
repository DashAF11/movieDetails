package com.example.moviedetails.data.repository

import com.example.moviedetails.data.apiService.ApiInterface
import com.example.moviedetails.data.networkWrapper.SafeApiRequest
import com.example.moviedetails.data.pojo.DataState
import com.example.moviedetails.domain.IGetMovies
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMoviesRepository @Inject constructor(
    private val safeApiRequest: SafeApiRequest,
    private val apiInterface: ApiInterface
) : IGetMovies {
    override suspend fun getMovies() = flow {
        emit(DataState.Loading)
        emit(safeApiRequest.apiRequest { apiInterface.getMovies() })
    }
}
