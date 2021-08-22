package com.example.poke_app.view_model

import androidx.lifecycle.ViewModel
import com.example.poke_app.repository.PokeRepository

class MainViewModel : ViewModel() {


    fun getPokemons() {
        PokeRepository.getPokemons() { pokeResponse, error ->
            print("")
        }


    }
}