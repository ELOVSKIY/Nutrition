package com.helicoptera.nutrition.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.helicoptera.nutrition.databinding.MealItemBinding
import com.helicoptera.nutrition.domain.Meal

//TODO Dependency inversion
class MealAdapter(private val mealSelectListener: MealSelectListener) :
    ListAdapter<Meal, MealAdapter.MealViewHolder>(this) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MealViewHolder {
        return MealViewHolder.from(parent)
    }

    override fun onBindViewHolder(
        holder: MealViewHolder,
        position: Int
    ) {
        val medalInfo = getItem(position)
        holder.bind(medalInfo, mealSelectListener)
    }

    override fun submitList(list: MutableList<Meal>?) {
        super.submitList(list)
    }

    companion object : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(
            oldItem: Meal, newItem: Meal
        ): Boolean {
            return (newItem.type == oldItem.type)
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }

    class MealViewHolder(private val binding: MealItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(meal: Meal,
                 mealSelectListener: MealSelectListener) {
            binding.meal = meal
            binding.plus.setOnClickListener {
                mealSelectListener.onMealSelected(meal.type)
            }
        }

        companion object {
            fun from(parent: ViewGroup): MealViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = MealItemBinding.inflate(inflater, parent, false)

                return MealViewHolder(binding)
            }
        }
    }
}