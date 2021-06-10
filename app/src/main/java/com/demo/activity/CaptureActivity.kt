package com.demo.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.demo.one.R
import com.demo.widgets.CarouselView
import com.demo.widgets.SimpleTabLayout

/**
 * 模仿录制视频页面
 * Created by hengwei on 2021/3/30.
 */
class CaptureActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "CaptureActivity"
    }

    private val ivProp by lazy(LazyThreadSafetyMode.NONE) { findViewById<ImageView>(R.id.iv_prop) }
    private val ivBeauty by lazy(LazyThreadSafetyMode.NONE) { findViewById<ImageView>(R.id.iv_beauty) }
    private val ivMusic by lazy(LazyThreadSafetyMode.NONE) { findViewById<ImageView>(R.id.iv_music) }
    private val ivPhoto by lazy(LazyThreadSafetyMode.NONE) { findViewById<ImageView>(R.id.iv_photo) }
    private val ivClear by lazy(LazyThreadSafetyMode.NONE) { findViewById<ImageView>(R.id.iv_clear) }
    private val indicator by lazy(LazyThreadSafetyMode.NONE) { findViewById<SimpleTabLayout>(R.id.indicator) }
    private val llCategory by lazy(LazyThreadSafetyMode.NONE) { findViewById<LinearLayout>(R.id.ll_category) }
    private val vpContent by lazy(LazyThreadSafetyMode.NONE) { findViewById<ViewPager>(R.id.vp_content) }
    private val llPropContainer by lazy(LazyThreadSafetyMode.NONE) { findViewById<LinearLayout>(R.id.ll_prop_container) }

    private lateinit var propList: ArrayList<Prop>
    private lateinit var categoryList: ArrayList<Category>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_capture)

        findViewById<CarouselView>(R.id.thumbnail).startScroll()

        initCategories()
        initProps()

        vpContent.adapter = CategoryAdapter(categoryList, propList)
        indicator.bindViewPager(vpContent)

        ivProp.setOnClickListener {
            togglePropContainer()
        }
    }

    override fun onBackPressed() {
        if (llPropContainer.visibility == View.VISIBLE) {
            llPropContainer.visibility = View.GONE
        } else {
            super.onBackPressed()
        }
    }

    private fun initProps() {
        propList = ArrayList()
        for (i in 1..50) {
            val prop = Prop(i, "", "道具")
            propList.add(prop)
        }
    }

    private fun initCategories() {
        categoryList = ArrayList()
        for (i in 1..20) {
            val category = Category(i, "分类")
            categoryList.add(category)
        }

    }

    private fun togglePropContainer() {
        when (llPropContainer.visibility) {
            View.VISIBLE -> llPropContainer.visibility = View.GONE
            else -> llPropContainer.visibility = View.VISIBLE
        }
    }

    class CategoryAdapter(
        private val categoryList: List<Category>,
        private val propList: List<Prop>
    ) : PagerAdapter() {
        override fun getCount(): Int {
            return categoryList.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            return RecyclerView(container.context).also {
                it.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                it.adapter = PropAdapter(propList)
                it.layoutManager = GridLayoutManager(container.context, 5)
                container.addView(it)
            }
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View?)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return categoryList[position].text
        }
    }

    class PropAdapter(private val dataList: List<Prop>) :
        RecyclerView.Adapter<PropAdapter.PropHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_capture_prop, parent, false)
            return PropHolder(view)
        }

        override fun onBindViewHolder(holder: PropHolder, position: Int) {
            holder.bindData(dataList[position])
        }

        override fun getItemCount(): Int {
            return dataList.size
        }

        class PropHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            private val tvName: TextView = itemView.findViewById(R.id.tv_name)

            fun bindData(data: Prop) {
                tvName.text = data.text
            }
        }
    }


    data class Category(val id: Int, val text: String)
    data class Prop(val id: Int, val imgSrc: String, val text: String)

}