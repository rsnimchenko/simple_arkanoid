package com.rsnimchenko.simple_arkanoid

import android.app.Application
import com.rsnimchenko.simple_arkanoid.util.SharedPrefUtil

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPrefUtil.init(this)
    }
}