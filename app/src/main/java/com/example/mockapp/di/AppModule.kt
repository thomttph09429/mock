package com.example.mockapp.di

import android.content.Context
import com.example.mockapp.db.BudgetDatabase
import com.example.mockapp.util.DispatcherProvider
import com.example.mockapp.util.StandardDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): BudgetDatabase =
        BudgetDatabase.getInstance(context)

    @Singleton
    @Provides
    fun provideBudgetDao(database: BudgetDatabase) = database.budgetDao()

    @Singleton
    @Provides
    fun provideDispatcher(): DispatcherProvider = StandardDispatcher()
}