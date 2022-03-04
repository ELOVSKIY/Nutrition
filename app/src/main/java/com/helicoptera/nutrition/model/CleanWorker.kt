package com.helicoptera.nutrition.model

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.helicoptera.nutrition.data.repository.MealRepository

class CleanWorker(private val appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val repository = MealRepository(appContext)

        val meals = repository.fetchMeals()
        val mealsWithoutPortion = meals.map { it.copy(portion = null) }
        repository.insertMeals(mealsWithoutPortion)

        return Result.success()
    }
}