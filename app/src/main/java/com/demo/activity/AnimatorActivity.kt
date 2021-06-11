package com.demo.activity

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.*
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.demo.one.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Created by hengwei on 2021/6/11.
 */
class AnimatorActivity : AppCompatActivity() {

    private val line: View by lazy { findViewById(R.id.line) }
    private val ball: View by lazy { findViewById(R.id.ball) }
    private val tvProgress: TextView by lazy { findViewById(R.id.tv_progress) }
    private val tvValue: TextView by lazy { findViewById(R.id.tv_value) }
    private val fab: FloatingActionButton by lazy { findViewById(R.id.fab) }

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.animator_demo_activity_main)

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
                this,
                android.R.layout.simple_dropdown_item_1line,
                dataList.map { it.simpleName })
        }
        dialog = AlertDialog
            .Builder(this)
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