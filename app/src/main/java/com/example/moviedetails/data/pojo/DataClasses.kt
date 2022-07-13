package com.example.moviedetails.data.pojo

data class BaseResponse<T>(
    val success: Boolean,
    val message: String,
    val results: T
)

data class MovieData(
    val id: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val poster_path: String,
    val backdrop_path: String,
    val release_date: String,
    val vote_average: String,
    val adult: String,
)

data class MovieDetail(
    val id: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val poster_path: String,
    val backdrop_path: String,
    val release_date: String,
    val vote_average: String,
    val adult: String,
    val genres: List<Genres>
)

data class Genres(
    val id: String,
    val name: String
)