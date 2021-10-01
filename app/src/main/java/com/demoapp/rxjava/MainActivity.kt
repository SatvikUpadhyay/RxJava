package com.demoapp.rxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.apolis.moviesapp_retroft3.api.ApiClient
import com.apolis.moviesapp_retroft3.data.Genre
import com.apolis.moviesapp_retroft3.data.MovieDetailsResponse
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var m1 : Movies
    lateinit var m2 : Movies
    lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        m1 = Movies("ABC", "Action")
        m2 = Movies("XYZ", "Comedy")

        val observable = ApiClient.apiService.getMovieDetails(848278)

        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map{
                it.genres
            }
            .subscribeWith(getObserver())
    }

    fun getObservable() : Observable<Movies> {
        return Observable.just(m1,m2)
    }

    fun getObserver(): SingleObserver<List<Genre>> {
        return object:SingleObserver<List<Genre>>{
            override fun onSubscribe(d: Disposable) {
                disposable = d
                Log.d("called","onSubscribe")
            }

            override fun onError(e: Throwable) {
                Log.d("called","onError")
            }

            override fun onSuccess(t: List<Genre>) {
                TODO("Not yet implemented")
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}