package com.helicoptera.nutrition

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.helicoptera.nutrition.date.DateTimeProvider.getMinutesToMidnight
import com.helicoptera.nutrition.model.CleanWorker
import java.util.concurrent.TimeUnit

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val cleanWorkRequest =
            PeriodicWorkRequestBuilder<CleanWorker>(1, TimeUnit.DAYS)
                .setInitialDelay(getMinutesToMidnight(), TimeUnit.MINUTES)
                .build()

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                CLEAN_WORKER,
                ExistingPeriodicWorkPolicy.KEEP,
                cleanWorkRequest
            )
    }

    companion object {
        private const val CLEAN_WORKER = "clean_worker"
    }
}