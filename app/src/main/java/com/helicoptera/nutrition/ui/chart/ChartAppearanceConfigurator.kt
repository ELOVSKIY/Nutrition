package com.helicoptera.nutrition.ui.chart

import com.github.mikephil.charting.charts.LineChart

class ChartAppearanceConfigurator {

    fun configureChartAppearance(chart: LineChart) {
        chart.apply {
            isDragEnabled = false
            setScaleEnabled(false)

            for (axis in listOf(xAxis, axisRight, axisLeft)) {
                axis.isEnabled = false
            }

            description.isEnabled = false
            legend.isEnabled = false
        }
    }
}