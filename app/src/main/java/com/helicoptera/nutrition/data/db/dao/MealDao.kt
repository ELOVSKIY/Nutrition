package com.helicoptera.nutrition.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.helicoptera.nutrition.data.db.entity.MealEntity

@Dao
interface MealDao {

    @Query("SELECT * FROM meal")
    fun meals(): LiveData<List<MealEntity>>

    @Query("SELECT * FROM meal")
    suspend fun fetchMeals(): List<MealEntity>

    @Query("SELECT count(*) FROM meal")
    suspend fun fetchMealCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(mealEntity: MealEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeals(meals: List<MealEntity>)
}