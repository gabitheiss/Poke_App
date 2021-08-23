package com.example.poke_app.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class PokeResponse(val results : List<Pokemon>)


//Entity para declarar que é uma entidade - tabela no banco
@Entity
data class Pokemon(
    //usamos a chave primaria com o nome pois a api nao trás o id, mas se tiver nomes repetidos
    //não dará certo, a chave primaria tem que ser única - recomendado ID
    @PrimaryKey
    @ColumnInfo(name = "poke_name")
    @SerializedName("name")
    val name: String,

    @ColumnInfo(name = "poke_url")
    @SerializedName("url")
    val url: String
){
    fun extractIdFromUrl(): String{
        val listStr = url.split("/")
        return listStr[6]
    }

}
