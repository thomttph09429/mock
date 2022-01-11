package com.example.mockapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.example.mockapp.databinding.ItemTypeSpendingBinding
import com.example.mockapp.db.entity.Budget


class TypeSpendingAdapter(val context: Context, val iListener: IListener) :
    ListAdapter<Budget, TypeSpendingAdapter.TypeSendViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TypeSendViewHolder {
        val binding =
            ItemTypeSpendingBinding.inflate(LayoutInflater.from(context), parent, false)
        return TypeSendViewHolder(binding)

    }

    override fun onBindViewHolder(holder: TypeSendViewHolder, position: Int) {
        holder.bind(currentList[position])


    }


    inner class TypeSendViewHolder(private val binding: ItemTypeSpendingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(budget: Budget) = with(binding) {
            ivPhoto.setImageResource(budget.budgetImage)
            tvSpending.text = budget.budgetValue.toString()
            tvTitle.text = budget.budgetTitle
            carView.setCardBackgroundColor(
                ContextCompat.getColor(
                    context, budget.backgroundColor
                )
            )
        }

    }


    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Budget>() {
            override fun areItemsTheSame(oldItem: Budget, newItem: Budget): Boolean {
                return oldItem.budgetId == newItem.budgetId
            }

            override fun areContentsTheSame(oldItem: Budget, newItem: Budget): Boolean {
                return oldItem == newItem
            }

        }
    }
}