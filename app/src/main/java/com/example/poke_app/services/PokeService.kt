package com.example.poke_app.services

import com.example.poke_app.model.PokeResponse
import com.example.poke_app.model.PokemonDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeService {

    @GET("/api/v2/pokemon?limit=1118")
    fun getAll(): Call<PokeResponse>


    @GET("/api/v2/pokemon/{id}")
    fun getDetails(@Path("id") id: String): Call<PokemonDetails>

}