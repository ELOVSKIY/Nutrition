package com.helicoptera.nutrition.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.OnConflictStrategy
import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.helicoptera.nutrition.domain.MealType

@Entity(tableName = "meal")
data class MealEntity(

    @PrimaryKey(autoGenerate = false)
    @OnConflictStrategy
    @ColumnInfo(name = "type")
    val type: MealType,

    @Embedded(prefix = "portion_")
    val portion: MealPortionEntity? = null
)