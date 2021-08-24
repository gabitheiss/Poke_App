package com.example.poke_app.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.poke_app.model.Pokemon


@Dao
interface PokemonDAO {

    //select para retornar todos
    @Query("SELECT * FROM Pokemon ORDER BY poke_name" )
    fun all() : List<Pokemon>

    //select para retornar somente um
    @Query("SELECT * FROM Pokemon WHERE poke_name = :pokeId" )
    fun byId(pokeId : String) : Pokemon

    //insert para inserir no banco
    //onConflict server para validar quando o insert identifica algum resigtro duplicado
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pokemon: Pokemon)

}