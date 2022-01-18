package com.example.mockapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.example.mockapp.databinding.ItemTypeSpendingBinding
import com.example.mockapp.db.entity.Budget


class BudgetAdapter :
    ListAdapter<Budget, BudgetAdapter.TypeSendViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TypeSendViewHolder {
        val binding =
            ItemTypeSpendingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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