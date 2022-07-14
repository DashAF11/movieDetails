package com.example.moviedetails.data.apiService

object APIConstant {
    const val BASE_URL = "https://api.themoviedb.org/3/"

    const val CASE_GET_MOVIES =
        "movie/popular?api_key=a32b3c9922836cf80716782cea3325f5&language=en-US&page=1"

    const val CASE_GET_MOVIES_PAGING =
        "movie/popular?api_key=a32b3c9922836cf80716782cea3325f5&language=en-US"

    const val CASE_GET_MOVIE_DETAILS =
        "movie/{movie_id}?api_key=a32b3c9922836cf80716782cea3325f5&language=en-US"

    const val CASE_SEARCH_MOVIES =
        "search/movie?api_key=a32b3c9922836cf80716782cea3325f5&language=en-US&query={search_string}min&page={page_count}&include_adult=false"

}

