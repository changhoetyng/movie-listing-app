package com.example.testingkotlin

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

object AppExecutors {
    val mNetworkIO : ScheduledExecutorService = Executors.newScheduledThreadPool(3)
}