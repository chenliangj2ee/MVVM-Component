package com.chenliang.mvvmc.act

import android.content.Intent
import com.chenliang.account.act.LoginActivity
import com.chenliang.account.bean.BeanUser
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.baselibrary.utils.send
import com.chenliang.mvvmc.R
import com.chenliang.mvvmc.databinding.ActivitySplashBinding
import gorden.rxbus2.RxBus

/**
 * 启动页
 */
class SplashActivity : MyBaseActivity<ActivitySplashBinding>() {
    override fun layoutId() = R.layout.activity_splash

    override fun initCreate() {
        delayed(1000) { next() }
    }

    fun next() {
        if (BeanUser().get() == null) {
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            startActivity(Intent(this, MainActivity::class.java))
        }
        finish()
    }
}