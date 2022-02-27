package com.raaf.pokeclient.di.modules

import android.app.Application
import androidx.room.Room
import com.raaf.pokeclient.data.databases.favoritePokemon.FavoritePokemonDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FavoritePokemonDatabaseModule {

    @Provides
    @Singleton
    fun provideFavoritePokemonDatabase(app: Application) : FavoritePokemonDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            FavoritePokemonDatabase::class.java,
            "favoritePokemonDatabase"
        ).build()
    }
}