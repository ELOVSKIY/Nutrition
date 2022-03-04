package com.helicoptera.nutrition.ui

import com.helicoptera.nutrition.domain.MealType

interface MealSelectListener {

    fun onMealSelected(mealType: MealType)
}