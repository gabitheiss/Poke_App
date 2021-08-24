package com.example.poke_app.database

import androidx.room.TypeConverter
import com.example.poke_app.model.Types
import com.google.gson.Gson

class Converters {


    //converter a lista de objetos para json - para o banco entender
    @TypeConverter
    fun listToJson(value: List<Types>?) = Gson().toJson(value)


    //converter json vindo do banco para lista de objetos, para usarmos no app posteriormente
    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Types>::class.java).toList()

}