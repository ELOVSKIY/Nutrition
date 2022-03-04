package com.helicoptera.nutrition.ui.chart

import android.graphics.Color
import com.github.mikephil.charting.data.LineDataSet

class DataSetAppearanceConfigurator {

    fun configureDataSetAppearance(dataSet: LineDataSet) {
        dataSet.apply {
            setDrawCircles(false)
            lineWidth = CHART_LINE_WIDTH
            mode = LineDataSet.Mode.HORIZONTAL_BEZIER
            valueTextSize = TEXT_SIZE
            valueTextColor = Color.GRAY
        }
    }

    companion object {
        private const val CHART_LINE_WIDTH = 3F
        private const val TEXT_SIZE = 12F
    }
}