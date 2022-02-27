package com.raaf.pokeclient

import android.app.Application
import android.content.res.Configuration
import com.raaf.pokeclient.di.components.DaggerPokeComponent
import com.raaf.pokeclient.di.components.PokeComponent
import leakcanary.LeakCanary

class App : Application() {

    companion object {
        lateinit var pokeComponent: PokeComponent
    }

    override fun onCreate() {
        super.onCreate()
        LeakCanary.config = LeakCanary.config.copy(retainedVisibleThreshold = 3)
        pokeComponent = DaggerPokeComponent.builder()
            .application(this)
            .build()
    }
}