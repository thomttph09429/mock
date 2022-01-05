package com.example.mockapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mockapp.databinding.ItemRulerBinding


class RulerAdapter : ListAdapter<Int, RulerAdapter.RulerViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RulerAdapter.RulerViewHolder {
        val binding = ItemRulerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RulerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RulerAdapter.RulerViewHolder, position: Int) {
        holder.bind(position)


    }


    inner class RulerViewHolder(private val binding: ItemRulerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(i: Int) = with(binding) {
            if (i % 4 == 0) {
                vShort.visibility = View.GONE
                vLong.visibility = View.VISIBLE
            } else {
                vShort.visibility = View.VISIBLE
                vLong.visibility = View.GONE
            }
        }

    }


    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Int>() {
            override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
                return oldItem == newItem

            }

            override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
                return oldItem == newItem

            }

        }
    }
}

