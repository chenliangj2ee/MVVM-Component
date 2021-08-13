package com.chenliang.account

import com.chenliang.account.bean.BeanUser
import com.chenliang.annotation.MyApiService
import com.chenliang.baselibrary.annotation.MyRetrofitGo
import retrofit2.http.POST
import retrofit2.http.Query


@MyApiService(mName = "API", mPath = "http://api.alpha.xiaoliuyisheng.cn/app/doctor/")
interface ApiService {
    @MyRetrofitGo(mTag = "登录", mLoading = true, mFailToast = true)
    @POST("home/login")
    fun login(
        @Query("account") account: String,
        @Query("password") password: String
    ): Data<BeanUser>

    @MyRetrofitGo(mTag = "注册", mLoading = true, mFailToast = true)
    @POST("home/register")
    fun register(
        @Query("account") account: String,
        @Query("password") password: String
    ): Data<BeanUser>


    @MyRetrofitGo(mTag = "添加测试", mCache = false, mFailToast = true)
    @POST("home/add")
    fun addTest(
        @Query("account") account: String,
        @Query("password") password: String
    ): Data<BeanUser>


}