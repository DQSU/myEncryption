package gateway.hitrontech.com.encryption.fragment.encryption

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import gateway.hitrontech.com.encryption.R
import gateway.hitrontech.com.encryption.base.BaseFragment
import gateway.hitrontech.com.encryption.databinding.FragmentEncryptionBinding
import gateway.hitrontech.com.encryption.utils.SharePreManager

class EncryptionFragment : BaseFragment(), Contract.View {

    private var mBinding: FragmentEncryptionBinding? = null
    private var mPresenter: Contract.Presenter? = null

    override fun createView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View {

        if (!viewExisted) {
            mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_encryption, container, false)

        }
        return mBinding!!.root
    }

    @SuppressLint("CheckResult")
    override fun viewFound(view: View?) {
        if (!viewExisted) {
            Presenter(this)
            mPresenter!!.start()

            RxView.clicks(mBinding!!.encryption)
                    .subscribe { mPresenter!!.getEncryptionText(mBinding!!.plainText.text!!.toString()) }

            RxView.clicks(mBinding!!.changePlainText)
                    .subscribe {
                        if (mBinding!!.plainText.text!!.toString() == getString(R.string.test_number)) {
                            mBinding!!.plainText.setText(getString(R.string.ht_security_key))
                        } else {
                            mBinding!!.plainText.setText(getString(R.string.test_number))
                        }
                    }

        }
    }

    override fun getTitle(): String {
        return "加密"
    }

    override fun setEncryptionText(cipherText: String) {
        SharePreManager.instance.cipherText = cipherText
        mBinding!!.cipherText.setText(cipherText)
    }

    override fun setPresenter(presenter: Contract.Presenter) {
        this.mPresenter = presenter
    }

    companion object {

        val instance: Fragment
            get() {
                val args = Bundle()
                val fragment = EncryptionFragment()
                fragment.arguments = args
                return fragment
            }
    }
}
