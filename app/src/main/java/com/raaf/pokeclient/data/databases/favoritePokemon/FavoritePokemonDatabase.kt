package com.raaf.pokeclient.data.databases.favoritePokemon

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raaf.pokeclient.data.dataModels.Pokemon

@Database(entities = arrayOf(Pokemon::class), version = 1, exportSchema = false)
abstract class FavoritePokemonDatabase : RoomDatabase() {

    abstract fun favoritePokemonDao() : FavoritePokemonDao
}