package com.example.mockapp.db

import com.example.mockapp.R
import com.example.mockapp.db.entity.Budget

object InitData {
    fun getData(): List<Budget> {
        return arrayListOf(
            Budget(budgetImage = R.drawable.ic_coffee,budgetTitle = "Coffee", budgetValue = 400, backgroundColor = R.color.color_coffee),
            Budget(budgetImage = R.drawable.ic_house,budgetTitle = "House", budgetValue = 300, backgroundColor = R.color.color_house),
            Budget(budgetImage = R.drawable.ic_love,budgetTitle = "Lover", budgetValue = 260, backgroundColor = R.color.color_lover),
            Budget(budgetImage = R.drawable.ic_eating,budgetTitle = "Eating", budgetValue = 150, backgroundColor = R.color.color_eating),
            Budget(budgetImage = R.drawable.ic_car,budgetTitle = "Taxi", budgetValue = 700, backgroundColor = R.color.color_taxi),
            Budget(budgetImage = R.drawable.ic_other,budgetTitle = "Other", budgetValue = 580, backgroundColor = R.color.color_other)

            )
    }

}