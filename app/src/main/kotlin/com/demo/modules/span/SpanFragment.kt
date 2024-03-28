package com.demo.modules.span

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.demo.modules.span.core.spannable
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
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSpanBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.content.spannable()
            .append({ "N" }, { VerticalImageSpan(requireContext(), R.drawable.ic_span_gender) })
            .append(
                { "N" },
                { VerticalImageSpan(requireContext(), LevelDrawable(requireContext())) })
            .append({ "N" }, { VerticalImageSpan(requireContext(), VipDrawable(requireContext())) })
            .append({ "心爱的小摩托:当前文本是用户发的一段话，可以换行的一段话" }, {})
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}