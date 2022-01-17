package com.example.mockapp.viewmodel

import androidx.lifecycle.*
import com.example.mockapp.db.entity.Budget
import com.example.mockapp.repository.BudgetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(private  val budgetRepository: BudgetRepository) : ViewModel() {
    private val _budget = MutableLiveData<List<Budget>>()
    val budgets: LiveData<List<Budget>> = _budget



    fun getAllBudget() {
        viewModelScope.launch {
            budgetRepository.getAllBudget().collect { budget->
                _budget.value=budget
            }
        }

    }

    fun insertBudget(budget: List<Budget>) {
        viewModelScope.launch {
            budgetRepository.insertBudget(budget)
        }
    }

    fun updateBudge(budgetValue: Long,budgetId: Int){
        viewModelScope.launch {
            budgetRepository.updateBudget(budgetValue, budgetId)
        }
    }
}