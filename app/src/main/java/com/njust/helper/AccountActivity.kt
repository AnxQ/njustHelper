package com.njust.helper

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import com.njust.helper.activity.BaseActivity
import com.njust.helper.tools.Prefs
import com.zwb.commonlibs.injection.IntentInjection
import com.zwb.commonlibs.injection.ViewInjection

/**
 * 账户切换
 *
 * @author zwb
 */
class AccountActivity : BaseActivity() {
    companion object {
        const val REQUEST_JWC = 2
        const val REQUEST_LIB = 4
        const val EXTRA_PASSWORD_TYPE = "password_type"
    }

    @ViewInjection(R.id.editText1)
    lateinit var stuidText: EditText
    @ViewInjection(R.id.editText2)
    lateinit var jwcPwdText: EditText
    @ViewInjection(R.id.editText3)
    lateinit var libPwdText: EditText

    @IntentInjection(EXTRA_PASSWORD_TYPE)
    var type: Int = 0

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        // 读入原账号数据
        stuidText.setText(Prefs.getId(this))
        jwcPwdText.setText(Prefs.getJwcPwd(this))
        libPwdText.setText(Prefs.getLibPwd(this))

        // 设置焦点
        when (type) {
            REQUEST_JWC -> jwcPwdText.requestFocus()
            REQUEST_LIB -> libPwdText.requestFocus()
            else -> stuidText.requestFocus()
        }
    }

    override fun layoutRes(): Int {
        return R.layout.activity_account
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.account, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_save -> {
                val stuid = stuidText.text.toString().trim()
                val jwcPwd = jwcPwdText.text.toString()
                val libPwd = libPwdText.text.toString()
                if (stuid == "") {
                    showSnack(getString(R.string.toast_input_id))
                    return true
                }
                if (jwcPwd == "") {
                    showSnack(getString(R.string.toast_input_jwc_pwd))
                    return true
                }
                if (libPwd == "") {
                    showSnack(getString(R.string.toast_input_lib_pwd))
                    return true
                }
                Prefs.putIdValues(this, stuid, jwcPwd, libPwd)
                Prefs.putCookie(this, "", null, 1)
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }
}
