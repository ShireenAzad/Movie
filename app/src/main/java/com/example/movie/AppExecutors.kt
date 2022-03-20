package com.example.movie

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

class AppExecutors {
    private var instance: AppExecutors? = null
    fun getInstance(): AppExecutors? {
        if (instance == null) {
            instance = AppExecutors()
        }
        return instance
    }

    private val networkIO = Executors.newScheduledThreadPool(3)
    fun getNetworkIO(): ScheduledExecutorService? {
        return networkIO
    }


}