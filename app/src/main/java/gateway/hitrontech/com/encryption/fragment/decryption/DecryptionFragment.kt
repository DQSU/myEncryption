package gateway.hitrontech.com.encryption.fragment.decryption

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hitrontech.hitronencryption.EncryptionManager
import com.jakewharton.rxbinding2.view.RxView
import gateway.hitrontech.com.encryption.R
import gateway.hitrontech.com.encryption.base.BaseFragment
import gateway.hitrontech.com.encryption.databinding.FragmentDecryptionBinding
import gateway.hitrontech.com.encryption.utils.Constants
import gateway.hitrontech.com.encryption.utils.SharePreManager

class DecryptionFragment : BaseFragment(), Contract.View {

    private var mBinding: FragmentDecryptionBinding? = null

    private var mPresenter: Contract.Presenter? = null

    override fun createView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View {

        if (!viewExisted) {
            mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_decryption, container, false)
        }
        return mBinding!!.root
    }

    @SuppressLint("CheckResult")
    override fun viewFound(view: View?) {
        if (!viewExisted) {
            Presenter(this)
            mPresenter!!.start()

            RxView.clicks(mBinding!!.decryption)
                    .subscribe { mPresenter!!.getPlainText(mBinding!!.cipherText.text!!.toString()) }

            RxView.clicks(mBinding!!.changeCipherText)
                    .subscribe { changeCipherText() }


        }
    }

    override fun getTitle(): String {
        return "解密"
    }

    override fun setPlainText(plainText: String) {
        mBinding!!.plainText.setText(plainText)
    }

    override fun setCipherText(cipherText: String) {
        mBinding!!.cipherText.setText(cipherText)
    }

    override fun setPresenter(presenter: Contract.Presenter) {
        mPresenter = presenter
    }

    private fun changeCipherText() {
        if (mBinding!!.cipherText.text!!.toString() == EncryptionManager.instance.base64EncoderByAppId(
                        SharePreManager.instance.appId,
                        Constants.KEY,
                        context!!.getString(R.string.test_number)
                )) {
            mBinding!!.cipherText.setText(
                    EncryptionManager.instance.base64EncoderByAppId(
                            SharePreManager.instance.appId,
                            Constants.KEY,
                            context!!.getString(R.string.ht_security_key)
                    )
            )
        } else {
            mBinding!!.cipherText.setText(
                    EncryptionManager.instance.base64EncoderByAppId(
                            SharePreManager.instance.appId,
                            Constants.KEY,
                            context!!.getString(R.string.test_number)
                    )
            )
        }
    }

    companion object {


        val instance: Fragment
            get() {
                val fragment = DecryptionFragment()
                val args = Bundle()
                fragment.arguments = args
                return fragment
            }
    }
}
