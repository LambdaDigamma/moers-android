package com.lambdadigamma.moers

import android.app.Application

class App: Application() {

    companion object {
        lateinit var instance: App
            private set
        const val preferenceName = "moers"
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

    }

}
