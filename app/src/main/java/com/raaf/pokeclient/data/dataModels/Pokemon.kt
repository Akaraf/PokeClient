package com.raaf.pokeclient.data.dataModels

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.raaf.pokeclient.data.dataModels.converters.PokemonSpritesConverter

@Entity(tableName = "pokemon")
@TypeConverters(PokemonSpritesConverter::class)
data class Pokemon(
    @PrimaryKey
    val id: Int,
    val name: String,
    @SerializedName("base_experience")
    val baseExperience: Int,
    val height: Int,
    val weight: Int,
    val sprites: PokemonSprites
)