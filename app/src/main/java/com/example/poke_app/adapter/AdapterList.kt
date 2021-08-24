package com.example.poke_app.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.poke_app.R
import com.example.poke_app.databinding.ItensListPokemonBinding
import com.example.poke_app.model.Pokemon
import com.example.poke_app.utils.toUpperFirstChar


class AdapterList(var listOfPokemons: MutableList<Pokemon>) :
    RecyclerView.Adapter<PokemonViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.itens_list_pokemon, parent, false)
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


class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItensListPokemonBinding.bind(itemView)


    @SuppressLint("ResourceType")
    fun bind(pokemon: Pokemon) {

        binding.idName.text = pokemon.name.toUpperFirstChar()
        binding.idId.text = "#${pokemon.extractIdFromUrl()}"

        pokemon.details?.let {
            Glide.with(itemView.context)
                .load(it.sprites.other?.artWork?.image)
                .into(binding.idImagem)


            val pokeTypeSetup = it.type[0].type.extractPokeSetup()
            binding.idCard.setCardBackgroundColor(itemView.context.getColor(pokeTypeSetup.colorCard))
            binding.typesContainer.typeCardView1.setCardBackgroundColor(
                itemView.context.getColor(
                    pokeTypeSetup.colorType
                )
            )
            binding.typesContainer.typeImageView1.setImageDrawable(
                itemView.context.getDrawable(
                    pokeTypeSetup.icon
                )
            )
            binding.typesContainer.typeTextView1.text = it.type[0].type.typeName.toUpperFirstChar()

            if (it.type.size > 1) {
                val setupCard2 = it.type[1].type.extractPokeSetup()
                binding.typesContainer.typeCardView2.setCardBackgroundColor(
                    itemView.context.getColor(
                        setupCard2.colorType
                    )
                )
                binding.typesContainer.typeImageView2.setImageDrawable(
                    itemView.context.getDrawable(
                        setupCard2.icon
                    )
                )
                binding.typesContainer.typeTextView2.text =
                    it.type[1].type.typeName.toUpperFirstChar()
                binding.typesContainer.typeCardView2.visibility = View.VISIBLE
            } else {
                binding.typesContainer.typeCardView2.visibility = View.GONE
            }
        }
    }
}



