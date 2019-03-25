package com.kryst.njit.base

import android.app.ProgressDialog
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.ViewGroup
import android.widget.TextView
import gateway.hitrontech.com.encryption.R


abstract class BaseActivity : AppCompatActivity() {
    private var mDialog: ProgressDialog? = null

    fun showProgressDialog() {
        showProgressDialog("")
    }

    fun showProgressDialog(title: String) {
        showProgressDialog(title, null, null)
    }

    fun dismissProgressDialog() {
        if (mDialog != null) {
            mDialog!!.dismiss()
            mDialog = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dismissProgressDialog()
    }

    fun showProgressDialog(title: String, now: Int?, max: Int?) {
        var mTitle = title
        if (now == null && mDialog != null) {
            mDialog!!.dismiss()
            mDialog = null
        }
        if (mDialog == null) {
            mDialog = ProgressDialog(this)
        }
        if (now != null && max != null) {
            if (mDialog!!.isIndeterminate) {
                mDialog!!.dismiss()
                mDialog = ProgressDialog(this)
            }
            mDialog!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
            if (mDialog!!.max == 100 && max != 100) {
                mDialog!!.max = max
            }
            mDialog!!.progress = now
        }

        if (!mDialog!!.isShowing) {

            val params = mDialog!!.window!!
                    .attributes
            params.dimAmount = 0f
            mDialog!!.window!!.attributes = params

            if (TextUtils.isEmpty(mTitle)) {
                mTitle = "加载中..."
            }
            mDialog!!.setTitle(mTitle)
            mDialog!!.setCancelable(false)
            mDialog!!.show()
        }
    }

    fun showMessage(message: String) {
        val viewGroup = (this.findViewById(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
        val snackBar = Snackbar.make(viewGroup, message, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.ok) {}

        snackBar.view.findViewById<TextView>(android.support.design.R.id.snackbar_text).maxLines = 5
        snackBar.show()

    }

    fun showMessage(e: Throwable) {

        showMessage(e.localizedMessage)
    }

    fun showMessage(@StringRes message: Int) {
        showMessage(getString(message))
    }

    fun showMessage(@StringRes message: Int, message2: String) {
        showMessage(getString(message) + message2)
    }

    abstract fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = false)


}