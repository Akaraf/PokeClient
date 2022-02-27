package com.raaf.pokeclient.di.modules

import com.raaf.pokeclient.data.RandomPokemonProvider
import com.raaf.pokeclient.data.api.PokeService
import dagger.Module
import dagger.Provides

@Module(includes = arrayOf(PokeServiceModule::class))
class RandomPokemonProviderModule {

    @Provides
    fun provideRandomPokemonProvider(apiService: PokeService) : RandomPokemonProvider {
        return RandomPokemonProvider(apiService)
    }
}