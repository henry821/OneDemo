package com.demo.activities

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.baselibrary.utils.LogUtil
import com.demo.adapters.TitleListNormalAdapter
import com.demo.beans.TitleBean
import com.demo.one.R
import com.demo.utils.CoroutinesUtil
import java.util.*

/**
 * Description
 * Author wanghengwei
 * Date   2019/3/20 18:51
 */
class KotlinLearningActivity : Activity() {

    private lateinit var tvCheckNull: TextView
    private lateinit var rvOperation: RecyclerView

    private lateinit var mTitleList: ArrayList<TitleBean>

    private lateinit var mTitleListAdapter: TitleListNormalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_kotlin_learning)

        tvCheckNull = findViewById(R.id.tv_title_check_null)
        rvOperation = findViewById(R.id.rv_operation)

        mTitleList = ArrayList()

        initTitleRecyclerView()

        tvCheckNull.setOnClickListener {
            checkNull()
        }

    }

    private fun initTitleRecyclerView() {
        mTitleList.add(TitleBean("普通用法", null))
        mTitleList.add(TitleBean("普通用法2", null))
        mTitleList.add(TitleBean("普通用法3", null))
        mTitleList.add(TitleBean("取消协程的执行", null))
        mTitleList.add(TitleBean("取消协程的执行2", null))
        mTitleList.add(TitleBean("协程超时", null))
        mTitleList.add(TitleBean("并发执行", null))
        mTitleListAdapter = TitleListNormalAdapter(this, mTitleList, TitleListNormalAdapter.OnItemClickListener { position, bean ->
            when (position) {
                0 -> CoroutinesUtil.basicMethod()
                1 -> CoroutinesUtil.basicMethod2()
                2 -> CoroutinesUtil.basicMethod3()
                3 -> CoroutinesUtil.cancelMethod()
                4 -> CoroutinesUtil.cancelMethod2()
                5 -> CoroutinesUtil.timeoutMethod()
                6 -> CoroutinesUtil.asyncMethod()
            }
        })
        rvOperation.layoutManager = LinearLayoutManager(this)
        rvOperation.adapter = mTitleListAdapter
    }

    private fun checkNull() {
        val output: String? = createString()
        LogUtil.e(output ?: "This is a Null String")
    }

    private fun createString(): String? {
//        return "123456"
        return null
    }


}