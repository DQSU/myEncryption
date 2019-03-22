package gateway.hitrontech.com.encryption.fragment.decryption

import com.hitrontech.hitronencryption.EncryptionManager
import gateway.hitrontech.com.encryption.utils.Constants
import gateway.hitrontech.com.encryption.utils.SharePreManager

class Presenter(private val mView: Contract.View) : Contract.Presenter {

    init {
        mView.setPresenter(this)
    }

    override fun getPlainText(cipherText: String) {
        mView.showProgressDialog()
        mView.setPlainText(
                EncryptionManager.instance
                        .base64DecoderByAppId(
                                SharePreManager.instance.appId,
                                Constants.KEY,
                                cipherText
                        )!!)
        mView.dismissProgressDialog()
    }

    override fun getCipherText() {
        mView.showProgressDialog()
        mView.setCipherText(SharePreManager.instance.cipherText!!)
        mView.dismissProgressDialog()
    }

    override fun start() {
        getCipherText()
    }
}
