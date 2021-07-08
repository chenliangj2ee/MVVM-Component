package com.chenliang.baselibrary.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.chenliang.baselibrary.R
import com.chenliang.baselibrary.annotation.MVVM
import com.chenliang.baselibrary.annotation.activityRefresh
import com.chenliang.baselibrary.annotation.activityTitle
import com.chenliang.baselibrary.annotation.activityToolbar
import com.chenliang.baselibrary.utils.log
import com.chenliang.baselibrary.utils.show
import com.chenliang.baselibrary.view.MyToolBar
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.base_activity_content.*
import kotlinx.android.synthetic.main.base_fragment_content.view.*

@MVVM(toolbar = false)
abstract class MyBaseFragment<BINDING : ViewDataBinding> : Fragment() {
    lateinit var rootView: View
    lateinit var toolBar: MyToolBar
    lateinit var refresh: SmartRefreshLayout
    lateinit var binding: BINDING
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        log("MyActivityManager", this.javaClass.name)
        rootView = layoutInflater.inflate(R.layout.base_fragment_content, null)
        initToolbar()
        bindView()
        var onCreateStart = System.currentTimeMillis()
        initOnCreateView()
        var onCreateEnd = System.currentTimeMillis()
        if (onCreateEnd - onCreateStart > 200) {
            throw Exception("${this::class.simpleName} initOnCreateView耗时太长，请优化...")
        }
        return rootView
    }

    private fun initToolbar() {
        toolBar = MyToolBar(context)
        rootView.base_root.addView(
            toolBar,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        )
        toolBar.showLeft(false)
        toolBar.showRight(false)
        toolBar.setTitle(activityTitle(this))
        toolBar.show(activityToolbar(this))
    }

    open fun refresh() {
    }

    open fun stopRefresh() {
        refresh.finishRefresh()
    }

    private fun bindView() {
        var content = layoutInflater.inflate(layoutId(), null)

        refresh = SmartRefreshLayout(context)
        refresh.setEnableRefresh(activityRefresh(this))
        refresh.setRefreshHeader(ClassicsHeader(context))
        refresh.setOnRefreshListener {
            refresh();
        }
        refresh.addView(
            content, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        )
        binding = DataBindingUtil.bind<BINDING>(content)!!
        base_root.addView(
            refresh,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        )
    }

    abstract fun initOnCreateView()
    abstract fun layoutId(): Int

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}