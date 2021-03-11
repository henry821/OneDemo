package com.demo.activity

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.demo.one.R

/**
 * 验证lottie文件效果
 * Created by hengwei on 2021/3/11.
 */
class LottieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_lottie)

        val dataList = mutableListOf<AnimData>()
        dataList.add(AnimData("lottie/redpacket", "lottie/redpacket/hongbao.json"))
        dataList.add(AnimData("lottie/redpacket2", "lottie/redpacket2/hongbao.json"))

        val rvContainer = findViewById<RecyclerView>(R.id.rv_container)
        val adapter = LottieAdapter(dataList)
        rvContainer.run {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(this@LottieActivity)
            addItemDecoration(DividerItemDecoration(this@LottieActivity, RecyclerView.VERTICAL))
        }
    }

    class LottieAdapter(private val dataList: List<AnimData>) : RecyclerView.Adapter<LottieAdapter.LottieHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LottieHolder {
            val view = LottieAnimationView(parent.context)
            val lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            view.layoutParams = lp
            return LottieHolder(view)
        }

        override fun onBindViewHolder(holder: LottieHolder, position: Int) {
            holder.bindData(dataList[position])
        }

        override fun getItemCount(): Int {
            return dataList.size
        }

        class LottieHolder(itemView: LottieAnimationView) : RecyclerView.ViewHolder(itemView) {
            fun bindData(data: AnimData) {
                (itemView as LottieAnimationView).run {
                    imageAssetsFolder = data.imageAssetsFolder
                    setAnimation(data.assetsName)
                    playAnimation()
                }
            }
        }
    }

    data class AnimData(val imageAssetsFolder: String, val assetsName: String)

}