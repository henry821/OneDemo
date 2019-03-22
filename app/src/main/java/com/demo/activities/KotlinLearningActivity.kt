package com.demo.activities

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.demo.adapters.TitleListNormalAdapter
import com.demo.beans.TitleBean
import com.demo.one.R
import com.demo.utils.CoroutinesUtil

/**
 * Description
 * Author wanghengwei
 * Date   2019/3/20 18:51
 */
class KotlinLearningActivity : Activity() {

    private lateinit var rvOperation: RecyclerView

    private lateinit var mTitleList: ArrayList<TitleBean>

    private lateinit var mTitleListAdapter: TitleListNormalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_kotlin_learning)

        rvOperation = findViewById(R.id.rv_operation)

        mTitleList = ArrayList()

        initTitleRecyclerView()

    }

    private fun initTitleRecyclerView() {
        mTitleList.add(TitleBean("普通用法", null))
        mTitleList.add(TitleBean("普通用法2", null))
        mTitleList.add(TitleBean("普通用法3", null))
        mTitleListAdapter = TitleListNormalAdapter(this, mTitleList, TitleListNormalAdapter.OnItemClickListener { position, bean ->
            when (position) {
                0 -> CoroutinesUtil.basicMethod()
                1 -> CoroutinesUtil.basicMethod2()
                2 -> CoroutinesUtil.basicMethod3()
            }
        })
        rvOperation.layoutManager = LinearLayoutManager(this)
        rvOperation.adapter = mTitleListAdapter
    }


}