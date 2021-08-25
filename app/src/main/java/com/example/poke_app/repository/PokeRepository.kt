package com.example.poke_app.repository

import android.content.Context
import com.example.poke_app.database.AppDataBase
import com.example.poke_app.model.PokeResponse
import com.example.poke_app.model.Pokemon
import com.example.poke_app.model.PokemonDetails
import com.example.poke_app.model.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokeRepository(private val context: Context) {


    private val database = AppDataBase.getDataBase(context)
    val service = RetrofitBuilder.getPokeService()

    fun fetchAll(onComplete: (PokeResponse?, String?) -> Unit) {
        val call = service.getAll()
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


    //Irá retornar os detalhes do pokemon
    //@param pokeId String - id do pokemon que extraimos da url no primeiro service

    fun fetchPokemonDetails(pokeId: String, onComplete: (PokemonDetails?, String?) -> Unit){
        val call = service.getDetails(pokeId)
        call.enqueue(object : Callback<PokemonDetails>{
            override fun onResponse(call: Call<PokemonDetails>, response: Response<PokemonDetails>){
                if (response.body() != null) {
                    onComplete(response.body(), null)
                } else {
                    onComplete(null, "Pokemon não encontrado")
                }
            }
            override fun onFailure(call: Call<PokemonDetails>, t: Throwable) {
                onComplete(null, t.message)
            }
        })
    }

    // Função que irá receber uma lista de Pokemon e irá add no database local
    fun insertIntoDatabase(items: List<Pokemon>) {
        val dao = database.pokemonDAO()
        items.forEach { poke ->
            dao.insert(pokemon = poke)
        }

    }

    // Função que irá receber Pokemon e irá add no database local
    fun insertIntoDatabase(pokemon: Pokemon) {
        val dao = database.pokemonDAO()
        dao.insert(pokemon)
    }

    // Buscamos todos os Pokemons que já estão dentro do database local
    fun fetchAllFromDatabase(): List<Pokemon>? {
        val dao = database.pokemonDAO()
        return dao.all()
    }

    //buscamos os Pokemons que ja estao salvos no banco local, e chamamos a função de filtro
    //que foi criada no DAO
    fun fetchAllFromDataBaseWithFilter(query: String): List<Pokemon>?{
        val dao = database.pokemonDAO()
        return dao.fetchFiltered(query.lowercase())
    }


}