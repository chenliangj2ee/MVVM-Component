package com.chenliang.account.act

import com.chenliang.account.R
import com.chenliang.account.bean.BeanUser
import com.chenliang.account.databinding.AccountActLoginBinding
import com.chenliang.account.vm.AccountViewModel
import com.chenliang.baselibrary.annotation.My
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.baselibrary.base.obs
import com.chenliang.baselibrary.utils.*
import gorden.rxbus2.Subscribe
import kotlinx.android.synthetic.main.account_act_login.*


@My(myToolbarTitle = "模块A", myToolbarShow = true)
class LoginActivity : MyBaseActivity<AccountActLoginBinding>() {
    override fun layoutId() = R.layout.account_act_login

    override fun initCreate() {
        login.click { loginAction() }
        register.click { toAct(RegisterActivity::class.java) }

    }

    private fun loginAction() {
        var loginVM = initVM(AccountViewModel::class.java)
        var name = account.text.toString()
        var pass = password.text.toString()

        if (hasNull(name, "请输入账号", pass, "请输入密码")) {

            return
        }

        loginVM.login(name, pass).obs(this) {
            it.c { }//登录不处理缓存
            it.y { loginSucess(it.data!!) }
            it.n { loginFail(it.message) }

        }
    }

    /**
     * 登录成功
     */
    private fun loginSucess(user: BeanUser) {
        user.save()
    }

    /**
     * 登录失败,登录失败，创建个账号信息，保存，模拟登录成功
     */
    fun loginFail(message: String) {

        var user = BeanUser()
        user.name = "tom"
        user.age = 12

        user.save()

        toast(message)
        goto("/app/main", "username", "tom", "age", 15)
        finish()
    }

    @Subscribe(code = 100)
    fun eventRegiest(user: BeanUser) {
        mBinding.user = user
    }

}