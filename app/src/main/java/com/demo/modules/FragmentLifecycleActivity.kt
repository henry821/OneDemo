package com.demo.modules

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.OnBackStackChangedListener
import com.demo.one.R
import com.demo.one.databinding.ActivityFragmentLifecycleBinding
import com.demo.utils.dp

/**
 * Description Fragment生命周期
 * Author henry
 * Date   2023/1/6
 */
class FragmentLifecycleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFragmentLifecycleBinding
    private lateinit var backStackChangedListener: OnBackStackChangedListener

    private val index: Int
        get() = supportFragmentManager.fragments.size

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentLifecycleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            addFragment.setOnClickListener {
                //为了熟悉写法这里不用ktx提供的简便写法
                supportFragmentManager.beginTransaction()
                    .add(R.id.container, ChildFragment.newInstance(index))
                    .setReorderingAllowed(true)
                    .addToBackStack(index.toString())
                    .commit()
            }
            popFragment.setOnClickListener {
                val popIndex = index / 2
                Toast.makeText(
                    this@FragmentLifecycleActivity,
                    "弹出index：$popIndex",
                    Toast.LENGTH_SHORT
                ).show()
                supportFragmentManager.popBackStack(popIndex.toString(), 0)
            }
        }
        backStackChangedListener = OnBackStackChangedListener {
            binding.backStackInfo.text = "当前Fragment数量：${supportFragmentManager.fragments.size}"
        }
        supportFragmentManager.addOnBackStackChangedListener(backStackChangedListener)
        FragmentManager.enableDebugLogging(true)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.fragments.isNotEmpty()) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.removeOnBackStackChangedListener(backStackChangedListener)
    }

}

class ChildFragment : Fragment() {

    private val index by lazy { arguments?.getInt("index") ?: -1 }

    companion object {
        fun newInstance(index: Int): Fragment {
            return ChildFragment().also {
                it.arguments = bundleOf("index" to index)
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return TextView(context).apply {
            gravity = Gravity.CENTER
            textSize = 16f.dp
            setBackgroundColor(Color.WHITE)
            text = "子Fragment：$index"
        }
    }

}