package com.raaf.pokeclient.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.raaf.pokeclient.R
import com.raaf.pokeclient.data.dataModels.Pokemon
import com.raaf.pokeclient.utils.fillUI
import com.raaf.pokeclient.utils.isAddingToFavorite

class FavoriteGridAdapter(private val pokemons: MutableSet<Pokemon>) :
    RecyclerView.Adapter<FavoriteGridAdapter.PokemonGridViewHolder>() {

    interface OnDeleteListener {
        fun onDelete(pokemon: Pokemon?, position: Int?)
    }

    private var deleteListener: OnDeleteListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonGridViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.layout_pokemon_small, parent, false)
        return PokemonGridViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonGridViewHolder, position: Int) {
        val item = pokemons.elementAt(position)
        fillUI(
            holder.pokemonLayout,
            holder.pokemonIV,
            holder.pokemonNameTV,
            holder.pokemonIdTV,
            holder.pokemonCharacteristicTV,
            holder.favoriteTV,
            item
        )
        isAddingToFavorite(true, holder.favoriteTV)
        holder.deleteListener = deleteListener
        holder.pokemon = item
        holder.pos = position
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemCount(): Int = pokemons.size

    fun onItemDismiss(position: Int?) {
        if (position == null) return
        pokemons.remove(pokemons.elementAt(position))
        notifyItemRemoved(position)
    }

    fun setOnDeleteClickListener(listener: OnDeleteListener) {
        deleteListener = listener
    }

    inner class PokemonGridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener
    {

        val pokemonLayout: CardView = itemView.findViewById(R.id.pokemon_layout)
        val pokemonIV: ImageView = itemView.findViewById(R.id.pokemon_i_v)
        val pokemonNameTV: TextView = itemView.findViewById(R.id.pokemon_name_t_v)
        val pokemonIdTV: TextView = itemView.findViewById(R.id.pokemon_id_t_v)
        val pokemonCharacteristicTV: TextView = itemView.findViewById(R.id.pokemon_characteristics_t_v)
        val favoriteTV: TextView = itemView.findViewById(R.id.favorite_t_v)
        var pos: Int? = null
        var pokemon: Pokemon? = null
        var deleteListener: OnDeleteListener? = null

        init {
            favoriteTV.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            deleteListener?.onDelete(pokemon, pos)
        }
    }
}