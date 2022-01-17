package com.example.mockapp.di

import com.example.mockapp.db.dao.BudgetDao
import com.example.mockapp.repository.BudgetRepository
import com.example.mockapp.repository.IBudgetRepository
import com.example.mockapp.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun providerBudgetRepository(
        dispatcherProvider: DispatcherProvider,
        budgetDao: BudgetDao
    ): IBudgetRepository = BudgetRepository(dispatcherProvider, budgetDao)


}