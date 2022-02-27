package com.raaf.pokeclient.di.modules

import com.raaf.pokeclient.data.api.PokeService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PokeServiceModule {

    @Provides
    @Singleton
    fun provideWebService() : PokeService {
        return PokeService.create()
    }
}