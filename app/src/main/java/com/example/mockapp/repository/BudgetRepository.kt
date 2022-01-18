package com.example.mockapp.repository

import com.example.mockapp.db.dao.BudgetDao
import com.example.mockapp.db.entity.Budget
import com.example.mockapp.util.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BudgetRepository @Inject constructor(private val dispatcherProvider: DispatcherProvider, private  val budgetDao: BudgetDao) : IBudgetRepository {
    override suspend fun getAllBudget(): Flow<List<Budget>> {
        return budgetDao.getAllBudget().flowOn(dispatcherProvider.io)

    }

    override suspend fun insertBudget(budget: List<Budget>) {
        withContext(dispatcherProvider.io){
            budgetDao.insertBudget(budget)

        }
    }


    override suspend fun updateBudget(budgetValue: Long, budgetId: Int) {
        withContext(dispatcherProvider.io){
            budgetDao.updateBudget(budgetValue, budgetId)

        }
    }
}