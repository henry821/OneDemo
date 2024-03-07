package com.demo.modules.span

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.core.text.set
import androidx.fragment.app.Fragment
import com.demo.one.R
import com.demo.one.databinding.FragmentSpanBinding

/**
 * Description TextView各种Span
 * Author henry
 * Date   2022/12/19
 */
class SpanFragment : Fragment() {

    private var _binding: FragmentSpanBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSpanBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val text = "心爱的小摩托:当前文本是用户发的一段话，可以换行的一段话"
        val spanList = listOf<Any>(
            VerticalImageSpan(requireContext(), R.drawable.ic_span_gender),
            VerticalImageSpan(requireContext(), LevelDrawable(requireContext()).toBitmap()),
            VerticalImageSpan(requireContext(), VipDrawable(requireContext()).toBitmap())
        )

        val list = text.split(":").toMutableList().apply {
            add(1, ": ")
            spanList.forEach { _ -> add(1, "%") }
        }

        val builder = SpannableStringBuilder().apply {
            list.forEach { append(it) }
        }

        val nameSpan = ForegroundColorSpan(Color.parseColor("#A4A9B3"))
        builder[0, builder.indexOf(":")] = nameSpan

        val replaceIndex = builder.indexOfFirst { it == '%' }
        spanList.forEachIndexed { index, any ->
            builder[replaceIndex + index, replaceIndex + index + 1] = any
        }

        binding.content.text = builder
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}