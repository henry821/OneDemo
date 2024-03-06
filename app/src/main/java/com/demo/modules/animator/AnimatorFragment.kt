package com.demo.modules.animator

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.demo.one.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Created by hengwei on 2021/6/11.
 */
class AnimatorFragment : Fragment() {

    private lateinit var line: View
    private lateinit var ball: View
    private lateinit var tvProgress: TextView
    private lateinit var tvValue: TextView
    private lateinit var fab: FloatingActionButton

    private lateinit var dialog: AlertDialog
    private var adapter: ArrayAdapter<String>? = null
    private var animator: ValueAnimator? = null

    private val dataList = listOf(
        AccelerateDecelerateInterpolator::class.java,
        AnticipateInterpolator::class.java,
        PathInterpolator::class.java,
        BounceInterpolator::class.java,
        AnticipateOvershootInterpolator::class.java,
        OvershootInterpolator::class.java,
        LinearInterpolator::class.java,
        DecelerateInterpolator::class.java,
        AccelerateInterpolator::class.java
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.fragment_animator, container, false).apply {
            line = findViewById(R.id.line)
            ball = findViewById(R.id.ball)
            tvProgress = findViewById(R.id.tv_progress)
            tvValue = findViewById(R.id.tv_value)
            fab = findViewById(R.id.fab)
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        line.post {
            animator = ValueAnimator.ofFloat(0f, line.measuredWidth.toFloat()).also {
                it.duration = 5000
                it.addUpdateListener { va ->
                    val animatedValue = va.animatedValue
                    ball.translationX = animatedValue as Float

                    tvProgress.text =
                        getString(R.string.animator_value_progress, va.animatedFraction * 100)
                    tvValue.text =
                        getString(
                            R.string.animator_time_progress,
                            (va.currentPlayTime / va.totalDuration.toFloat()) * 100
                        )

                }
            }
        }

        initDialog()

        fab.setOnClickListener {
            dialog.show()
        }
    }

    private fun initDialog() {
        if (adapter == null) {
            adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                dataList.map { it.simpleName })
        }
        dialog = AlertDialog
            .Builder(requireContext())
            .setAdapter(
                adapter
            ) { dialog, which ->
                val clazz = dataList[which]
                animator?.run {
                    interpolator = clazz.newInstance()
                    start()
                }
                dialog.dismiss()
            }
            .create()
    }

}