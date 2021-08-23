package com.example.poke_app.repository

import com.example.poke_app.model.PokeResponse
import com.example.poke_app.model.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokeRepository {

    fun getPokemons(callback: (PokeResponse?, String?) -> Unit) {

        val retrofitSerice = RetrofitBuilder.getPokeService()
        val call = RetrofitBuilder.getPokeService().getAll()
        call.enqueue(object : Callback<PokeResponse> {

            override fun onResponse(call: Call<PokeResponse>, response: Response<PokeResponse>) {
                if (response.body() != null) {
                    response.body()?.let { pokeResponse ->
                        callback(pokeResponse, null)
                    }
                } else {
                    callback(null, "Erro")
                }
            }

            override fun onFailure(call: Call<PokeResponse>, t: Throwable) {
                callback(null, t.message)
            }

        })

    }
}