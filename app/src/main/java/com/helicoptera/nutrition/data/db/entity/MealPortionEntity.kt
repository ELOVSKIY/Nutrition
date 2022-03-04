package com.helicoptera.nutrition.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.util.Date

@Entity(tableName = "meal_portion")
data class MealPortionEntity(

    @ColumnInfo(name = "kcal")
    val kcal: Int,

    @ColumnInfo(name = "time")
    val time: Date
)