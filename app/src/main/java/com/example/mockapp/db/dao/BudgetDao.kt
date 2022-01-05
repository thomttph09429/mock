package com.example.mockapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mockapp.db.entity.Budget
import com.example.mockapp.util.Constant.BUDGET_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {
    @Transaction
    @Query("SELECT * FROM $BUDGET_TABLE")
    fun getAllBudget(): Flow<List<Budget>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudget(budgets: List<Budget>)

    @Update()
    suspend fun updateBudget(budgets: Budget)
}