package gateway.hitrontech.com.encryption.activity

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.view.MenuItem
import gateway.hitrontech.com.encryption.R
import gateway.hitrontech.com.encryption.base.BaseActivity
import gateway.hitrontech.com.encryption.databinding.ActivityFunctionBinding
import gateway.hitrontech.com.encryption.fragment.about.AboutFragment
import gateway.hitrontech.com.encryption.fragment.decryption.DecryptionFragment
import gateway.hitrontech.com.encryption.fragment.encryption.EncryptionFragment
import gateway.hitrontech.com.encryption.fragment.encryption_decryption.EncryptionDecryptionFragment
import gateway.hitrontech.com.encryption.fragment.file_encryption_decryption.FileEncryptionDecryptionFragment
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class FunctionActivity : BaseActivity() {

    private var mBinding: ActivityFunctionBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_function)

        // set action bar
        setSupportActionBar(mBinding!!.toolBar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu)
            supportActionBar!!.setDisplayShowTitleEnabled(true)
        }

        mBinding!!.navigationView.setCheckedItem(R.id.encryption)
        mBinding!!.navigationView.setNavigationItemSelectedListener { menuItem ->
            drawerItemClick(menuItem.title.toString())
            true
        }

        replaceFragment(FileEncryptionDecryptionFragment.instance)
    }

    @SuppressLint("ResourceType")
    private fun drawerItemClick(title: String) {
        when (title) {
            "加密" -> replaceFragment(EncryptionFragment.instance)
            "解密" -> replaceFragment(DecryptionFragment.instance)
            "加密/解密" -> replaceFragment(EncryptionDecryptionFragment.instance)
            "文件加密/解密" -> replaceFragment(FileEncryptionDecryptionFragment.instance)
            "关于" -> replaceFragment(AboutFragment.getInstance())
            else -> {
            }
        }
    }

    override fun replaceFragment(fragment: Fragment, addToBackStack: Boolean) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        transaction.replace(R.id.function_interface, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
        closeDrawer()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                if (mBinding!!.drawer.isDrawerOpen(GravityCompat.START)) {
                    closeDrawer()
                } else {
                    openDrawer()
                }
            }
            else -> {
            }
        }
        return false
    }

    private fun openDrawer() {
        Observable.timer(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<Long>() {
                    override fun onCompleted() {}

                    override fun onError(e: Throwable) {}

                    override fun onNext(aLong: Long?) {
                        mBinding!!.drawer.openDrawer(GravityCompat.START)
                    }
                })
    }

    private fun closeDrawer() {
        Observable.timer(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<Long>() {
                    override fun onCompleted() {}

                    override fun onError(e: Throwable) {}

                    override fun onNext(aLong: Long?) {
                        mBinding!!.drawer.closeDrawers()
                    }
                })
    }
}
