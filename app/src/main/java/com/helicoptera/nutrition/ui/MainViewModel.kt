package com.helicoptera.nutrition.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.helicoptera.nutrition.data.repository.MealRepository
import com.helicoptera.nutrition.date.DateTimeProvider
import com.helicoptera.nutrition.domain.Meal
import com.helicoptera.nutrition.domain.MealPortion
import com.helicoptera.nutrition.domain.MealType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(appContext: Context) : ViewModel() {

    private val repository = MealRepository(appContext)

    init {
        viewModelScope.launch {
            val mealCount = repository.fetchMealCount()
            if (mealCount == 0) {
                for (mealType in MealType.values()) {
                    val meal = Meal(mealType)

                    updateMeal(meal)
                }
            }
        }
    }

    private val _meals = repository.meals
    val meals: LiveData<List<Meal>>
        get() = repository.meals

    private val _caloriesGoal = MutableLiveData(CALORIES_GOAL)
    val caloriesGoal: LiveData<String>
        get() = Transformations.map(_caloriesGoal) { it.toString() }

    private val _eating = Transformations.map(_meals) { meals ->
        var kcalSum = 0
        meals.forEach { meal ->
            meal.portion?.let { portion ->
                kcalSum += portion.kcal
            }
        }

        return@map kcalSum
    }

    val eating: LiveData<String>
        get() = Transformations.map(_eating) { it.toString() }

    private val _burn = MutableLiveData(BURN)
    val burn: LiveData<String>
        get() = Transformations.map(_burn) { it.toString() }

    private val _total = Transformations.map(_eating) { eating ->
        return@map eating - (_burn.value ?: 0)
    }

    val total: LiveData<String>
        get() = Transformations.map(_total) { it.toString() }


    val kcal = MutableLiveData("")

    private val selectedMealType = MutableLiveData<MealType?>(null)
    val openDialogEvent: LiveData<Boolean>
        get() = Transformations.map(selectedMealType) { mealType ->
            return@map mealType != null
        }

    private val _dismissDialogEvent = MutableLiveData(false)
    val dismissDialogEvent: LiveData<Boolean>
        get() = _dismissDialogEvent

    fun selectMeal(mealType: MealType) {
        selectedMealType.value = mealType
    }


    fun submit() {
        kcal.value?.let { kcalStr ->
            val kcal = kcalStr.toIntOrNull()
            if (kcal != null) {
                setKcal(kcal)
            }
        }

        dismiss()
    }

    fun dismiss() {
        selectedMealType.value = null
        kcal.value = ""
        _dismissDialogEvent.value = true
    }

    fun onDialogDismissed() {
        selectedMealType.value = null
        _dismissDialogEvent.value = false
    }

    private fun setKcal(kcal: Int) {
        selectedMealType.value?.let { mealType ->
            val currentTime = DateTimeProvider.getCurrentTime()
            val mealPortion = MealPortion(kcal, currentTime)
            val meal = Meal(mealType, mealPortion)

            updateMeal(meal)
        }

        selectedMealType.value = null
    }

    private fun updateMeal(meal: Meal) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertMeal(meal)
        }
    }

    companion object {
        private const val CALORIES_GOAL = 1450
        private const val BURN = 300
    }
}