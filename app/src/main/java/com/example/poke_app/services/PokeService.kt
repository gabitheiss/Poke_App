package com.example.poke_app.services

import com.example.poke_app.model.PokeResponse
import retrofit2.Call
import retrofit2.http.GET

interface PokeService {

    @GET("/api/v2/pokemon/")
    fun getAll(): Call<PokeResponse>

}