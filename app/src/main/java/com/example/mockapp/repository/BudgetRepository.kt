package com.example.mockapp.repository

import androidx.lifecycle.LiveData
import com.example.mockapp.db.dao.BudgetDao
import com.example.mockapp.db.entity.Budget
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class BudgetRepository(private val budgetDao: BudgetDao) : IBudgetRepository {
    override suspend fun getAllBudget(): Flow<List<Budget>> {
       return budgetDao.getAllBudget().flowOn(Dispatchers.IO)

    }

    override suspend fun insertBudget(budget: List<Budget>) {
        budgetDao.insertBudget(budget)    }

    override suspend fun updateBudget(budget: Budget) = withContext(Dispatchers.IO) {
        budgetDao.updateBudget(budget)
    }
}