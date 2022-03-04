package com.helicoptera.nutrition.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.helicoptera.nutrition.databinding.ActivityMainBinding
import com.helicoptera.nutrition.databinding.InsertDialogBinding
import com.helicoptera.nutrition.domain.Meal
import com.helicoptera.nutrition.domain.MealType
import com.helicoptera.nutrition.ui.chart.ChartAppearanceConfigurator
import com.helicoptera.nutrition.ui.chart.DataSetAppearanceConfigurator
import com.helicoptera.nutrition.ui.chart.NonZeroValueFormatter
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val chartAppearanceConfigurator = ChartAppearanceConfigurator()
    private val dataSetAppearanceConfigurator = DataSetAppearanceConfigurator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        val viewModel: MainViewModel by viewModels() {
            MainViewModelFactory(this.applicationContext)
        }

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, com.helicoptera.nutrition.R.layout.activity_main)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = MealAdapter(object : MealSelectListener {
            override fun onMealSelected(mealType: MealType) {
                viewModel.selectMeal(mealType)
            }
        })
        binding.recycler.adapter = adapter

        chartAppearanceConfigurator.configureChartAppearance(binding.chart)

        viewModel.meals.observe(this) {
            adapter.submitList(it.toMutableList())

            updateChart(binding, it)
        }
        viewModel.openDialogEvent.observe(this) { open ->
            if (open) {
                showDialog(viewModel)
            }
        }
    }

    private fun updateChart(
        binding: ActivityMainBinding,
        mealsInfo: List<Meal>
    ) {
        binding.chart.apply {
            val caloriesPerPeriod = IntArray(DAY_PERIOD_COUNT) { 0 }
            mealsInfo.forEach { mealsInfo ->
                mealsInfo.portion?.apply {
                    val hour = time.hours
                    val period = hour /
                            (TimeUnit.DAYS.toHours(1).toInt() / DAY_PERIOD_COUNT)

                    caloriesPerPeriod[period] += kcal
                }
            }

            val entries = mutableListOf<Entry>()
            for (hour in caloriesPerPeriod.indices) {
                entries.add(Entry(hour.toFloat(), caloriesPerPeriod[hour].toFloat()))
            }

            val dataSet = LineDataSet(entries, "")
            dataSetAppearanceConfigurator.configureDataSetAppearance(dataSet)

            val dataSets = mutableListOf<ILineDataSet>()
            dataSets.add(dataSet)

            val data = LineData(dataSets)
            data.setValueFormatter(NonZeroValueFormatter())
            setData(data)
            invalidate()

        }
    }

    private fun showDialog(viewModel: MainViewModel) {
        val dialog = Dialog(this)
        val binding = InsertDialogBinding.inflate(LayoutInflater.from(this))
        binding.viewModel = viewModel

        val dismissObserver = Observer<Boolean> { dismiss ->
            if (dismiss) {
                dialog.dismiss()
            }
        }

        viewModel.dismissDialogEvent.observe(this, dismissObserver)

        dialog.apply {
            setOnDismissListener {
                viewModel.dismissDialogEvent.removeObserver(dismissObserver)
                viewModel.onDialogDismissed()
            }
            setContentView(binding.root)
            show()
        }
    }

    companion object {
        private const val DAY_PERIOD_COUNT = 8
    }
}