package com.demo.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.one.R

/**
 * 入口作用的Activity
 */
abstract class EntranceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entrance)

        val list = initData()

        val recyclerView = findViewById<RecyclerView>(R.id.rv_content)
        val adapter = MainAdapter(list)
        recyclerView.run {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(this@EntranceActivity)
        }
    }

    class MainAdapter(private val dataList: List<MainData>) : RecyclerView.Adapter<MainAdapter.MainHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
            val view = Button(parent.context)
            val lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            view.layoutParams = lp
            return MainHolder(view)
        }

        override fun onBindViewHolder(holder: MainHolder, position: Int) {
            holder.bindData(dataList[position])
        }

        override fun getItemCount(): Int {
            return dataList.size
        }

        class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bindData(data: MainData) {
                (itemView as Button).run {
                    this.text = data.btnText
                    setOnClickListener {
                        itemView.context.startActivity(Intent(itemView.context, data.dest))
                    }
                }
            }
        }
    }

    data class MainData(val btnText: String, val dest: Class<out Any>)

    /**
     * 生成要展示的条目
     */
    abstract fun initData(): List<MainData>

}