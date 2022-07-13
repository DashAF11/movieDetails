package com.example.moviedetails.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedetails.data.pojo.BaseResponse
import com.example.moviedetails.data.pojo.DataState
import com.example.moviedetails.data.pojo.MovieData
import com.example.moviedetails.data.pojo.MovieDetail
import com.example.moviedetails.domain.IGetMovies
import com.example.moviedetails.domain.toSharedFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val getMoviesRepository: IGetMovies) :
    ViewModel() {

    private val _getMovieData = MutableSharedFlow<DataState<BaseResponse<List<MovieData>>>>()
    private val _getMovieDetails = MutableSharedFlow<DataState<BaseResponse<MovieDetail>>>()

    val getMovieData = _getMovieData.toSharedFlow()
    val getMovieDetails = _getMovieDetails.toSharedFlow()

    fun getMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            getMoviesRepository.getMovies().onEach {
                _getMovieData.emit(it)
            }.launchIn(viewModelScope)
        }
    }

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getMoviesRepository.getMovieDetails(movieId).onEach {
                _getMovieDetails.emit(it)
            }.launchIn(viewModelScope)
        }
    }
}