package com.helicoptera.nutrition.ui.chart

import com.github.mikephil.charting.formatter.ValueFormatter
import kotlin.math.roundToInt

class NonZeroValueFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return if (value == 0f) {
            ""
        } else {
            value.roundToInt().toString()
        }
    }
}