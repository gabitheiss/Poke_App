package com.example.poke_app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.poke_app.model.Pokemon

@Database(entities = [Pokemon::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null


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
