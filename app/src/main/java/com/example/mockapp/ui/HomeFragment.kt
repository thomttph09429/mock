package com.example.mockapp.ui

import android.animation.ValueAnimator
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.mockapp.base.BaseFragment
import com.example.mockapp.databinding.FragmentHomeBinding
import kotlin.math.abs
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mockapp.R
import com.example.mockapp.adapter.RulerAdapter
import com.example.mockapp.db.InitData
import com.example.mockapp.util.Constant.HIGH
import com.example.mockapp.util.Constant.NORMAL
import com.example.mockapp.viewmodel.BudgetViewModel


class HomeFragment : BaseFragment<FragmentHomeBinding>(), IListener {
    private var oldPrice = 0
    private lateinit var typeSpendingAdapter: TypeSpendingAdapter
    private val rulerAdapter = RulerAdapter()
    private var linearLayoutManager: LinearLayoutManager? = null
    private val viewModel: BudgetViewModel by viewModels()


    private var mPositionCurrent: Int = 1
    override fun initView() {
        viewModel.getAllBudget()
        initTypeSpending()
        setUpToolbar()
        initRuler()

    }


    private fun scrollRuler() {
        binding.rvRuler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                startMarkAnimation()
                startTextAnimation()

            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val visiblePosition = linearLayoutManager!!.findFirstVisibleItemPosition()
                val newPrice: Int = visiblePosition.plus(2) * 10
                Handler(Looper.getMainLooper()).postDelayed({
                    oldPrice = newPrice
                }, 1)
                startCostAnimation(oldPrice, newPrice, binding.tvSpend)
                when (newPrice) {
                    in 0..NORMAL -> {
                        changeTextStatus(
                            resources.getString(R.string.status_normal),
                            resources.getString(R.string.description_normal)
                        )

                    }
                    in NORMAL..HIGH -> {
                        changeTextStatus(
                            resources.getString(R.string.status_a_lot),
                            resources.getString(R.string.description_a_lot)
                        )
                    }
                    else -> {
                        changeTextStatus(
                            resources.getString(R.string.status_crazy),
                            resources.getString(R.string.description_crazy)
                        )
                    }
                }
            }
        })


    }

    private fun changeTextStatus(status: String, description: String) =
        with(binding) {
            tvStatus.text = status
            tvDescription.text = description
        }

    private fun initRuler() {

        val rulerList = arrayListOf<Int>()
        for (i in 0..3000 step 10) {
            rulerList.add(i)
        }
        rulerAdapter.submitList(rulerList)

        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvRuler.apply {
            adapter = rulerAdapter
            layoutManager = linearLayoutManager

        }
        scrollRuler()

    }


    private fun setUpToolbar() {
        binding.toolbar.setOnClickListener {
            activity?.finish()
        }

    }

    override fun isCurrent(): Int {
        Log.e("isCurrent", mPositionCurrent.toString())
        return mPositionCurrent


    }


    fun showCostByCategory(position: Int) {
        viewModel.budgets.observe(this, Observer {
            val coffee = it[0].budgetValue.toInt()
            val house = it[1].budgetValue.toInt()
            val lover = it[2].budgetValue.toInt()
            val eating = it[3].budgetValue.toInt()
            val taxi = it[4].budgetValue.toInt()
            val other = it[5].budgetValue.toInt()
            when (position) {
                0 -> {
                    startCostAnimation(house, coffee, binding.tvSpend)

                }
                1 -> {
                    startCostAnimation(coffee, house, binding.tvSpend)

                }
                2
                -> {
                    startCostAnimation(house, lover, binding.tvSpend)

                }
                3
                -> {
                    startCostAnimation(lover, eating, binding.tvSpend)

                }
                4
                -> {
                    startCostAnimation(eating, taxi, binding.tvSpend)

                }
                5
                -> {
                    startCostAnimation(taxi, other, binding.tvSpend)

                }
            }
        })

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
        val animator = ValueAnimator.ofInt(start, end)
        animator.duration = 1000
        animator.addUpdateListener { animation ->
            view.text = animation.animatedValue.toString()
        }
        animator.start()
        view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))


    }


    override fun getViewBinding(): FragmentHomeBinding =
        FragmentHomeBinding.inflate(layoutInflater)

    private fun initTypeSpending() {
        typeSpendingAdapter = TypeSpendingAdapter(requireContext(), this)
        binding.vpBudget.apply {
            viewModel.budgets.observe(this@HomeFragment, Observer {
                if (it.isEmpty()) {
                    viewModel.insertBudget(InitData.getData())
                } else {
                    typeSpendingAdapter.submitList(it)

                }
            })
            adapter = typeSpendingAdapter
            binding.vpBudget.apply {
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

                binding.vpBudget.registerOnPageChangeCallback(object :
                    ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        showCostByCategory(position)
                    }

                })
            }

            binding.vpBudget.setPageTransformer(pageTransformer)

        }
    }
}