package gateway.hitrontech.com.encryption.base

import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.TextView
import gateway.hitrontech.com.encryption.R


abstract class BaseActivity : AppCompatActivity() {
    private var mDialog: ProgressBar? = null

    fun showProgressDialog() {
        showProgressDialog("")
    }

    fun showProgressDialog(title: String) {
        showProgressDialog(title, null, null)
    }

    fun dismissProgressDialog() {
        if (mDialog != null) {
            mDialog!!.visibility = View.VISIBLE
            mDialog = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dismissProgressDialog()
    }

    private fun dismiss() {
        mDialog!!.visibility = View.GONE;

    }

    private fun show() {
        mDialog!!.visibility = View.VISIBLE
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }


    fun showProgressDialog(title: String, now: Int?, max: Int?) {
        if (now == null && mDialog != null) {
            dismiss()
            mDialog = null
        }
        if (mDialog == null) {
            mDialog = ProgressBar(this)
        }
        if (now != null && max != null) {
            if (mDialog!!.isIndeterminate) {
                dismiss()
                mDialog = ProgressBar(this)
            }

            if (mDialog!!.max == 100 && max != 100) {
                mDialog!!.max = max
            }
            mDialog!!.progress = now
        }

        if (!mDialog!!.isShown) {
            show()
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