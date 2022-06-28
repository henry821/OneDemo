package com.demo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import com.demo.one.databinding.FragmentWindowInsetsBinding

/**
 * Created by hengwei on 2022/6/15.
 */
class WindowInsetsFragment : Fragment() {

    private var _binding: FragmentWindowInsetsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentWindowInsetsBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.btnHideStatusBars?.setOnClickListener {
            ViewCompat.getWindowInsetsController(it)?.hide(WindowInsetsCompat.Type.statusBars())
        }

        _binding?.btnShowStatusBars?.setOnClickListener {
            ViewCompat.getWindowInsetsController(it)?.show(WindowInsetsCompat.Type.statusBars())
        }

        _binding?.btnHideNavigationBars?.setOnClickListener {
            ViewCompat.getWindowInsetsController(it)?.hide(WindowInsetsCompat.Type.navigationBars())
        }

        _binding?.btnShowNavigationBars?.setOnClickListener {
            ViewCompat.getWindowInsetsController(it)?.show(WindowInsetsCompat.Type.navigationBars())
        }
    }

    override fun onStart() {
        super.onStart()
        val targetView = _binding?.root ?: return
        ViewCompat.setOnApplyWindowInsetsListener(targetView) { _, insets ->
            val statusBarsInset = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            val navigationBarsInsets = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            _binding?.tvWindowInsetsInfo?.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                bottomMargin = navigationBarsInsets.bottom
            }

            _binding?.tvWindowInsetsInfo?.text = """
                statusBars.Insets     (${statusBarsInset.left},${statusBarsInset.top},${statusBarsInset.right},${statusBarsInset.bottom})
                navigationBars.Insets(${navigationBarsInsets.left},${navigationBarsInsets.top},${navigationBarsInsets.right},${navigationBarsInsets.bottom})
                systemBarsInsets.Insets(${systemBarsInsets.left},${systemBarsInsets.top},${systemBarsInsets.right},${systemBarsInsets.bottom})
            """.trimIndent()

            WindowInsetsCompat.CONSUMED
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}