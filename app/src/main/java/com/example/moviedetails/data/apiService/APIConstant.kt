package com.example.moviedetails.data.apiService

object APIConstant {
    const val BASE_URL = "https://api.themoviedb.org/3/movie/"
    const val END_URL_MOVIE_DETAIL = "?api_key=a32b3c9922836cf80716782cea3325f5&language=en-US"

    const val CASE_GET_MOVIES =
        "popular?api_key=a32b3c9922836cf80716782cea3325f5&language=en-US&page=1"

    const val CASE_GET_MOVIE_DETAILS =
        "{movie_id}$END_URL_MOVIE_DETAIL"
}

