package com.example.poke_app.view_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.poke_app.model.Pokemon
import com.example.poke_app.repository.PokeRepository

class MainViewModel : ViewModel() {


    val _pokeResponse = MutableLiveData<List<Pokemon>>()
    val pokeResponse : LiveData<List<Pokemon>> = _pokeResponse

    val _error = MutableLiveData<String>()
    var error : LiveData<String> = _error



    // Função que chamamos somente interna dentro do viewmodel.
    // Irá buscar da API a lista de pokemons. Após receber os dados
    // insere no banco local a lista que recebeu.

    fun fetchAllFromServer(context: Context) {
        val repository = PokeRepository(context)
        repository.fetchAll { response, error ->
            response?.let {
                _pokeResponse.value = it.results

                loadPokeDetails(it.results, repository)
            }
            error?.let {
                _error.value = it
            }
        }
    }

    private fun loadPokeDetails(pokemons: List<Pokemon>, repository: PokeRepository) {
        pokemons.forEach { poke ->
            repository.fetchPokemonDetails(pokeId = poke.extractIdFromUrl()) { details, _ ->
                details?.let {

                    poke.details = details
                    repository.insertIntoDatabase(poke)

                }
            }
        }
    }


    // Função criada para verificar se busca os dados do database local ou da API.
    // Esta é a função que sempre será chamada da nossa view.
    fun fetchAllFromDatabase(context: Context) {
        val listOf = PokeRepository(context).fetchAllFromDatabase()
        if (listOf != null && listOf.isNotEmpty()) {
            _pokeResponse.value = listOf!!
        } else {
            fetchAllFromServer(context)
        }

    }
}