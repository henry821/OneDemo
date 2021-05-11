package com.demo.google.storage

import com.demo.activity.EntranceActivity
import com.demo.one.R

/**
 * 存储示例入口
 */
class StorageEntranceActivity : EntranceActivity() {

    override fun initData(): List<MainData> {
        val list = ArrayList<MainData>()
        list.add(MainData(getString(R.string.action_open_document_app_name),
            com.demo.google.storage.actionopendocument.MainActivity::class.java))
        list.add(MainData(getString(R.string.action_open_document_tree_app_name),
            com.demo.google.storage.actionopendocumenttree.MainActivity::class.java))
        return list
    }

}