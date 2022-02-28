package com.raaf.pokeclient.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raaf.pokeclient.App
import com.raaf.pokeclient.R
import com.raaf.pokeclient.data.dataModels.Pokemon
import com.raaf.pokeclient.data.databases.extensions.removeFromFavorite
import com.raaf.pokeclient.ui.adapters.FavoriteGridAdapter
import com.raaf.pokeclient.ui.extensions.lazyViewModel
import com.raaf.pokeclient.utils.setTitle
import com.raaf.pokeclient.viewModels.FavoritesViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class FavoritesFragment : Fragment(), FavoriteGridAdapter.OnDeleteListener {

    val favoriteVM: FavoritesViewModel by lazyViewModel {
        App.pokeComponent.favoriteViewModel().create(it)
    }

    private lateinit var favoriteRV: RecyclerView
    private lateinit var favoriteAdapter: FavoriteGridAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        favoriteRV = view.findViewById(R.id.favorite_r_v)
        favoriteRV.layoutManager = GridLayoutManager(this.requireContext(),2)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteVM.favoriteDao.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                val favorites = response.toMutableSet()
                favoriteAdapter = FavoriteGridAdapter(favorites)
                favoriteAdapter.setOnDeleteClickListener(this)
                favoriteRV.adapter = favoriteAdapter
            }
    }

    override fun onResume() {
        super.onResume()
        setTitle(requireActivity().findViewById(R.id.toolbar), getString(R.string.favorites_fragment_label))
    }

    override fun onDelete(pokemon: Pokemon?, position: Int?) {
        favoriteAdapter.onItemDismiss(position)
        pokemon?.let { favoriteVM.favoriteDao.removeFromFavorite(it) }
    }
}