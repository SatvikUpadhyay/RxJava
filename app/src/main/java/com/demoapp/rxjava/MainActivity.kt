package com.demoapp.rxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.apolis.moviesapp_retroft3.api.ApiClient
import com.apolis.moviesapp_retroft3.data.MovieDetailsResponse
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var m1 : Movies
    lateinit var m2 : Movies

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        m1 = Movies("ABC", "Action")
        m2 = Movies("XYZ", "Comedy")

        val observable = ApiClient.apiService.getMovieDetails(848278)
        val observable1 = ApiClient.apiService.getMovieDetails(848277)

//        call2.enqueue(object : Callback<MovieDetailsResponse> {
//            override fun onResponse(
//                call: Call<MovieDetailsResponse>,
//                response: Response<MovieDetailsResponse>
//            ) {
//                Log.d("Response", response.body().toString())
//            }
//
//            override fun onFailure(call: Call<MovieDetailsResponse>, t: Throwable) {
//                Log.d("Response", "Failed")
//            }
//
//        })

        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(getObserver())

        observable1.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(getObserver())

    }

    fun getObservable() : Observable<Movies> {
        return Observable.just(m1,m2)
    }

    fun getObserver(): Observer<MovieDetailsResponse> {
        return object:Observer<MovieDetailsResponse>{
            override fun onSubscribe(d: Disposable) {
                Log.d("called","onSubscribe")
            }

            override fun onNext(t: MovieDetailsResponse) {
                Log.d("called",t.toString())
            }

            override fun onError(e: Throwable) {
                Log.d("called","onError")
            }

            override fun onComplete() {
                Log.d("called","onComplete")
            }

        }
    }
}