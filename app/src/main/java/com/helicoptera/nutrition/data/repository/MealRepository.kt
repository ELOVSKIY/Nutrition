package com.helicoptera.nutrition.data.repository

import android.content.Context
import androidx.lifecycle.Transformations
import com.helicoptera.nutrition.data.db.MealDatabase
import com.helicoptera.nutrition.domain.Meal

//TODO Без интернета служит просто прослойкой мапинга
class MealRepository(appContext: Context) {

    private val mealDao = MealDatabase.getInstance(appContext).mealDao

    val meals = Transformations.map(mealDao.meals()) { mealInfoEntities ->
        return@map mealInfoEntities.map { mealInfoEntity ->
            mealInfoEntity.toDomain()
        }
    }

    suspend fun fetchMeals() : List<Meal> {
        return mealDao.fetchMeals().map { it.toDomain() }
    }

    suspend fun insertMeal(meal: Meal) {
        mealDao.insertMeal(meal.toEntity())
    }

    suspend fun insertMeals(meals: List<Meal>) {
        mealDao.insertMeals(meals.map { it.toEntity() })
    }

    suspend fun fetchMealCount(): Int {
        return mealDao.fetchMealCount()
    }
}