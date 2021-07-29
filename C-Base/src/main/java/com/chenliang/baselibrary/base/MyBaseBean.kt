package com.chenliang.baselibrary.base

import androidx.databinding.BaseObservable
import androidx.databinding.ViewDataBinding
import com.chenliang.baselibrary.BaseInit
import com.chenliang.baselibrary.utils.MySpUtis
import com.google.gson.Gson
import java.io.Serializable


/*
如果需要保存数据到sp，且该类型的数据，只会保存一个对象的数据到sp，可以用以下方法：比如BeanUser继承MyBaseBean，登录后保存BeanUser

     var user = BeanUser()
     user.name = "tom"
     user.age = 12
     user.save()//保存

     var user2 = BeanUser().get()//获取
     user2!!.logJson()

      BeanUser().clear()//清除
 */
open class MyBaseBean() : BaseObservable(), Serializable {
    open var itemType = 0
    open var binding: ViewDataBinding? = null


    open fun save() = MySpUtis.putString(this::class.java.name, Gson().toJson(this))
    open fun get() = MySpUtis.getObject(this::class.java.name, this::class.java)
    open fun clear() = MySpUtis.clear(this::class.java.name)
}

