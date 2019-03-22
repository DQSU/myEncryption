package gateway.hitrontech.com.encryption.fragment.encryption_decryption

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
import gateway.hitrontech.com.encryption.databinding.FragmentEncryptionDecryptionBinding
import gateway.hitrontech.com.encryption.utils.Constants

class EncryptionDecryptionFragment : BaseFragment(), Contract.View {

    private var mBinding: FragmentEncryptionDecryptionBinding? = null
    private var mPresenter: Contract.Presenter? = null

    override fun createView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View {

        if (!viewExisted) {
            mBinding = DataBindingUtil
                    .inflate(inflater, R.layout.fragment_encryption_decryption, container, false)
        }
        return mBinding!!.root
    }

    @SuppressLint("CheckResult")
    override fun viewFound(view: View?) {
        if (!viewExisted) {
            Presenter(this)
            mPresenter!!.start()

            mBinding!!.key.setText(Constants.KEY)

            RxView.clicks(mBinding!!.encryption)
                    .subscribe {
                        mPresenter!!.getCipherText(
                                mBinding!!.key.text!!.toString(),
                                mBinding!!.plainText.text!!.toString()
                        )
                    }

            // todo changing the value of key has no effect on encryption
            RxView.clicks(mBinding!!.changeKey)
                    .subscribe {
                        if (mBinding!!.keyLayout.visibility == View.VISIBLE) {
                            mBinding!!.keyLayout.visibility = View.GONE
                        } else {
                            mBinding!!.keyLayout.visibility = View.VISIBLE
                        }
                    }

            RxView.clicks(mBinding!!.decryption)
                    .subscribe {
                        mPresenter!!.getPlainText(
                                mBinding!!.key.text!!.toString(),
                                mBinding!!.cipherText.text!!.toString()
                        )
                    }
        }
    }

    override fun getTitle(): String {
        return "加密/解密"
    }

    override fun setCipherText(cipherText: String) {
        mBinding!!.cipherText.setText(cipherText)
    }

    override fun setPlainText(plainText: String) {
        mBinding!!.decryptionText.setText(plainText)
    }

    override fun setPresenter(presenter: Contract.Presenter) {
        mPresenter = presenter
    }

    companion object {

        val instance: Fragment
            get() {
                val fragment = EncryptionDecryptionFragment()
                val args = Bundle()
                fragment.arguments = args
                return fragment
            }
    }
}
