package com.example.mockapp.db

import com.example.mockapp.R
import com.example.mockapp.db.entity.Budget

object InitData {
    fun getData(): List<Budget> {
        return arrayListOf(
            Budget(0,budgetImage = R.drawable.ic_cafe,budgetTitle = "Cafe", budgetValue = 400, backgroundColor = R.color.color_coffee, R.color.color_coffee_dark),
            Budget(1,budgetImage = R.drawable.ic_house,budgetTitle = "House", budgetValue = 300, backgroundColor = R.color.color_house,R.color.color_house_dark),
            Budget(2,budgetImage = R.drawable.ic_love,budgetTitle = "Lover", budgetValue = 260, backgroundColor = R.color.color_lover,R.color.color_lover_dark),
            Budget(3,budgetImage = R.drawable.ic_gym,budgetTitle = "Gym", budgetValue = 150, backgroundColor = R.color.color_gym,R.color.color_gym_dark),
            Budget(4,budgetImage = R.drawable.ic_taxi,budgetTitle = "Taxi", budgetValue = 800, backgroundColor = R.color.color_taxi,R.color.color_taxi_dark),
            Budget(5,budgetImage = R.drawable.ic_other,budgetTitle = "Other", budgetValue = 580, backgroundColor = R.color.color_other,R.color.color_other_dark)

            )
    }

}