package com.example.mockapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mockapp.db.dao.BudgetDao
import com.example.mockapp.db.entity.Budget
import com.example.mockapp.util.Constant.DATABASE_NAME


@Database(entities = [Budget::class], version = 1)
abstract class BudgetDatabase : RoomDatabase() {
    abstract fun budgetDao(): BudgetDao
    companion object {
        @Volatile
        private var INSTANCE: BudgetDatabase? = null
        fun getInstance(context: Context): BudgetDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                BudgetDatabase::class.java,
                DATABASE_NAME
            ).build().also {
                INSTANCE = it
            }
        }
    }
}