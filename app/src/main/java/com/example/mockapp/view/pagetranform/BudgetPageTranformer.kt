package com.example.mockapp.view.pagetranform

import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.mockapp.R


class BudgetPageTranformer(private val colors: List<Int>) :
    ViewPager2.PageTransformer {
    companion object {
        const val TRANSLATION_FACTOR = 0.6F
    }


    override fun transformPage(view: View, position: Float) {
        view.apply {
            val pageWidth = width
            val offset = -(pageWidth * TRANSLATION_FACTOR) * position

            when {
                //This item is on the left  of the screen
                position < -0.5f -> {
                    translationX = offset + 30
                    checkCurrentItem(this, false)
                }
                //current item
                position in -0.5..0.5 -> {
                    translationX = offset
                    checkCurrentItem(this, true)
                        startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in_left))



                }

                //first item on the right
                position in 0.51..1.8 -> {
                    translationX = offset
                    checkCurrentItem(this, false)

                }
                // second item on the right
                position > 1.9 -> {
                    translationX = offset - 200
                    checkCurrentItem(this, false)
                }


            }

        }
    }


    fun checkCurrentItem(view: View, isCurrentItem: Boolean) {
        val whiteColor = view.context.getColor(R.color.white)
        val parent = view.parent as RecyclerView
        val imgAndText = parent.getChildViewHolder(view)
        val itemView = parent.getChildViewHolder(view).itemView
        val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        val tvSpending = itemView.findViewById<TextView>(R.id.tvSpending)
        val tvDollar = itemView.findViewById<TextView>(R.id.tv)
        val ivPhoto = itemView.findViewById<ImageView>(R.id.ivPhoto)
        val carViewContainer = itemView.findViewById<CardView>(R.id.carViewContainer)
        val cardImage = itemView.findViewById<CardView>(R.id.cardImage)
        val position = imgAndText.adapterPosition
        val colorImage = view.context.getColor(colors[position])
        if (isCurrentItem) {
            tvTitle.visibility = View.VISIBLE
            tvSpending.visibility = View.VISIBLE
            tvDollar.visibility = View.VISIBLE
            ivPhoto.setColorFilter(whiteColor)
            carViewContainer.setCardBackgroundColor(colorImage)
            cardImage.setCardBackgroundColor(view.context.getColor(R.color.cover_color))
        } else {
            ivPhoto.setColorFilter(colorImage)
            cardImage.setCardBackgroundColor(whiteColor)
            tvTitle.visibility = View.GONE
            tvSpending.visibility = View.GONE
            tvDollar.visibility = View.GONE
            carViewContainer.background.alpha = 0
        }

    }
}