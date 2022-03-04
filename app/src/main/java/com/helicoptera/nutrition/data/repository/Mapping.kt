package com.helicoptera.nutrition.data.repository

import com.helicoptera.nutrition.data.db.entity.MealEntity
import com.helicoptera.nutrition.data.db.entity.MealPortionEntity
import com.helicoptera.nutrition.domain.Meal
import com.helicoptera.nutrition.domain.MealPortion

fun MealEntity.toDomain() : Meal {
    return Meal(this.type, this.portion.toDomain())
}

fun Meal.toEntity() : MealEntity {
    return MealEntity(this.type, this.portion.toEntity())
}

fun MealPortionEntity?.toDomain() : MealPortion? {
    return if (this != null) MealPortion(this.kcal, this.time) else null
}

fun MealPortion?.toEntity() : MealPortionEntity? {
    return if (this != null) MealPortionEntity(this.kcal, this.time) else null
}