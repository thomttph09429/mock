package com.example.mockapp.view.fragment

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.ValueAnimator
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.mockapp.base.BaseFragment
import com.example.mockapp.databinding.FragmentHomeBinding
import android.view.animation.AnimationUtils
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mockapp.R
import com.example.mockapp.adapter.BudgetAdapter
import com.example.mockapp.adapter.RulerAdapter
import com.example.mockapp.db.DataProvider
import com.example.mockapp.db.entity.Budget
import com.example.mockapp.util.Constant.ALPHA_DURATION
import com.example.mockapp.view.pagetranform.BudgetPageTranformer
import com.example.mockapp.util.Constant.NORMAL
import com.example.mockapp.util.Constant.PAGE_CAFE
import com.example.mockapp.util.Constant.PAGE_GYM
import com.example.mockapp.util.Constant.PAGE_HOUSE
import com.example.mockapp.util.Constant.PAGE_LOVE
import com.example.mockapp.util.Constant.PAGE_OTHER
import com.example.mockapp.util.Constant.PAGE_TAXI
import com.example.mockapp.util.Constant.TOO_HIGH
import com.example.mockapp.viewmodel.BudgetViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment() : BaseFragment<FragmentHomeBinding>() {
    private var oldPrice = 0
    private  val budgetAdapter= BudgetAdapter()
    private val rulerAdapter = RulerAdapter()
    private var linearLayoutManager: LinearLayoutManager? = null
    private val viewModel: BudgetViewModel by activityViewModels()
    private var listBudget = arrayListOf<Budget>()
    private var alphaAnimator: Animator? = null


    override fun initView() {
        initBudgetPlan()
        initScrollbar()

    }

    override fun initAction() {
        scrollRuler()
    }

    override fun initData() {
        viewModel.getAllBudget()
        viewModel.budgets.observe(this@HomeFragment, { budgets ->
            listBudget = budgets as ArrayList<Budget>
        })
    }



    private fun scrollRuler() = with(binding) {
        rvRuler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                startMarkAnimation()
                val visiblePosition = linearLayoutManager!!.findFirstVisibleItemPosition()
                val newPrice: Int = visiblePosition.plus(2) * 10
                Handler(Looper.getMainLooper()).postDelayed({
                    oldPrice = newPrice
                }, 1)
                startCostAnimation(oldPrice, newPrice, tvBudget)
                updateStatus(newPrice)
            }
        })

    }

    private fun updateStatus(newPrice: Int) {

        when {
            newPrice <= NORMAL -> {
                if (checkStatusText(resources.getString(R.string.status_normal))) {
                    startTextAnimation()
                    changeTextStatus(
                        resources.getString(R.string.status_normal),
                        resources.getString(R.string.description_normal)
                    )
                }


            }
            newPrice >= TOO_HIGH -> {
                if (checkStatusText(resources.getString(R.string.status_crazy))) {
                    startTextAnimation()
                    changeTextStatus(
                        resources.getString(R.string.status_crazy),
                        resources.getString(R.string.description_crazy)
                    )
                }


            }
            else -> {
                if (checkStatusText(resources.getString(R.string.status_a_lot))) {
                    startTextAnimation()
                    changeTextStatus(
                        resources.getString(R.string.status_a_lot),
                        resources.getString(R.string.description_a_lot)
                    )
                }


            }
        }
    }

    private fun checkStatusText(status: String): Boolean {
        return binding.tvStatus.text.toString().trim() != status

    }

    private fun changeTextStatus(status: String, description: String) =
        with(binding)
        {
            tvStatus.text = status
            tvDescription.text = description
            startTextAnimation()

        }

    fun showCostByCategory(position: Int) = with(binding) {

            val costStart: Int = tvBudget.text.toString().toInt()
            val costEnd: Int = listBudget[position].budgetValue.toInt()
            startCostAnimation(costStart, costEnd, tvBudget)
            val coffee = listBudget[PAGE_CAFE].budgetValue.toInt()
            val house = listBudget[PAGE_HOUSE].budgetValue.toInt()
            val lover = listBudget[PAGE_LOVE].budgetValue.toInt()
            val gym = listBudget[PAGE_GYM].budgetValue.toInt()
            val taxi = listBudget[PAGE_TAXI].budgetValue.toInt()
            val other = listBudget[PAGE_OTHER].budgetValue.toInt()
            updateBudget(position)
            when (position) {
                PAGE_CAFE-> {
                    updateScrollbar((coffee / 10) - 2)

                }
                PAGE_HOUSE -> {

                    updateScrollbar((house / 10) - 2)

                }
                PAGE_LOVE
                -> {

                    updateScrollbar((lover / 10) - 2)

                }
                PAGE_GYM
                -> {

                    updateScrollbar((gym / 10) - 2)

                }
                PAGE_TAXI
                -> {
                    updateScrollbar((taxi / 10) - 2)

                }
                PAGE_OTHER
                -> {
                    updateScrollbar((other / 10) - 2)

                }
            }


    }
    private fun updateBudget(position: Int) = with(binding) {
        btnSave.setOnClickListener {
            val budgetValue = tvBudget.text.toString().toLong()
            viewModel.updateBudge(budgetValue, position)
        }


    }
    private fun updateScrollbar(position: Int) {
        linearLayoutManager?.scrollToPositionWithOffset(position, 0)
        updateStatus(position * 10)

    }

    fun startMarkAnimation() {
        val animZoomIn = AnimationUtils.loadAnimation(
            context,
            R.anim.zoom_in
        )
        binding.marker.startAnimation(animZoomIn)
    }

    fun startTextAnimation() = with(binding) {
        val animTextIn = AnimationUtils.loadAnimation(context, R.anim.anim_text_in)
        val animTextOut = AnimationUtils.loadAnimation(context, R.anim.anim_text_description_in)
        tvStatus.startAnimation(animTextIn)
        tvDescription.startAnimation(animTextOut)

    }

    private fun startCostAnimation(start: Int, end: Int, view: TextView) {
        val animator = ValueAnimator.ofInt(start, end)
        animator.duration = ALPHA_DURATION
        animator.addUpdateListener { animation ->
            val value = animation.animatedValue.toString().toInt()
            view.text = value.toString()
        }
        animator.start()
        alphaAnimator = AnimatorInflater.loadAnimator(requireContext(), R.animator.animator_alpha)
        alphaAnimator?.apply {
            setTarget(binding.tvBudget)
            start()
        }

    }


    private fun initScrollbar() {
        val rulerList = arrayListOf<Int>()
        for (i in 0..3000 step 10) {
            rulerList.add(i)
        }
        rulerAdapter.submitList(rulerList)
        binding.rvRuler.apply {
            linearLayoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = rulerAdapter
            layoutManager = linearLayoutManager
        }
    }

    private fun initBudgetPlan() = with(binding) {

        var colors = mutableListOf<Int>()

        vpBudget.apply {
            viewModel.budgets.observe(this@HomeFragment, Observer {
                if (it.isEmpty()) {
                    viewModel.insertBudget(DataProvider.getData())
                } else {
                    budgetAdapter.submitList(it)
                }
                it.forEach { budget ->
                    colors.add(budget.backgroundColor)
                }
            })

            adapter = budgetAdapter
            vpBudget.apply {
                clipToPadding = false
                clipChildren = false
                offscreenPageLimit = 3
                getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            }

            vpBudget.setPageTransformer(BudgetPageTranformer(colors))
            vpBudget.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    showCostByCategory(position)

                }


            }

            )
        }
    }

    override fun getViewBinding(): FragmentHomeBinding =
        FragmentHomeBinding.inflate(layoutInflater)
}