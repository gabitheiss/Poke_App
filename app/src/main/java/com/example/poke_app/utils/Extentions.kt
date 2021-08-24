package com.example.poke_app.utils

//deixar a primeira letra maiuscula dos nomes dos pokemons

fun String.toUpperFirstChar(): String {
    return replaceFirstChar { it.uppercase() }
}