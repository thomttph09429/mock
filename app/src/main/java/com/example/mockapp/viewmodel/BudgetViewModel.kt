package com.example.mockapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mockapp.db.BudgetDatabase
import com.example.mockapp.db.entity.Budget
import com.example.mockapp.repository.BudgetRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BudgetViewModel(application: Application) : AndroidViewModel(application) {
    private val budgetRepository: BudgetRepository
    private val _budget = MutableLiveData<List<Budget>>()
    val budgets: LiveData<List<Budget>> = _budget

    init {
        val budgetDao = BudgetDatabase.getInstance(application).budgetDao()
        budgetRepository = BudgetRepository(budgetDao)
    }

    fun getAllBudget() {
        viewModelScope.launch {
            budgetRepository.getAllBudget().collect {
                _budget.value=it
            }
        }

    }

    fun insertBudget(budget: List<Budget>) {
        viewModelScope.launch {
            budgetRepository.insertBudget(budget)
        }
    }
     fun updateBudge(budget: Budget){
         viewModelScope.launch {
             budgetRepository.updateBudget(budget)
         }
     }
}