package com.example.mockapp.db

import com.example.mockapp.R
import com.example.mockapp.db.entity.Budget

object DataProvider {
    fun getData(): List<Budget> {
        return arrayListOf(
            Budget(0,budgetImage = R.drawable.ic_cafe,budgetTitle = "Cafe", budgetValue = 400, backgroundColor = R.color.color_coffee),
            Budget(1,budgetImage = R.drawable.ic_house,budgetTitle = "House", budgetValue = 300, backgroundColor = R.color.color_house),
            Budget(2,budgetImage = R.drawable.ic_love,budgetTitle = "Lover", budgetValue = 260, backgroundColor = R.color.color_lover),
            Budget(3,budgetImage = R.drawable.ic_gym,budgetTitle = "Gym", budgetValue = 150, backgroundColor = R.color.color_gym),
            Budget(4,budgetImage = R.drawable.ic_taxi,budgetTitle = "Taxi", budgetValue = 800, backgroundColor = R.color.color_taxi),
            Budget(5,budgetImage = R.drawable.ic_other,budgetTitle = "Other", budgetValue = 580, backgroundColor = R.color.color_other)

            )
    }

}