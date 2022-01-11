package com.example.mockapp.db.dao

import androidx.room.*
import com.example.mockapp.db.entity.Budget
import com.example.mockapp.util.Constant.BUDGET_TABLE
import com.example.mockapp.util.Constant.COLUMN_ID
import com.example.mockapp.util.Constant.COLUMN_VALUE
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {
    @Transaction
    @Query("SELECT * FROM $BUDGET_TABLE")
    fun getAllBudget(): Flow<List<Budget>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudget(budgets: List<Budget>)


    @Query("UPDATE $BUDGET_TABLE SET $COLUMN_VALUE=:budgetValue WHERE $COLUMN_ID = :budgetId")
    suspend fun updateBudget(budgetValue :Long, budgetId: Int)
}