package com.example.poke_app.view_model

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

    private val pokemonRepository = PokeRepository()

    fun getPokemons() {
        pokemonRepository.getPokemons() { pokeResponse, error ->
            pokeResponse?.let {
                _pokeResponse.value = it.results
            }
            error?.let {
                _error.value = it
            }
        }


    }
}