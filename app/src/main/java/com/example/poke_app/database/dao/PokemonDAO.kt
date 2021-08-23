package com.example.poke_app.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.poke_app.model.Pokemon


@Dao
interface PokemonDAO {

    @Query("SELECT * FROM Pokemon ORDER BY poke_name" )
    fun all() : List<Pokemon>

    @Insert
    fun insert(pokemon: Pokemon)

}