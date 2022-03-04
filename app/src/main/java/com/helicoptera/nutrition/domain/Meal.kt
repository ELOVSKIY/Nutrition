package com.helicoptera.nutrition.domain

data class Meal(
    val type: MealType,
    val portion: MealPortion? = null
)