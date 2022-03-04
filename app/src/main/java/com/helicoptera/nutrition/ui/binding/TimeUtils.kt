package com.helicoptera.nutrition.ui.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@BindingAdapter("time")
fun TextView.setMealTypeTitle(date: Date?) {
    if (date != null) {
        val dateFormat = SimpleDateFormat("hh:mm aa", Locale.US)
        val time: String = dateFormat.format(date)
        this.text = time
    } else {
        this.text = ""
    }
}