package com.example.poke_app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.poke_app.database.dao.PokemonDAO
import com.example.poke_app.model.*

@Database(entities = [Pokemon::class, PokemonDetails::class, Sprites::class, Other::class, ArtWork::class], version = 1)

abstract class AppDataBase : RoomDatabase() {

    abstract fun pokemonDAO() : PokemonDAO

    companion object {

        fun getDataBase(context: Context): AppDataBase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                "pokemon_app_db"
            )
                .allowMainThreadQueries()
                .build()
        }
    }
}
