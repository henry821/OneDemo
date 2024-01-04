package com.demo.modules.fresco

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.one.R
import com.demo.one.databinding.FragmentFrescoBinding
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView

class FrescoFragment : Fragment() {
    private var _binding: FragmentFrescoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFrescoBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageUriStr =
            "https://hbimg.huabanimg.com/2bab4e86fb981b32e5391cf710977708f2f2ba9715e1b3-FeMyza_fw658"

        binding.refresh.setOnClickListener { displayWebp(imageUriStr) }

//        val list =
//            List(50) { imageUriStr }
//        with(binding.rv) {
//            adapter = MainAdapter(list)
//            layoutManager = LinearLayoutManager(
//                context,
//                RecyclerView.VERTICAL,
//                false
//            )
//            addItemDecoration(
//                DividerItemDecoration(
//                    context,
//                    RecyclerView.VERTICAL
//                )
//            )
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun displayWebp(str: String) {
        val controller = Fresco
            .newDraweeControllerBuilder()
            .setUri(str)
            .setAutoPlayAnimations(true)
            .build()
        binding.topSdv.controller = controller
    }

    class MainAdapter(private val list: List<String>) : RecyclerView.Adapter<MainHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
            val itemView =
                LayoutInflater.from(parent.context).inflate(R.layout.item_fresco_rv, parent, false)
            return MainHolder(itemView)
        }

        override fun getItemCount() = list.size

        override fun onBindViewHolder(holder: MainHolder, position: Int) {
            holder.bindViewHolder(list[position])
        }

    }

    class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val item = itemView.findViewById<SimpleDraweeView>(R.id.sdv)

        fun bindViewHolder(str: String) {
            item.controller = Fresco.newDraweeControllerBuilder()
                .setUri(str)
                .setAutoPlayAnimations(true)
                .build()
        }
    }
}