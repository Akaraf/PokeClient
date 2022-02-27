package com.raaf.pokeclient.data.api

import com.raaf.pokeclient.data.dataModels.Pokemon
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeService {

    companion object {
        private const val BASE_URL = " https://pokeapi.co/api/v2/"

        fun create() : PokeService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(PokeInterceptor())
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okHttpClient)
                .build()

            return retrofit.create(PokeService::class.java)
        }
    }

    @GET("pokemon/{id}/")
    fun getPokemonById(@Path("id") id: Int): Single<Pokemon>

    @GET("pokemon/{name}/")
    fun getPokemonByName(@Path("name") name: String): Observable<Pokemon>
}