package gateway.hitrontech.com.encryption.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * Created by krystal on 2018/12/23 0023.
 */


abstract class BaseFragment : Fragment() {

    private var mRootView: View? = null
    protected lateinit var mContext: Context
    private var mActionBar: android.support.v7.app.ActionBar? = null
    protected lateinit var mActivity: BaseActivity
    protected open var viewExisted = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = createView(inflater, container, savedInstanceState)
        assert(mRootView != null)
        viewFound(mRootView!!)
        viewExisted = true
        return mRootView
    }


    protected abstract fun createView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View

    protected abstract fun viewFound(view: View?)


    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mContext = activity
        mActivity = activity as BaseActivity
    }

    protected abstract fun getTitle(): String

    protected fun setTitle(title: String) {
        mActionBar!!.title = title
    }

    private lateinit var title: String

    override fun onResume() {
        super.onResume()
        mActionBar = (activity as AppCompatActivity).supportActionBar
        title = getTitle()
        setTitle(title)
    }

    fun showMessage(message: String) {
        mActivity.showMessage(message)
    }

    fun showMessage(e: Throwable) {
        mActivity.showMessage(e)
    }

    fun showMessage(@StringRes message: Int) {
        mActivity.showMessage(message)
    }

    fun showMessage(@StringRes message: Int, message2: String) {
        mActivity.showMessage(message, message2)
    }

    fun showProgressDialog(title: String, now: Int?, max: Int?) {
        mActivity.showProgressDialog(title, now, max)
    }

    fun dismissProgressDialog() {
        mActivity.dismissProgressDialog()
    }

    fun showProgressDialog() {
        showProgressDialog("")
    }

    fun showProgressDialog(title: String) {
        showProgressDialog(title, null, null)
    }

    fun onBackPressedSupport(): Boolean {
        return if (fragmentManager != null && fragmentManager!!.backStackEntryCount > 0) {
            fragmentManager!!.popBackStack()
            true
        } else {
            onBackPressedSupport()
        }
    }

    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = false) {
        mActivity.replaceFragment(fragment, addToBackStack)
    }
}
