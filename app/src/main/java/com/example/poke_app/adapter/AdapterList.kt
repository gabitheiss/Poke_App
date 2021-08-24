package com.example.poke_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.poke_app.R
import com.example.poke_app.databinding.ItensListPokemonBinding
import com.example.poke_app.model.Pokemon
import com.example.poke_app.utils.toUpperFirstChar


class AdapterList(var listOfPokemons : MutableList<Pokemon>):
RecyclerView.Adapter<PokemonViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itens_list_pokemon, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        listOfPokemons[position].apply {
            holder.bind(this)
        }
    }

    override fun getItemCount(): Int {
        return listOfPokemons.size
    }

    fun refresh(pokemons: List<Pokemon>) {
        listOfPokemons = mutableListOf()
        listOfPokemons.addAll(pokemons)
        notifyDataSetChanged()
    }

}


class PokemonViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){

    private val binding = ItensListPokemonBinding.bind(itemView)

    fun bind(pokemon : Pokemon){

        binding.idName.text = pokemon.name.toUpperFirstChar()
        binding.idId.text = "#${pokemon.extractIdFromUrl()}"

        pokemon.details?.let{
            Glide.with(itemView.context)
                .load(it.sprites.other?.artWork?.image)
                .into(binding.idImagem)
        }
    }

}