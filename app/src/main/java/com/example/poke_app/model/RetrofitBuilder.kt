package com.example.poke_app.model

import com.example.poke_app.services.PokeService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitBuilder {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    fun getPokeService(): PokeService{
        return retrofit.create(PokeService::class.java)

    }

}