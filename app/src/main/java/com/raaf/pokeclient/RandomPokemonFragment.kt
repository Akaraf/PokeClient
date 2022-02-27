package com.raaf.pokeclient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.raaf.pokeclient.data.dataModels.Pokemon
import com.raaf.pokeclient.extensions.lazyViewModel
import com.raaf.pokeclient.utils.clearUI
import com.raaf.pokeclient.utils.fillUI
import com.raaf.pokeclient.utils.isAddingToFavorite
import com.raaf.pokeclient.utils.setTitle
import com.raaf.pokeclient.viewModels.RandomPokemonViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class RandomPokemonFragment : Fragment(), View.OnClickListener {

    val randomVM: RandomPokemonViewModel by lazyViewModel { 
        App.pokeComponent.randomViewModel().create(it)
    }
    private lateinit var randomPokemonLayout: CardView
    private lateinit var pokemonIV: ImageView
    private lateinit var pokemonNameTV: TextView
    private lateinit var pokemonIdTV: TextView
    private lateinit var pokemonCharacteristicTV: TextView
    private lateinit var favoriteTV: TextView
    private lateinit var randomButton: Button

    private var isPokemonInFavorite = false
    private var currentPokemon: Pokemon? = null
    private var favoriteStatusDisposable: Disposable? = null
    private var randomDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        randomVM
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_random_pokemon, container, false)
        randomButton = view.findViewById(R.id.random_pokemon_button)
        randomButton.setOnClickListener(this)
        randomPokemonLayout = view.findViewById(R.id.layout_random_pokemon)
        pokemonIV = randomPokemonLayout.findViewById(R.id.pokemon_i_v)
        pokemonNameTV = randomPokemonLayout.findViewById(R.id.pokemon_name_t_v)
        pokemonIdTV = randomPokemonLayout.findViewById(R.id.pokemon_id_t_v)
        pokemonCharacteristicTV = randomPokemonLayout
            .findViewById(R.id.pokemon_characteristics_t_v)
        favoriteTV = randomPokemonLayout.findViewById(R.id.favorite_t_v)
        favoriteTV.setOnClickListener(this)
        return view
    }

    override fun onResume() {
        super.onResume()
        makeRandomAction(randomVM.savedPokemon)
        makeRandomAction(currentPokemon)
        setTitle(requireActivity().findViewById(
            R.id.toolbar),
            getString(R.string.menu_fragment_label)
        )
        observeRandomPokemon()
    }

    override fun onStop() {
        super.onStop()
        randomVM.saveRandomId(currentPokemon)
        dispose()
    }

    override fun onClick(p0: View?) {
        when (p0) {
            randomButton -> {
                randomVM.getRandomPokemon()
            }
            favoriteTV -> {
                if (isPokemonInFavorite) currentPokemon?.let { randomVM.removeFromFavorite(it) }
                else currentPokemon?.let { randomVM.addToFavorite(it) }
                isPokemonInFavorite = !isPokemonInFavorite
            }
        }
    }

    private fun makeRandomAction(pokemon: Pokemon?) {
        if (pokemon == null) return
        currentPokemon = pokemon
        clearUI(
            randomPokemonLayout,
            pokemonIV,
            pokemonNameTV,
            pokemonIdTV,
            pokemonCharacteristicTV,
            favoriteTV,
        )
        fillUI(
            randomPokemonLayout,
            pokemonIV,
            pokemonNameTV,
            pokemonIdTV,
            pokemonCharacteristicTV,
            favoriteTV,
            pokemon
        )
    }

    private fun observeRandomPokemon() {
        randomDisposable = randomVM.getObservableRandomPokemon()
            .subscribeOn(Schedulers.computation())
            .subscribe { response->
                makeRandomAction(response)
                response?.id?.let { observePokemonInFavorite(it) }
            }
    }

    private fun observePokemonInFavorite(id: Int) {
        favoriteStatusDisposable?.dispose()
        favoriteStatusDisposable = randomVM.dao.checkItem(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                if (response != null) isPokemonInFavorite = response.isNotEmpty()
                isAddingToFavorite(isPokemonInFavorite, favoriteTV)
            }
    }

    private fun dispose() {
        favoriteStatusDisposable?.dispose()
        favoriteStatusDisposable = null
        randomDisposable?.dispose()
        randomDisposable = null
    }
}