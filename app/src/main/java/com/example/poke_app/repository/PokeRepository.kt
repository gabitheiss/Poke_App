package com.example.poke_app.repository

import android.content.Context
import com.example.poke_app.database.AppDataBase
import com.example.poke_app.model.PokeResponse
import com.example.poke_app.model.Pokemon
import com.example.poke_app.model.PokemonDetails
import com.example.poke_app.model.RetrofitBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokeRepository(private val context: Context) {


    private val database = AppDataBase.getDataBase(context)
    val service = RetrofitBuilder.getPokeService()


    suspend fun fetchAll() : List<Pokemon>? {

        return withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
            val response = service.getAll()
            val responsePokemon = processData(response)
            responsePokemon?.results?.forEach {
                fetchPokemonDetails(it.extractIdFromUrl())?.let { details ->
                    it.details = details
                }
            }
            responsePokemon?.results

        }
    }

    private fun <T> processData(response: Response<T>): T? {
        return if (response.isSuccessful) response.body() else null
    }

    private suspend fun fetchPokemonDetails(pokeId: String) : PokemonDetails? {
        return withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
            val response = service.getDetails(pokeId)
            processData(response)
        }
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