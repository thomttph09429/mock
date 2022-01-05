package com.example.mockapp.repository

import androidx.lifecycle.LiveData
import com.example.mockapp.db.entity.Budget
import kotlinx.coroutines.flow.Flow


interface IBudgetRepository {
    suspend fun getAllBudget(): Flow<List<Budget>>
     suspend fun insertBudget(budget: List<Budget>)
     suspend fun updateBudget(budget: Budget)
}