package com.apolis.moviesapp_retroft3.api

import com.apolis.moviesapp_retroft3.data.MovieDetailsResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId: Int): Single<MovieDetailsResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") MovieID : String, @Query("genre") GenreID : String) : Call<MovieDetailsResponse>

    @POST("movie/login")
    fun postMovieDetails(@Body jsonbody : MovieDetailsResponse) : Boolean
}