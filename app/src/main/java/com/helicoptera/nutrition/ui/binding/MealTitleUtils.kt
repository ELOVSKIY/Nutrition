package com.helicoptera.nutrition.ui.binding

import android.widget.TextView
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import com.helicoptera.nutrition.R
import com.helicoptera.nutrition.domain.MealType

@BindingAdapter("title")
fun TextView.setMealTypeTitle(mealType: MealType) {
    val stringRes = getResIdByMealType(mealType)
    this.setText(stringRes)
}

@StringRes
private fun getResIdByMealType(mealType: MealType) : Int{
    return when(mealType) {
        MealType.BREAKFAST -> R.string.breakfast
        MealType.LUNCH -> R.string.lunch
        MealType.DINNER -> R.string.dinner
    }
}