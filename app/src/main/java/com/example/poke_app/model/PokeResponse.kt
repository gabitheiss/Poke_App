package com.example.poke_app.model

data class PokeResponse(val results : List<Pokemon>)

data class Pokemon(
    val name: String,
    val url: String
)
