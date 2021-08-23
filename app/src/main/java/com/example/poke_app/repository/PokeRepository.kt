package com.example.poke_app.repository

import android.content.Context
import com.example.poke_app.database.AppDataBase
import com.example.poke_app.model.PokeResponse
import com.example.poke_app.model.Pokemon
import com.example.poke_app.model.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokeRepository(private val context: Context) {


    private val database = AppDataBase.getDataBase(context)

    fun fetchAll(onComplete: (PokeResponse?, String?) -> Unit) {
        val retrofitSerice = RetrofitBuilder.getPokeService()
        val call = retrofitSerice.getAll()
        call.enqueue(object : Callback<PokeResponse> {

            override fun onResponse(call: Call<PokeResponse>, response: Response<PokeResponse>) {
                if (response.body() != null) {
                    onComplete(response.body(), null)
                } else {
                    onComplete(null, "Erro")
                }
            }

            override fun onFailure(call: Call<PokeResponse>, t: Throwable) {
                onComplete(null, t.message)
            }
        })
    }

    fun insertIntoDatabase(items: List<Pokemon>) {
        val dao = database.pokemonDAO()
        items.forEach { poke ->
            dao.insert(pokemon = poke)
        }

    }

    fun fetchAllFromDatabase(): List<Pokemon>? {
        val dao = database.pokemonDAO()
        return dao.all()
    }


}