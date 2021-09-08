package com.example.poke_app.view_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poke_app.model.Pokemon
import com.example.poke_app.repository.PokeRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {


    val _pokeResponse = MutableLiveData<List<Pokemon>>()
    val pokeResponse : LiveData<List<Pokemon>> = _pokeResponse

    val _error = MutableLiveData<String>()
    var error : LiveData<String> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading



    // Função que chamamos somente interna dentro do viewmodel.
    // Irá buscar da API a lista de pokemons. Após receber os dados
    // insere no banco local a lista que recebeu.

    fun fetchAllFromServer(context: Context) {
        val repository = PokeRepository(context)
        _isLoading.value = true

        viewModelScope.launch {
            repository.fetchAll()?.let { pokemons ->
                _isLoading.value = false
                _pokeResponse.value = pokemons
            }
        }
    }


    // Função criada para verificar se busca os dados do database local ou da API.
    // Esta é a função que sempre será chamada da nossa view.
    fun fetchAllFromDatabase(context: Context) {
        val listOf = PokeRepository(context).fetchAllFromDatabase()
        if (listOf != null && listOf.isNotEmpty()) {
            _pokeResponse.value = listOf!!
        } else {
            fetchAllFromServer(context)
        }

    }


    //funcao que busca pokemons do banco de dados

//    fun fetchFilteredFromDataBase(context: Context, query:String){
//        val repository = PokeRepository(context)
//        val filteredList = repository.fetchAllFromDataBaseWithFilter(query)
//        filteredList?.let{
//            _pokeResponse.value = it
//        }
//
//    }
}