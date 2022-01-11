package com.example.mockapp.ui

import android.animation.ValueAnimator
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.mockapp.base.BaseFragment
import com.example.mockapp.databinding.FragmentHomeBinding
import kotlin.math.abs
import android.view.animation.AnimationUtils
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mockapp.R
import com.example.mockapp.adapter.RulerAdapter
import com.example.mockapp.db.InitData
import com.example.mockapp.db.entity.Budget
import com.example.mockapp.util.Constant.HIGH
import com.example.mockapp.util.Constant.NORMAL
import com.example.mockapp.util.Constant.TOO_HIGH
import com.example.mockapp.viewmodel.BudgetViewModel


class HomeFragment() : BaseFragment<FragmentHomeBinding>() {
    private var oldPrice = 0
    private lateinit var budgetAdapter: BudgetAdapter
    private val rulerAdapter = RulerAdapter()
    private var linearLayoutManager: LinearLayoutManager? = null
    private val viewModel: BudgetViewModel by viewModels()
    override fun initView() {
        initBudgetPlan()
        initScrollbar()

    }

    override fun initAction() {
        scrollRuler()
    }

    private fun updateBudget(position: Int) = with(binding) {
        btnSave.setOnClickListener {
            val budgetValue = tvBudget.text.toString().toLong()
            viewModel.updateBudge(budgetValue, position)
        }


    }

    private fun scrollRuler() = with(binding) {
        rvRuler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                startTextAnimation()

            }

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
                changeTextStatus(
                    resources.getString(R.string.status_normal),
                    resources.getString(R.string.description_normal)
                )
            }
            newPrice >= TOO_HIGH -> {
                changeTextStatus(
                    resources.getString(R.string.status_crazy),
                    resources.getString(R.string.description_crazy)
                )
            }
            else -> {
                changeTextStatus(
                    resources.getString(R.string.status_a_lot),
                    resources.getString(R.string.description_a_lot)
                )
            }
        }
    }

    private fun changeTextStatus(status: String, description: String) =
        with(binding) {
            tvStatus.text = status
            tvDescription.text = description
        }

    fun showCostByCategory(position: Int) = with(binding) {

        viewModel.budgets.observe(this@HomeFragment, { budgets ->
            val costStart: Int = tvBudget.text.toString().toInt()
            val costEnd: Int = budgets[position].budgetValue.toInt()
            startCostAnimation(costStart, costEnd, tvBudget)
            val coffee = budgets[0].budgetValue.toInt()
            val house = budgets[1].budgetValue.toInt()
            val lover = budgets[2].budgetValue.toInt()
            val gym = budgets[3].budgetValue.toInt()
            val taxi = budgets[4].budgetValue.toInt()
            val other = budgets[5].budgetValue.toInt()
            when (position) {
                0 -> {
                    updateScrollbar((coffee / 10) - 2)
                    updateBudget(position)

                }
                1 -> {

                    updateScrollbar((house / 10) - 2)
                    updateBudget(position)

                }
                2
                -> {

                    updateScrollbar((lover / 10) - 2)
                    updateBudget(position)

                }
                3
                -> {

                    updateScrollbar((gym / 10) - 2)
                    updateBudget(position)

                }
                4
                -> {
                    updateScrollbar((taxi / 10) - 2)
                    updateBudget(position)

                }
                5
                -> {
                    updateScrollbar((other / 10) - 2)
                    updateBudget(position)

                }
            }
        })

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
        val animSlideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down)
        tvStatus.startAnimation(animSlideDown)
        tvDescription.startAnimation(animSlideDown)

    }

    private fun startCostAnimation(start: Int, end: Int, view: TextView) {
        val animator = ValueAnimator.ofInt(start ,end)
        animator.duration = 500
        animator.addUpdateListener { animation ->
            val value= animation.animatedValue.toString().toInt()
            if (value % 2==0){
                view.text = value.toString()

            }
        }
        animator.start()
        view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))


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
        viewModel.getAllBudget()
        budgetAdapter = BudgetAdapter(requireContext())
        vpBudget.apply {
            viewModel.budgets.observe(this@HomeFragment, Observer {
                if (it.isEmpty()) {
                    viewModel.insertBudget(InitData.getData())
                } else {
                    budgetAdapter.submitList(it)

                }
            })
            adapter = budgetAdapter
            vpBudget.apply {
                clipToPadding = false
                clipChildren = false
                offscreenPageLimit = 3
                getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            }
            val pageTransformer = CompositePageTransformer().apply {
                addTransformer(MarginPageTransformer(60))
                addTransformer { view, position ->
                    val r = 1 - abs(position)
                    view.scaleY = 0.85f + r * 0.15f
                    view.alpha = 1f
                    if (position < 0 || position > 0) {
                        view.alpha = 0.3f

                    }
                }

                vpBudget.registerOnPageChangeCallback(object :
                    ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)

                        showCostByCategory(position)
                    }


                }

                )

            }

            vpBudget.setPageTransformer(pageTransformer)

        }
    }

    override fun getViewBinding(): FragmentHomeBinding =
        FragmentHomeBinding.inflate(layoutInflater)
}