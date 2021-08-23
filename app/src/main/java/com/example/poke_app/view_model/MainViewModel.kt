package com.example.poke_app.view_model

import androidx.lifecycle.ViewModel
import com.example.poke_app.repository.PokeRepository

class MainViewModel : ViewModel() {


    private val pokemonRepository = PokeRepository()

    fun getPokemons() {
        pokemonRepository.getPokemons() { pokeResponse, error ->
            print("")
        }


    }
}