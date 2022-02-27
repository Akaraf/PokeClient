package com.raaf.pokeclient.data.databases.favoritePokemon

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.raaf.pokeclient.data.dataModels.Pokemon
import com.raaf.pokeclient.data.databases.BaseDao
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

@Dao
interface FavoritePokemonDao : BaseDao<Pokemon> {

    @Query("SELECT * FROM pokemon WHERE  id = :id")
    fun checkItem(id: Int) : Flowable<List<Pokemon>>

    @Transaction @Query("SELECT * FROM pokemon")
    fun getAll() : Single<List<Pokemon>>
}