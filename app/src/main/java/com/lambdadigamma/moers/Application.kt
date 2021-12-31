package com.lambdadigamma.moers

import android.app.Application

class Application : Application() {

    companion object {

        lateinit var instance: com.lambdadigamma.moers.Application
            private set

        const val sharedPreferencesName = "MMAPI"

    }

    override fun onCreate() {
        super.onCreate()

        instance = this


    }

}