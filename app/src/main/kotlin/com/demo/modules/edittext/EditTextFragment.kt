package com.demo.modules.edittext

import android.os.Bundle
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.demo.one.databinding.FragmentEditTextBinding

/**
 * Created by hengwei on 2022/11/3.
 */
class EditTextFragment : Fragment() {

    companion object {
        private const val CLRF = '\n'
    }

    private var _binding: FragmentEditTextBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentEditTextBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val deferringInsetsListener = RootViewDeferringInsetsCallback(
            persistentInsetTypes = WindowInsetsCompat.Type.systemBars(),
            deferredInsetTypes = WindowInsetsCompat.Type.ime()
        )
        ViewCompat.setWindowInsetsAnimationCallback(binding.root, deferringInsetsListener)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root, deferringInsetsListener)

        ViewCompat.setWindowInsetsAnimationCallback(
            binding.editText,
            TranslateDeferringInsetsAnimationCallback(
                view = binding.editText,
                persistentInsetTypes = WindowInsetsCompat.Type.systemBars(),
                deferredInsetTypes = WindowInsetsCompat.Type.ime(),
                dispatchMode = WindowInsetsAnimationCompat.Callback.DISPATCH_MODE_CONTINUE_ON_SUBTREE
            )
        )
        ViewCompat.setWindowInsetsAnimationCallback(
            binding.functions,
            TranslateDeferringInsetsAnimationCallback(
                view = binding.functions,
                persistentInsetTypes = WindowInsetsCompat.Type.systemBars(),
                deferredInsetTypes = WindowInsetsCompat.Type.ime(),
            )
        )
        ViewCompat.setWindowInsetsAnimationCallback(
            binding.editText,
            ControlFocusInsetsAnimationCallback(binding.editText)
        )

        binding.editText.addTextChangedListener(
            beforeTextChanged = { text, start, count, after ->
                binding.beforeTextChanged.text =
                    "text=${text}, start=${start}, count=${count}, after=${after}"
            },
            onTextChanged = { text, start, before, count ->
                binding.onTextChanged.text =
                    "text=${text}, start=${start}, before=${before}, count=${count}"
            },
            afterTextChanged = { text ->
                binding.afterTextChanged.text = "text=${text}, count=${text?.length}"
            }
        )

        binding.enter.setOnClickListener {
            val text = binding.editText.text
            val span = SpannableString("$text$CLRF")
            binding.editText.setText(span, TextView.BufferType.SPANNABLE)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}