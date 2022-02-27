package com.raaf.pokeclient.di.components

import android.app.Application
import com.raaf.pokeclient.di.modules.FavoritePokemonDatabaseModule
import com.raaf.pokeclient.di.modules.PokeServiceModule
import com.raaf.pokeclient.di.modules.RandomPokemonProviderModule
import com.raaf.pokeclient.viewModels.RandomPokemonViewModel
import com.raaf.pokeclient.viewModels.SearchViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = arrayOf(
    PokeServiceModule::class,
    FavoritePokemonDatabaseModule::class,
    RandomPokemonProviderModule::class
))
@Singleton
interface PokeComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder
        fun build(): PokeComponent
    }

    fun searchViewModel() : SearchViewModel.Factory
    fun randomViewModel() : RandomPokemonViewModel.Factory
}