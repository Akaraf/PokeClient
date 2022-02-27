package com.raaf.pokeclient.data.dataModels.converters

import androidx.room.TypeConverter
import com.raaf.pokeclient.data.dataModels.Home
import com.raaf.pokeclient.data.dataModels.Other
import com.raaf.pokeclient.data.dataModels.PokemonSprites

class PokemonSpritesConverter {

    @TypeConverter
    fun fromObject(sprites: PokemonSprites) : String {
        return sprites.other.home.front_default
    }

    @TypeConverter
    fun toObject(string: String) : PokemonSprites {
        return PokemonSprites(Other(Home(string)))
    }
}